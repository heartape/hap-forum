package com.heartape.hap.api.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.Callback;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author heartape
 * @description Oss工具
 * @since 2021/12/3
 */
@Slf4j
@Component
@Getter
public class OssUtils {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${oss.accessId}")
    private String accessId;
    @Value("${oss.accessKey}")
    private String accessKey;
    @Value("${oss.bucket}")
    private String bucket;
    // 与server整合,用来将oss下行流量代理到内网
    @Value("${oss.proxy}")
    private String proxy;

    @Value("${oss.callbackHost}")
    private String callbackHost;
    private final String callbackUrl = "/oss/callback";

    // Bucket所在地域对应的公网endpoint
    private final String endpoint = "oss-cn-shanghai.aliyuncs.com";
    // Bucket所在地域对应的内网endpoint
    private final String innerEndpoint = "oss-cn-shanghai-internal.aliyuncs.com";
    public static final String PICTURE_DIR = "album/"; // 用户上传文件时指定的前缀

    private final String regionId = "cn-shanghai";
    private final String roleArn = "acs:ram::***:role/oss";
    private final String roleSessionName = "oss";

    public final Integer PICTURE_TIME = 3600;
    public final Integer ROLE_TIME = 604800;

    /**
     * 后端签名秘钥，前端post上传
     */
    public Map<String, Object> uploadAutographKey(String dir) {
        String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
        DefaultCredentialProvider credentialProvider = new DefaultCredentialProvider(accessId, accessKey);
        ClientConfiguration clientConfiguration = new ClientConfiguration();
        OSSClient client = new OSSClient(endpoint, credentialProvider, clientConfiguration);
        long expireTime = 30;
        long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
        java.sql.Date expiration = new java.sql.Date(expireEndTime);
        PolicyConditions policyConds = new PolicyConditions();
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

        String postPolicy = client.generatePostPolicy(expiration, policyConds);
        byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
        String encodedPolicy = BinaryUtil.toBase64String(binaryData);
        String postSignature = client.calculatePostSignature(postPolicy);

        Map<String, Object> data = new HashMap<>();
        data.put("accessId", accessId);
        data.put("policy", encodedPolicy);
        data.put("signature", postSignature);
        data.put("dir", dir);
        data.put("host", host);
        data.put("expire", String.valueOf(expireEndTime / 1000));
        return data;
    }

    /**
     * 后端签名秘钥，前端post上传，添加回调
     * not check
     */
    public Map<String, Object> uploadCallbackAutograph(String dir) {
        Map<String, Object> uploadAutograph = uploadAutographKey(dir);
        Callback callback = new Callback();
        callback.setCallbackHost(callbackHost);
        callback.setCallbackUrl(callbackUrl);
        callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
        callback.setCallbackBody("{\\\"mimeType\\\":${mimeType},\\\"size\\\":${size}}");
        uploadAutograph.put("callback",callback);
        return uploadAutograph;
    }

    /**
     * 通过sts获取上传临时url,
     * 方法:put
     * @param filename 填写文件名，如dog.png
     * @param time 角色临时访问过期时间
     * not check
     */
    public String uploadAutographSTS(String dir, String filename, Integer time) throws ClientException {

        // 根据情况选择是否启用cache
        TemporaryRole temporaryRole = temporaryRoleCache(regionId, accessId, accessKey, roleArn, roleSessionName, time);

        OSS ossClient = new OSSClientBuilder().build(endpoint, temporaryRole.getAccessKeyId(), temporaryRole.getAccessKeySecret(), temporaryRole.getSecurityToken());
        String objectName = dir + filename;
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, objectName, HttpMethod.PUT);
        request.setContentType("application/txt");
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        request.setExpiration(expiration);
        URL uploadUrl = ossClient.generatePresignedUrl(request);
        ossClient.shutdown();
        return uploadUrl.toString();
    }

    public String uploadAutographSTS(String dir, String objectName) throws ClientException {
        return uploadAutographSTS(dir, objectName, ROLE_TIME);
    }

    /**
     * 返回临时accessKeyId,accessKeySecret,securityToken
     * @param regionId Bucket所在地域,如"cn-shanghai"
     * @param accessKeyId 用户KeyId
     * @param accessKeySecret 用户KeySecret
     * @param roleArn 角色 -> 基本信息 -> ARN
     * @param roleSessionName roleSessionName时临时Token的会话名称，自己指定用于标识你的用户,
     *                        要注意roleSessionName的长度和规则，不要有空格，只能有'-'和'_'字母和数字等字符
     * @param durationSeconds 过期时间
     */
    public TemporaryRole temporaryRole(String regionId, String accessKeyId, String accessKeySecret,
                                                    String roleArn, String roleSessionName, long durationSeconds) throws ClientException {

        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        AssumeRoleRequest request = new AssumeRoleRequest();
        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        if (durationSeconds < 3600){
            request.setDurationSeconds(3600L);
        } else {
            request.setDurationSeconds(durationSeconds);
        }
        // 发起请求,并得到响应
        AssumeRoleResponse response = client.getAcsResponse(request);
        AssumeRoleResponse.Credentials credentials = response.getCredentials();
        String accessKeyIdValue = credentials.getAccessKeyId();
        String accessKeySecretValue = credentials.getAccessKeySecret();
        String securityTokenValue = credentials.getSecurityToken();
        return new TemporaryRole(accessKeyIdValue,accessKeySecretValue,securityTokenValue);
    }

    public TemporaryRole temporaryRoleCache(String regionId, String accessKeyId, String accessKeySecret,
                                             String roleArn, String roleSessionName, long durationSeconds) throws ClientException {
        String roleKey = "photo:oss.role:token:hash";
        String idKey = "accessKeyId";
        String secretKey = "accessKeySecret";
        String tokenKey = "securityToken";

        String accessKeyIdValue;
        String accessKeySecretValue;
        String securityTokenValue;

        // 角色扮演缓存
        Boolean alive = redisTemplate.hasKey(roleKey);
        if (alive!=null && alive){
            accessKeyIdValue = redisTemplate.<String, String>opsForHash().get(roleKey, idKey);
            accessKeySecretValue = redisTemplate.<String, String>opsForHash().get(roleKey, secretKey);
            securityTokenValue = redisTemplate.<String, String>opsForHash().get(roleKey, tokenKey);
        } else {
            TemporaryRole temporaryRole = temporaryRole(regionId, accessKeyId, accessKeySecret, roleArn, roleSessionName, durationSeconds);
            accessKeyIdValue = temporaryRole.getAccessKeyId();
            accessKeySecretValue = temporaryRole.getAccessKeySecret();
            securityTokenValue = temporaryRole.getSecurityToken();
            String script = "local key = KEYS[1]\nlocal key1 = KEYS[2]\nlocal val1 = KEYS[3]\nlocal key2 = KEYS[4]\nlocal val2 = KEYS[5]\nlocal key3 = KEYS[6]\nlocal val3 = KEYS[7]\nlocal expire = ARGV[1]\nif redis.call(\"get\", key) == false then\nif redis.call(\"hset\", key, key1, val1, key2, val2, key3, val3) then\nif tonumber(expire) > 0 then\nredis.call(\"expire\", key, expire)\nreturn\nend\nend\nelse\nend";
            RedisScript<Boolean> redisScript = RedisScript.of(script);
            List<String> keys = new ArrayList<>();
            keys.add(roleKey);
            keys.add(idKey);
            keys.add(accessKeyIdValue);
            keys.add(secretKey);
            keys.add(accessKeySecretValue);
            keys.add(tokenKey);
            keys.add(securityTokenValue);
            redisTemplate.execute(redisScript, keys, Long.toString(durationSeconds - 10));
        }
        return new TemporaryRole(accessKeyIdValue,accessKeySecretValue,securityTokenValue);
    }

    /**
     * 通过STS获取的临时身份凭证,生成临时访问url
     * @param bucketName Bucket名称,如heartape-forum
     * @param objectName 填写Object完整路径，例如picture/dog.png,Object完整路径中不能包含Bucket名称
     * @param time 角色临时访问过期时间
     * @return GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容
     */
    public PictureUrl pictureTemporaryInnerUrl(String bucketName, String objectName, long time) throws ClientException {

        // 根据情况选择是否启用cache
        TemporaryRole temporaryRole = temporaryRoleCache(regionId, accessId, accessKey, roleArn, roleSessionName, time);
        // 根据情况选择内部还是外部endpoint
        OSS ossClient = new OSSClientBuilder().build(innerEndpoint, temporaryRole.getAccessKeyId(), temporaryRole.getAccessKeySecret(), temporaryRole.getSecurityToken());
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, objectName);
        // 生成以GET方法访问的签名URL
        generatePresignedUrlRequest.setMethod(HttpMethod.GET);
        generatePresignedUrlRequest.addQueryParameter("x-oss-process","image/resize,w_240,m_mfit");
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        generatePresignedUrlRequest.setExpiration(expiration);
        URL thumbnailUrl = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
        URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
        ossClient.shutdown();
        return new PictureUrl(url.toString(), thumbnailUrl.toString());
    }

    public PictureUrl pictureTemporaryInnerUrl(String bucketName, String objectName) throws ClientException {
        return pictureTemporaryInnerUrl(bucketName, objectName, ROLE_TIME);
    }

    @Data
    public class TemporaryRole{
        private String accessKeyId;
        private String accessKeySecret;
        private String securityToken;

        public TemporaryRole(String accessKeyId, String accessKeySecret, String securityToken) {
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
            this.securityToken = securityToken;
        }
    }

    /**
     * url代理
     */
    public PictureUrl proxyPictureUrl(PictureUrl source) {
        String url = source.getUrl();
        String thumbnailUrl = source.getThumbnailUrl();

        int indexUrl = url.indexOf("com") + 3;
        String subUrl = url.substring(0, indexUrl);
        String urlReplace = url.replaceFirst(subUrl, proxy);

        int indexThumbnailUrl = thumbnailUrl.indexOf("com") + 3;
        String subThumbnailUrl = thumbnailUrl.substring(0, indexThumbnailUrl);
        String thumbnailUrlReplace = thumbnailUrl.replaceFirst(subThumbnailUrl, proxy);

        return new PictureUrl(urlReplace,thumbnailUrlReplace);
    }

    @Data
    public class PictureUrl{
        private String url;
        private String thumbnailUrl;

        public PictureUrl(String url, String thumbnailUrl) {
            this.url = url;
            this.thumbnailUrl = thumbnailUrl;
        }
    }
}

