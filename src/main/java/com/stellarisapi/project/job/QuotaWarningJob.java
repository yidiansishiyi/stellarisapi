package com.stellarisapi.project.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class QuotaWarningJob {

    /*
     * 每小时定时检测接口配额
     */
    @Scheduled(fixedRate = 60 * 60 * 1000)
    void sendEmailAlerts() {

    }
}
