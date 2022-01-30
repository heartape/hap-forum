package com.heartape.hap.admin.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 资源操作记录工具类，如点赞，收藏等单次不可重复操作
 */
@Component
public class SourceOperateUtil {

    @Autowired
    private RedisTemplate<String,Long> longRedisTemplate;

    @Autowired
    private RedisTemplate<String,Integer> intRedisTemplate;

    /**
     * 获取资源被操作数
     */
    public int operateNumber(long parentId, long sourceId, String parentKey, String sourceKey){
        String parent = String.format(parentKey, parentId);
        String source = String.format(sourceKey, sourceId);
        Integer like = intRedisTemplate.<String, Integer>opsForHash().get(parent, source);
        return like == null || like == 0 ? 0 : like;
    }

    /**
     * 获取父资源被操作数
     */
    public int parentOperateNumber(long parentId, String parentKey){
        String parent = String.format(parentKey, parentId);
        Map<String, Integer> entries = intRedisTemplate.<String, Integer>opsForHash().entries(parent);

        int total = 0;
        for (Integer value : entries.values()) {
            total += value;
        }
        return total;
    }

    /**
     * 记录当前资源受到的操作次数
     * @param parentId 父资源
     * @param sourceId 当前资源
     */
    public void setOperate(long parentId, long sourceId, String parentKey, String sourceKey){
        String parent = String.format(parentKey, parentId);
        String source = String.format(sourceKey, sourceId);
        Boolean exist = longRedisTemplate.<String, Integer>opsForHash().hasKey(parent, source);
        if (exist) {
            intRedisTemplate.<String, Integer>opsForHash().increment(parent,source,1);
        } else {
            intRedisTemplate.<String, Integer>opsForHash().put(parent,source,1);
        }
    }

    /**
     * 是否已经对当前资源进行操作
     */
    public boolean getPeopleOperate(long uid, String userKey, long sourceId){
        String user = String.format(userKey, uid);
        Boolean member = longRedisTemplate.opsForSet().isMember(user, sourceId);
        return member != null && member;
    }

    /**
     * 记录用户操作行为,false为已经进行过操作
     */
    public boolean setPeopleOperate(long uid, String userKey, long sourceId){
        String user = String.format(userKey, uid);
        Long add = longRedisTemplate.opsForSet().add(user, sourceId);
        return add != null && add == 1;
    }

    /**
     * todo:根据被操作数排序分页
     */
    public List<Integer> pageByOperate(long parentId, String parentKey, int page, int size){
        String parent = String.format(parentKey, parentId);
        int offset = (page - 1) * size;
        Map<String, Integer> entries = intRedisTemplate.<String, Integer>opsForHash().entries(parent);
        List<Map.Entry<String, Integer>> list = new ArrayList<>(entries.entrySet());
        if (list.size() <= offset) {
            return new ArrayList<>();
        }
        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return -o1.getValue().compareTo(o2.getValue());
            }
            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });
        List<Integer> ids = new ArrayList<>();
        for (int i = offset; i < offset + size; i++) {
            String key = list.get(i).getKey();
            ids.add(Integer.getInteger(key));
        }
        return ids;
    }
}
