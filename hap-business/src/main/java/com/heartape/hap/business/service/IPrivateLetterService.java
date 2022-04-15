package com.heartape.hap.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.PrivateLetter;
import com.heartape.hap.business.entity.bo.PrivateLetterBO;
import com.heartape.hap.business.entity.bo.PrivateLetterCreatorBO;
import com.heartape.hap.business.entity.bo.PrivateLetterSimpleBO;
import com.heartape.hap.business.entity.dto.PrivateLetterDTO;

public interface IPrivateLetterService extends IService<PrivateLetter> {
    /**
     * 创建私信
     */
    void create(PrivateLetterDTO privateLetterDTO);

    /**
     * 获取私信简要信息列表
     */
    PageInfo<PrivateLetterSimpleBO> simple(Long targetUid, Integer pageNum, Integer pageSize);

    /**
     * 获取私信详细信息私信用户列表
     */
    PageInfo<PrivateLetterCreatorBO> creatorList(Integer pageNum, Integer pageSize);

    /**
     * 详细信息，某私信用户的所有私信内容
     */
    PageInfo<PrivateLetterBO> detail(Long uid, Integer pageNum, Integer pageSize);

    /**
     * 将单个用户的私信全部设置为已读
     */
    void readOne(Long uid);

    /**
     * 将所有私信全部设置为已读
     */
    void readAll();

}
