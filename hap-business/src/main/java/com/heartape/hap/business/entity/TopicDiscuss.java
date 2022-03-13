package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author heartape
 * @since 2022-03-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TopicDiscuss extends BaseEntity {

    @TableId(value = "discuss_id", type = IdType.ASSIGN_ID)
    private Long discussId;

    @TableField("uid")
    private Long uid;

    @TableField("topic_id")
    private Long topicId;

    @TableField("content")
    private String content;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField(value = "top",typeHandler = JacksonTypeHandler.class)
    private Top top;

    @TableField("publish_time")
    private LocalDateTime publishTime;


}
