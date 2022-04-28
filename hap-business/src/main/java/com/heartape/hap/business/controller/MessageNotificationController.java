package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.MessageNotificationBO;
import com.heartape.hap.business.entity.dto.MessageNotificationProducerDTO;
import com.heartape.hap.business.entity.ro.MessageNotificationRO;
import com.heartape.hap.business.response.Result;
import com.heartape.hap.business.service.IMessageNotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 消息通知
 */
@RestController
@RequestMapping("/business/message")
@Api(tags = "消息通知")
public class MessageNotificationController {

    @Autowired
    private IMessageNotificationService messageNotificationService;

    @PostMapping("/like")
    @ApiOperation("触发点赞消息通知")
    public Result like(@RequestBody MessageNotificationRO messageNotificationRO) {
        MessageNotificationProducerDTO messageNotificationProducerDTO = new MessageNotificationProducerDTO();
        BeanUtils.copyProperties(messageNotificationRO, messageNotificationProducerDTO);
        messageNotificationService.likeProducer(messageNotificationProducerDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("获取消息通知列表")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<MessageNotificationBO> messageNotificationSimple = messageNotificationService.list(pageNum, pageSize);
        return Result.success(messageNotificationSimple);
    }

    @PutMapping
    @ApiOperation("设置消息通知已读")
    public Result read(@RequestParam Long messageId) {
        return Result.success();
    }

    @PutMapping("/creator")
    @ApiOperation("设置某个用户所有消息通知已读")
    public Result readAll(@RequestParam Long uid) {
        return Result.success();
    }
}
