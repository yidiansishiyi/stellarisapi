package com.stellarisapi.project.model.dto.rateLimiterAllocation;

import com.stellaris.stellarisapicommon.model.entity.RateLimiterAllocation;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @author sanqi
 *   
 */
@Data
public class RateLimiterAllocationUpdateRequest implements Serializable {

    private Long id;

    private String roadSigns;

    private Long rate;

    private Long rateInterval;

    private String rateIntervalUnit;

    private String remark;

    private Integer scene;

    private String state;

    private Integer usageStatus;

    private static final long serialVersionUID = 1L;

    public RateLimiterAllocation getNewEntity(RateLimiterAllocation rateLimiterAllocation) {
        String state = this.getState();
        if (StringUtils.isNotBlank(state)) {
            rateLimiterAllocation.setState(state);
        }
        Integer scene = this.getScene();
        if (scene != null) {
            rateLimiterAllocation.setScene(scene);
        }
        String roadSigns = this.getRoadSigns();
        if (StringUtils.isNotBlank(roadSigns)) {
            rateLimiterAllocation.setRoadSigns(roadSigns);
        }
        Long rate = this.getRate();
        if (rate != null) {
            rateLimiterAllocation.setRate(rate);
        }
        Long rateInterval = this.getRateInterval();
        if (rateInterval != null) {
            rateLimiterAllocation.setRateInterval(rateInterval);
        }
        String rateIntervalUnit = this.getRateIntervalUnit();
        if (StringUtils.isNotBlank(rateIntervalUnit)) {
            rateLimiterAllocation.setRateIntervalUnit(rateIntervalUnit);
        }
        
        return rateLimiterAllocation;
    }

}