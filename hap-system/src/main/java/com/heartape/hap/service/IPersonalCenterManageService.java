package com.heartape.hap.service;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.entity.bo.CommentManageBO;
import com.heartape.hap.entity.bo.ContentManageBO;
import com.heartape.hap.entity.dto.PersonalCenterManageDTO;

public interface IPersonalCenterManageService {
    /**
     * 内容管理->全部
     */
    PageInfo<ContentManageBO> contentAll(PersonalCenterManageDTO personalCenterManageDTO);

    /**
     * 内容管理->文章
     */
    PageInfo<ContentManageBO> contentArticle(PersonalCenterManageDTO personalCenterManageDTO);

    /**
     * 内容管理->话题
     */
    PageInfo<ContentManageBO> contentTopic(PersonalCenterManageDTO personalCenterManageDTO);

    /**
     * 内容管理->讨论
     */
    PageInfo<ContentManageBO> contentDiscuss(PersonalCenterManageDTO personalCenterManageDTO);

    /**
     * 评论管理->文章
     */
    PageInfo<CommentManageBO> commentArticle(PersonalCenterManageDTO personalCenterManageDTO);

    /**
     * 评论管理->讨论
     */
    PageInfo<CommentManageBO> commentDiscuss(PersonalCenterManageDTO personalCenterManageDTO);
}
