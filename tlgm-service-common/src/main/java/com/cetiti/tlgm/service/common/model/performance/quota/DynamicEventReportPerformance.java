package com.cetiti.tlgm.service.common.model.performance.quota;

import java.io.Serializable;

import lombok.Data;

import static com.cetiti.tlgm.service.common.util.CommonUtil.getDoubleWithDefaultValue;
import static com.cetiti.tlgm.service.common.util.CommonUtil.getLongWithDefaultValue;

/**
 * 动态事件上报模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 16:23:55
 */
@Data
public class DynamicEventReportPerformance implements Serializable {

    /**
     * 天阙用户名对应loginname
     */
    private String username;
    /**
     * 有效事件
     */
    private Long effectNumber;
    /**
     * 有效事件得分
     */
    private Double effectScore;
    /**
     * 动态事件
     */
    private Long dynamicNumber;
    /**
     * 动态事件得分
     */
    private Double dynamicScore;
    /**
     * 未标记事件
     */
    private Long ndEffectiveNumber;
    /**
     * 其他事件
     */
    private Long otherNumber;
    private Long totalNumber;
    private Double totalScore;

    public Long getEffectNumber() {
        return getLongWithDefaultValue(effectNumber);
    }

    public Double getEffectScore() {
        return getDoubleWithDefaultValue(effectScore);
    }

    public Long getDynamicNumber() {
        return getLongWithDefaultValue(dynamicNumber);
    }

    public Double getDynamicScore() {
        return getDoubleWithDefaultValue(dynamicScore);
    }

    public Long getNdEffectiveNumber() {
        return getLongWithDefaultValue(ndEffectiveNumber);
    }

    public Long getOtherNumber() {
        return getLongWithDefaultValue(otherNumber);
    }

    public Long getTotalNumber() {
        return getLongWithDefaultValue(totalNumber);
    }

    public Double getTotalScore() {
        return getDoubleWithDefaultValue(totalScore);
    }
}
