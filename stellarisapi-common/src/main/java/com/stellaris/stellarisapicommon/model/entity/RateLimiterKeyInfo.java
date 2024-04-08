package com.stellaris.stellarisapicommon.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateLimiterKeyInfo implements Serializable {

    /**
     * 请求路径
     */
    private String url;

    /**
     * 用户唯一标识
     */
    private String userSign;

    /**
     * 请求方式
     */
    private String requestMethod;

    /**
     * 限流场景 1.接口限流 2.用户维度限流
     */
    private Integer scene;

}