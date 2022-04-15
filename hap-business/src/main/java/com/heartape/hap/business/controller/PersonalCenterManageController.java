package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.CommentManageBO;
import com.heartape.hap.business.entity.bo.ContentManageBO;
import com.heartape.hap.business.entity.dto.PersonalCenterManageDTO;
import com.heartape.hap.business.entity.ro.PersonalCenterManageRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IPersonalCenterManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 个人中心管理
 */
@RestController
@RequestMapping("/business/personal/manage")
@Api(tags = "个人中心管理")
public class PersonalCenterManageController {

    @Autowired
    private IPersonalCenterManageService personalCenterManageService;

    @GetMapping("/content/all")
    @ApiOperation("内容管理->全部")
    public Result contentAll(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        PersonalCenterManageDTO personalCenterManageDTO = new PersonalCenterManageDTO();
        BeanUtils.copyProperties(personalCenterManageRO, personalCenterManageDTO);
        PageInfo<ContentManageBO> contentAll = personalCenterManageService.contentAll(personalCenterManageDTO);
        return Result.success(contentAll);
    }

    @GetMapping("/content/article")
    @ApiOperation("内容管理->文章")
    public Result contentArticle(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        PersonalCenterManageDTO personalCenterManageDTO = new PersonalCenterManageDTO();
        BeanUtils.copyProperties(personalCenterManageRO, personalCenterManageDTO);
        PageInfo<ContentManageBO> contentArticle = personalCenterManageService.contentArticle(personalCenterManageDTO);
        return Result.success(contentArticle);
    }

    @GetMapping("/content/topic")
    @ApiOperation("内容管理->话题")
    public Result contentTopic(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        PersonalCenterManageDTO personalCenterManageDTO = new PersonalCenterManageDTO();
        BeanUtils.copyProperties(personalCenterManageRO, personalCenterManageDTO);
        PageInfo<ContentManageBO> contentTopic = personalCenterManageService.contentTopic(personalCenterManageDTO);
        return Result.success(contentTopic);
    }

    @GetMapping("/content/discuss")
    @ApiOperation("内容管理->讨论")
    public Result contentDiscuss(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        PersonalCenterManageDTO personalCenterManageDTO = new PersonalCenterManageDTO();
        BeanUtils.copyProperties(personalCenterManageRO, personalCenterManageDTO);
        PageInfo<ContentManageBO> contentDiscuss = personalCenterManageService.contentDiscuss(personalCenterManageDTO);
        return Result.success(contentDiscuss);
    }

    @GetMapping("/comment/all")
    @ApiOperation("评论管理->全部")
    public Result commentAll(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        return Result.success();
    }

    @GetMapping("/comment/article")
    @ApiOperation("评论管理->文章")
    public Result commentArticle(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        PersonalCenterManageDTO personalCenterManageDTO = new PersonalCenterManageDTO();
        BeanUtils.copyProperties(personalCenterManageRO, personalCenterManageDTO);
        PageInfo<CommentManageBO> commentArticle = personalCenterManageService.commentArticle(personalCenterManageDTO);
        return Result.success(commentArticle);
    }

    @GetMapping("/comment/discuss")
    @ApiOperation("评论管理->讨论")
    public Result commentDiscuss(@ModelAttribute PersonalCenterManageRO personalCenterManageRO) {
        PersonalCenterManageDTO personalCenterManageDTO = new PersonalCenterManageDTO();
        BeanUtils.copyProperties(personalCenterManageRO, personalCenterManageDTO);
        PageInfo<CommentManageBO> commentDiscuss = personalCenterManageService.commentDiscuss(personalCenterManageDTO);
        return Result.success(commentDiscuss);
    }
}
