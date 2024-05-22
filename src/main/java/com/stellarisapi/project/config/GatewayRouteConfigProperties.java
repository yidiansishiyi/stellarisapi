package com.stellarisapi.project.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : jiagang
 * @date : Created in 2022/7/20 14:44
 */
@ConfigurationProperties(prefix = "gateway.routes.config")
@Component
@Data
public class GatewayRouteConfigProperties {

    private String dataId;

    private String group;
  
    private String namespace;
}