package com.heartape.hap.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Creator {

    @TableId(value = "uid", type = IdType.ASSIGN_ID)
    private Long uid;

    private String email;

    private String mobile;

    private String password;

    private String nickname;

    private String profile;

    private String avatar;

    private String role;

    /**账户状态*/
    @TableField(value = "account_status")
    private String accountStatus;

    @TableField(fill = FieldFill.INSERT, select = false)
    private Boolean status;

    @TableField(value = "created_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.IGNORED)
    private LocalDateTime createdTime;

//    @TableField(value = "updated_by", fill = FieldFill.UPDATE, select = false)
//    private Long updatedBy;

    @TableField(value = "updated_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    public Creator() {
    }

    public Creator(Long uid, String email, String mobile, String password, String nickname, String profile, String avatar, String role) {
        this.uid = uid;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.nickname = nickname;
        this.profile = profile;
        this.avatar = avatar;
        this.role = role;
    }
}
