package com.stellarisapi.project.model.dto.earlyWarning;

import lombok.Data;

@Data
public class EarlyWarningAddRequest {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户邮箱
     */
    private String userMailbox;

    /**
     * 预警文本
     */
    private String warningText;

}
