package com.stellarisapi.project.job;

import com.stellarisapi.project.service.EarlyWarningService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class QuotaWarningJob {

    @Resource
    private EarlyWarningService earlyWarningService;

    /*
     * 每小时定时检测接口配额
     */
    @Scheduled(fixedRate =  60 * 1000)
    void sendEmailAlerts() {
        earlyWarningService.sendWarningMessage();
    }
}
