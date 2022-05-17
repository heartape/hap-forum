package com.heartape.hap.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.PrivateLetter;
import com.heartape.hap.business.entity.bo.PrivateLetterBO;
import com.heartape.hap.business.entity.bo.PrivateLetterCreatorBO;
import com.heartape.hap.business.entity.bo.PrivateLetterSimpleBO;
import com.heartape.hap.business.entity.dto.PrivateLetterDTO;
import com.heartape.hap.business.feign.TokenFeignServiceImpl;
import com.heartape.hap.business.mapper.PrivateLetterMapper;
import com.heartape.hap.business.service.IPrivateLetterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivateLetterServiceImpl extends ServiceImpl<PrivateLetterMapper, PrivateLetter> implements IPrivateLetterService {

    @Autowired
    private TokenFeignServiceImpl tokenFeignService;

    @Override
    public void create(PrivateLetterDTO privateLetterDTO) {
        // todo:验证用户之间有没有拉黑

        String content = privateLetterDTO.getContent();
        String simpleContent = content.length() > 16 ? content.substring(0,16) : content;

        PrivateLetter privateLetter = new PrivateLetter();
        BeanUtils.copyProperties(privateLetterDTO, privateLetter);
        privateLetter.setSimpleContent(simpleContent);
        privateLetter.setLookUp(false);
        baseMapper.insert(privateLetter);
    }

    @Override
    public PageInfo<PrivateLetterSimpleBO> simple(Long targetUid, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<PrivateLetter> queryWrapper = new QueryWrapper<PrivateLetter>().lambda();
        queryWrapper
                .select(PrivateLetter::getLetterId, PrivateLetter::getUid, PrivateLetter::getAvatar, PrivateLetter::getNickname, PrivateLetter::getSimpleContent, PrivateLetter::getLookUp, PrivateLetter::getCreatedTime)
                .eq(PrivateLetter::getTargetUid, targetUid)
                .orderByDesc(PrivateLetter::getCreatedTime);
        PageHelper.startPage(pageNum, pageSize);
        List<PrivateLetter> privateLetters = baseMapper.selectList(queryWrapper);
        PageInfo<PrivateLetter> pageInfo = PageInfo.of(privateLetters);
        PageInfo<PrivateLetterSimpleBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);

        List<PrivateLetterSimpleBO> boList = privateLetters.stream().map(privateLetter -> {
            PrivateLetterSimpleBO privateLetterSimpleBO = new PrivateLetterSimpleBO();
            BeanUtils.copyProperties(privateLetter, privateLetterSimpleBO);
            return privateLetterSimpleBO;
        }).collect(Collectors.toList());

        boPageInfo.setList(boList);
        return boPageInfo;
    }

    @Override
    public PageInfo<PrivateLetterCreatorBO> creatorList(Integer pageNum, Integer pageSize) {
        Long targetUid = tokenFeignService.getUid();
        // todo:过滤已被拉黑的用户，创建用户关系表
        List<Long> blacklist = new ArrayList<>();
        blacklist.add(2L);

        PageHelper.startPage(pageNum, pageSize);
        List<PrivateLetter> privateLetters = baseMapper.selectCreatorList(targetUid, blacklist);
        PageInfo<PrivateLetter> pageInfo = PageInfo.of(privateLetters);
        PageInfo<PrivateLetterCreatorBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);

        List<Long> uid = privateLetters.stream().map(PrivateLetter::getUid).distinct().collect(Collectors.toList());
        List<PrivateLetter> unreadList = query().select("uid", "count(*) unread").in("uid", uid).eq("target_uid", targetUid)
                .eq("look_up", false).groupBy("uid").list();
        List<PrivateLetterCreatorBO> creatorBOList = privateLetters.stream().map(privateLetter -> {
            PrivateLetterCreatorBO creatorBO = new PrivateLetterCreatorBO();
            BeanUtils.copyProperties(privateLetter, creatorBO);

            for (PrivateLetter letter : unreadList) {
                if (letter.getUid().equals(privateLetter.getUid())) {
                    creatorBO.setUnread(letter.getUnread());
                    break;
                }
            }
            return creatorBO;
        }).collect(Collectors.toList());

        boPageInfo.setList(creatorBOList);
        return boPageInfo;
    }

    @Override
    public PageInfo<PrivateLetterBO> detail(Long uid, Integer pageNum, Integer pageSize) {
        Long targetUid = tokenFeignService.getUid();

        LambdaQueryWrapper<PrivateLetter> queryWrapper = new QueryWrapper<PrivateLetter>().lambda();
        queryWrapper
                .select(PrivateLetter::getLetterId, PrivateLetter::getContent, PrivateLetter::getLookUp, PrivateLetter::getCreatedTime)
                .eq(PrivateLetter::getUid, uid).eq(PrivateLetter::getTargetUid, targetUid);
        PageHelper.startPage(pageNum, pageSize);
        List<PrivateLetter> privateLetters = baseMapper.selectList(queryWrapper);
        PageInfo<PrivateLetter> pageInfo = PageInfo.of(privateLetters);
        PageInfo<PrivateLetterBO> boPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(pageInfo, boPageInfo);

        List<PrivateLetterBO> privateLetterBOS = privateLetters.stream().map(privateLetter -> {
            PrivateLetterBO privateLetterBO = new PrivateLetterBO();
            BeanUtils.copyProperties(privateLetter, privateLetterBO);
            return privateLetterBO;
        }).collect(Collectors.toList());

        boPageInfo.setList(privateLetterBOS);
        return boPageInfo;
    }

    @Override
    public void readOne(Long uid) {
        Long targetUid = tokenFeignService.getUid();

        LambdaUpdateWrapper<PrivateLetter> updateWrapper = new UpdateWrapper<PrivateLetter>().lambda();
        updateWrapper
                .set(PrivateLetter::getLookUp, true)
                .eq(PrivateLetter::getUid, uid).eq(PrivateLetter::getTargetUid, targetUid).eq(PrivateLetter::getLookUp, false);
        baseMapper.update(null, updateWrapper);
    }

    @Override
    public void readAll() {
        Long targetUid = tokenFeignService.getUid();
        LambdaUpdateWrapper<PrivateLetter> updateWrapper = new UpdateWrapper<PrivateLetter>().lambda();
        updateWrapper
                .set(PrivateLetter::getLookUp, true)
                .eq(PrivateLetter::getTargetUid, targetUid).eq(PrivateLetter::getLookUp, false);
        baseMapper.update(null, updateWrapper);
    }
}
