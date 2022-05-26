package com.heartape.hap.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.PrivateLetter;
import com.heartape.hap.entity.bo.PrivateLetterBO;
import com.heartape.hap.entity.bo.PrivateLetterCreatorBO;
import com.heartape.hap.entity.bo.PrivateLetterSimpleBO;
import com.heartape.hap.entity.dto.PrivateLetterDTO;
import com.heartape.hap.entity.ro.PrivateLetterRO;
import com.heartape.hap.feign.TokenFeignServiceImpl;
import com.heartape.hap.response.Result;
import com.heartape.hap.service.IPrivateLetterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 私信
 */
@RestController
@RequestMapping("/system/letter")
@Api(tags = "私信")
public class PrivateLetterController {

    @Autowired
    private IPrivateLetterService privateLetterService;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @PostMapping
    @ApiOperation("创建私信")
    public Result create(@RequestBody PrivateLetterRO privateLetterRO) {
        PrivateLetterDTO privateLetterDTO = new PrivateLetterDTO();
        BeanUtils.copyProperties(privateLetterRO, privateLetterDTO);
        privateLetterService.create(privateLetterDTO);
        return Result.success();
    }

    @GetMapping("/simple")
    @ApiOperation("私信简要信息列表")
    public Result simple(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Long targetUid = tokenFeignService.getUid();
        PageInfo<PrivateLetterSimpleBO> privateLetterSimple = privateLetterService.simple(targetUid, pageNum, pageSize);
        long unread = privateLetterService.count(new QueryWrapper<PrivateLetter>().eq("target_uid", targetUid).eq("look_up", false));
        return Result.success().data("privateLetter", privateLetterSimple).data("unread", unread);
    }

    @GetMapping("/creator")
    @ApiOperation("详细信息私信用户列表")
    public Result creatorList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<PrivateLetterCreatorBO> privateLetterCreator = privateLetterService.creatorList(pageNum, pageSize);
        return Result.success(privateLetterCreator);
    }

    @GetMapping("/detail")
    @ApiOperation("详细信息，某私信用户的所有私信内容")
    public Result detail(@RequestParam Long uid, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<PrivateLetterBO> privateLetterBO = privateLetterService.detail(uid, pageNum, pageSize);
        return Result.success(privateLetterBO);
    }

    @PutMapping("/read/one")
    @ApiOperation("将单个用户的私信全部设置为已读")
    public Result readOne(@RequestParam Long uid) {
        privateLetterService.readOne(uid);
        return Result.success();
    }

    @PutMapping("/read/all")
    @ApiOperation("将所有私信全部设置为已读")
    public Result readAll() {
        privateLetterService.readAll();
        return Result.success();
    }

    @DeleteMapping
    @ApiOperation("删除单条私信")
    public Result remove(@RequestParam Long letterId) {
        privateLetterService.removeById(letterId);
        return Result.success();
    }
}
