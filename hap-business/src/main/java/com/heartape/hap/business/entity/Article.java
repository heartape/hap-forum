package com.heartape.hap.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.heartape.hap.business.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author heartape
 * @since 2022-03-12
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "Article对象", description = "")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "article_id", type = IdType.ASSIGN_ID)
    private Long articleId;

    @TableField("title")
    private String title;

    @TableField("content")
    private String content;

    @TableField("uid")
    private Long uid;

    @ApiModelProperty("多个labelId组成的jsonArray")
    @TableField(value = "label_id",typeHandler = JacksonTypeHandler.class)
    private List<Long> labelId;

    @ApiModelProperty("置顶条件，根据不同的搜索条件判断是否置顶（暂不实现）")
    @TableField("top")
    private String top;

    @TableField("publish_time")
    private LocalDateTime publishTime;

}
