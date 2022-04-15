package com.heartape.hap.business.controller;

import com.github.pagehelper.PageInfo;
import com.heartape.hap.business.entity.bo.MessageNotificationBO;
import com.heartape.hap.business.entity.dto.MessageNotificationDTO;
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

    /**
     * 创建点赞消息
     * 用于服务间调用
     */
    @PostMapping("/like")
    @ApiOperation("创建消息通知")
    public Result createLike(@RequestBody MessageNotificationRO messageNotificationRO) {
        MessageNotificationDTO messageNotificationDTO = new MessageNotificationDTO();
        BeanUtils.copyProperties(messageNotificationRO, messageNotificationDTO);
        messageNotificationService.createLike(messageNotificationDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("获取消息通知列表")
    public Result list(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<MessageNotificationBO> messageNotificationSimple = messageNotificationService.list(pageNum, pageSize);
        return Result.success(messageNotificationSimple);
    }
}
