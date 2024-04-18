package com.stellarisapi.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口预警记录表
 * @TableName early_warning
 */
@TableName(value ="early_warning")
@Data
public class EarlyWarning implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 创建用户 ID
     */
    private Long createUserId;

    /**
     * 用户邮箱
     */
    private String userMailbox;

    /**
     * 预警文本
     */
    private String warningText;

    /**
     * 发送状态
     */
    private String sendingStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}