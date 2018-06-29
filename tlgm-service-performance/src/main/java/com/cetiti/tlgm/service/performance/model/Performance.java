package com.cetiti.tlgm.service.performance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.performance.model.quota.BasicDataMaintenancePerformance;
import com.cetiti.tlgm.service.performance.model.quota.DynamicEventReportPerformance;
import lombok.Data;

import static com.cetiti.tlgm.service.common.CommonUtil.getDoubleScore;

/**
 * 专职网格员绩效模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 15:38:01
 */
@Data
public class Performance extends FullTimeGridMember implements Serializable{

    private BigDecimal id;

    private DynamicEventReportPerformance dynamicEventReportPerformance = new DynamicEventReportPerformance();
    private BasicDataMaintenancePerformance basicDataMaintenancePerformance = new BasicDataMaintenancePerformance();

    private Double totalScore;

    public void calcTotalScore() {
        totalScore = dynamicEventReportPerformance.getTotalScore() + basicDataMaintenancePerformance.getTotalScore();
    }

    public Double getTotalScore() {
        return getDoubleScore(totalScore);
    }
}
