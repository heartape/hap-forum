package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.constant.CommentMainTypeEnum;
import com.heartape.hap.business.constant.CommentTargetTypeEnum;
import com.heartape.hap.business.constant.ContentMainTypeEnum;
import com.heartape.hap.business.constant.ContentTypeEnum;
import com.heartape.hap.business.entity.*;
import com.heartape.hap.business.entity.bo.CommentManageBO;
import com.heartape.hap.business.entity.bo.ContentManageBO;
import com.heartape.hap.business.entity.dto.PersonalCenterManageDTO;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.*;
import com.heartape.hap.business.service.IPersonalCenterManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonalCenterManageServiceImpl implements IPersonalCenterManageService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private ContentManageMapper contentManageMapper;

    @Autowired
    private CommentManageMapper commentManageMapper;

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Override
    public PageInfo<ContentManageBO> contentAll(PersonalCenterManageDTO personalCenterManageDTO) {
        return null;
    }

    @Override
    public PageInfo<ContentManageBO> contentArticle(PersonalCenterManageDTO personalCenterManageDTO) {
        LocalDate startTime = personalCenterManageDTO.getStartTime();
        LocalDate endTime = personalCenterManageDTO.getEndTime();
        long uid = tokenFeignService.getUid();

        LambdaQueryWrapper<Article> queryWrapper = new QueryWrapper<Article>().lambda();
        queryWrapper
                .eq(Article::getUid, uid).orderByDesc(Article::getCreatedTime)
                .select(Article::getArticleId, Article::getTitle, Article::getSimpleContent, Article::getCreatedTime);
        if (startTime != null || endTime != null) {
            queryWrapper.between(Article::getCreatedTime, startTime, endTime);
        }
        PageHelper.startPage(personalCenterManageDTO.getPageNum(), personalCenterManageDTO.getPageSize());
        List<Article> articles = articleMapper.selectList(queryWrapper);
        PageInfo<Article> pageInfo = PageInfo.of(articles);
        PageInfo<ContentManageBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<ContentManageBO> contentManageBOS = pageInfo.getList().stream().map(article -> {
            ContentManageBO contentManageBO = new ContentManageBO();
            contentManageBO.setId(article.getArticleId());
            contentManageBO.setMainId(article.getArticleId());
            contentManageBO.setTitle(article.getTitle());
            contentManageBO.setContent(article.getSimpleContent());
            contentManageBO.setCreatedTime(article.getCreatedTime());
            contentManageBO.setType(ContentTypeEnum.ARTICLE.getTypeName());
            contentManageBO.setMainType(ContentMainTypeEnum.ARTICLE.getTypeName());
            // todo:加入数据统计
            contentManageBO.setCommentNumber(100);
            contentManageBO.setLikeNumber(100);
            contentManageBO.setReaderNumber(100);
            return contentManageBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(contentManageBOS);
        return boPageInfo;
    }

    @Override
    public PageInfo<ContentManageBO> contentTopic(PersonalCenterManageDTO personalCenterManageDTO) {
        LocalDate startTime = personalCenterManageDTO.getStartTime();
        LocalDate endTime = personalCenterManageDTO.getEndTime();
        long uid = tokenFeignService.getUid();

        LambdaQueryWrapper<Topic> queryWrapper = new QueryWrapper<Topic>().lambda();
        queryWrapper
                .select(Topic::getTopicId, Topic::getTitle, Topic::getSimpleDescription, Topic::getCreatedTime)
                .eq(Topic::getUid, uid).orderByDesc(Topic::getCreatedTime);
        if (startTime != null || endTime != null) {
            queryWrapper.between(Topic::getCreatedTime, startTime, endTime);
        }
        PageHelper.startPage(personalCenterManageDTO.getPageNum(), personalCenterManageDTO.getPageSize());
        List<Topic> articles = topicMapper.selectList(queryWrapper);
        PageInfo<Topic> pageInfo = PageInfo.of(articles);
        PageInfo<ContentManageBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<ContentManageBO> contentManageBOS = pageInfo.getList().stream().map(topic -> {
            ContentManageBO contentManageBO = new ContentManageBO();
            contentManageBO.setId(topic.getTopicId());
            contentManageBO.setMainId(topic.getTopicId());
            contentManageBO.setTitle(topic.getTitle());
            contentManageBO.setContent(topic.getSimpleDescription());
            contentManageBO.setCreatedTime(topic.getCreatedTime());
            contentManageBO.setType(ContentTypeEnum.TOPIC.getTypeName());
            contentManageBO.setMainType(ContentMainTypeEnum.TOPIC.getTypeName());
            // todo:加入数据统计
            contentManageBO.setCommentNumber(100);
            contentManageBO.setLikeNumber(100);
            contentManageBO.setReaderNumber(100);
            return contentManageBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(contentManageBOS);
        return boPageInfo;
    }

    @Override
    public PageInfo<ContentManageBO> contentDiscuss(PersonalCenterManageDTO personalCenterManageDTO) {
        LocalDate startTime = personalCenterManageDTO.getStartTime();
        LocalDate endTime = personalCenterManageDTO.getEndTime();
        long uid = tokenFeignService.getUid();

        PageHelper.startPage(personalCenterManageDTO.getPageNum(), personalCenterManageDTO.getPageSize());
        List<ContentManage> articles = contentManageMapper.selectDiscussList(uid, startTime, endTime);
        PageInfo<ContentManage> pageInfo = PageInfo.of(articles);
        PageInfo<ContentManageBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<ContentManageBO> contentManageBOS = pageInfo.getList().stream().map(contentManage -> {
            ContentManageBO contentManageBO = new ContentManageBO();
            BeanUtils.copyProperties(contentManage, contentManageBO);
            contentManageBO.setType(ContentTypeEnum.DISCUSS.getTypeName());
            contentManageBO.setMainType(ContentMainTypeEnum.TOPIC.getTypeName());
            // todo:加入数据统计
            contentManageBO.setCommentNumber(100);
            contentManageBO.setLikeNumber(100);
            contentManageBO.setReaderNumber(100);
            return contentManageBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(contentManageBOS);
        return boPageInfo;
    }

    @Override
    public PageInfo<CommentManageBO> commentArticle(PersonalCenterManageDTO personalCenterManageDTO) {
        LocalDate startTime = personalCenterManageDTO.getStartTime();
        LocalDate endTime = personalCenterManageDTO.getEndTime();
        long uid = tokenFeignService.getUid();
        PageHelper.startPage(personalCenterManageDTO.getPageNum(), personalCenterManageDTO.getPageSize());
        List<CommentManage> articles = commentManageMapper.selectArticleList(uid, startTime, endTime);
        PageInfo<CommentManage> pageInfo = PageInfo.of(articles);
        PageInfo<CommentManageBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<CommentManageBO> boList = articles.stream().map(commentManage -> {
            CommentManageBO commentManageBO = new CommentManageBO();
            BeanUtils.copyProperties(commentManage, commentManageBO);
            commentManageBO.setMainType(CommentMainTypeEnum.ARTICLE.getTypeName());
            commentManageBO.setTargetType(CommentTargetTypeEnum.ARTICLE.getTypeName());
            return commentManageBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(boList);
        return boPageInfo;
    }

    @Override
    public PageInfo<CommentManageBO> commentDiscuss(PersonalCenterManageDTO personalCenterManageDTO) {
        LocalDate startTime = personalCenterManageDTO.getStartTime();
        LocalDate endTime = personalCenterManageDTO.getEndTime();
        long uid = tokenFeignService.getUid();
        PageHelper.startPage(personalCenterManageDTO.getPageNum(), personalCenterManageDTO.getPageSize());
        List<CommentManage> discussList = commentManageMapper.selectDiscussList(uid, startTime, endTime);
        PageInfo<CommentManage> pageInfo = PageInfo.of(discussList);
        PageInfo<CommentManageBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);
        List<Long> topicIds = discussList.stream().map(CommentManage::getTitleId).distinct().collect(Collectors.toList());
        // 当前查出的讨论的父话题,避免3表联查
        LambdaQueryWrapper<Topic> queryWrapper = new QueryWrapper<Topic>().lambda();
        queryWrapper.select(Topic::getTopicId, Topic::getTitle).in(Topic::getTopicId, topicIds);
        List<Topic> topics = topicMapper.selectList(queryWrapper);
        List<CommentManageBO> boList = discussList.stream().map(commentManage -> {
            CommentManageBO commentManageBO = new CommentManageBO();
            BeanUtils.copyProperties(commentManage, commentManageBO);
            commentManageBO.setMainType(CommentMainTypeEnum.TOPIC.getTypeName());
            commentManageBO.setTargetType(CommentTargetTypeEnum.DISCUSS.getTypeName());
            // 讨论所归属的话题的id
            Long titleId = commentManage.getTitleId();
            for (Topic topic : topics) {
                if (topic.getTopicId().equals(titleId)) {
                    commentManageBO.setTitle(topic.getTitle());
                    break;
                }
            }
            return commentManageBO;
        }).collect(Collectors.toList());
        boPageInfo.setList(boList);
        return boPageInfo;
    }
}
