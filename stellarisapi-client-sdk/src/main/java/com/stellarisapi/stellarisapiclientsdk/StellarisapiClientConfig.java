package com.stellarisapi.stellarisapiclientsdk;

import com.stellarisapi.stellarisapiclientsdk.client.StellarisapiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * stellarisapi 客户端配置
 *
 
 */
@Configuration
@ConfigurationProperties("stellarisapi.client")
@Data
@ComponentScan
public class StellarisapiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public StellarisapiClient stellarisapiClient() {
        return new StellarisapiClient(accessKey, secretKey);
    }

}
