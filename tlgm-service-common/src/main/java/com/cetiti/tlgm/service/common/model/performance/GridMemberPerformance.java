package com.cetiti.tlgm.service.common.model.performance;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.model.GridCommunityTownship;
import com.cetiti.tlgm.service.common.model.performance.quota.BasicDataMaintenancePerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.DynamicEventReportPerformance;
import lombok.Data;

import static com.cetiti.tlgm.service.common.util.CommonUtil.getDoubleWithDefaultValue;

/**
 * 专职网格员绩效模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 15:38:01
 */
@Data
public class GridMemberPerformance extends GridCommunityTownship implements Serializable {

    private BigDecimal id;
    private BigDecimal userId;
    private String name;
    private String loginname;
    private Integer count;

    private DynamicEventReportPerformance dynamic = new DynamicEventReportPerformance();
    private BasicDataMaintenancePerformance basic = new BasicDataMaintenancePerformance();

    private Double totalScore;

    public void calcTotalScore() {
        totalScore = dynamic.getTotalScore() + basic.getTotalScore();
    }

    public Double getTotalScore() {
        return getDoubleWithDefaultValue(totalScore);
    }
}
