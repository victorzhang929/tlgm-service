package com.cetiti.tlgm.service.common.model.performance.quota;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.mapper.PerformanceTaskMapper;
import lombok.Data;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.CHECK_WEIGHT;
import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.INCREASE_WEIGHT;
import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.MODIFICATION_WEIGHT;
import static com.cetiti.tlgm.service.common.util.CommonUtil.getDoubleWithDefaultValue;
import static com.cetiti.tlgm.service.common.util.CommonUtil.getLongWithDefaultValue;

/**
 * 基础数据维护绩效基类模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:09:52
 */
@Data
public class BasePerformance implements Serializable{

    protected Long increaseNum = 0L;
    protected Long modificationNum = 0L;
    protected Long checkNum = 0L;
    protected Double totalScore = 0.0;

    public BasePerformance(){}

    /**
     * 计算模块列表的当前月份绩效信息
     * @param performanceTaskMapper
     * @param userId
     * @param moduleTypes
     * @throws Exception
     */
    protected void countPerformance(PerformanceTaskMapper performanceTaskMapper, BigDecimal userId, int[] moduleTypes)
            throws Exception {
        for (int moduleType : moduleTypes) {
            BasePerformance result = performanceTaskMapper.countPerformance(userId, moduleType);
            increaseNum += result.getIncreaseNum();
            modificationNum += result.getModificationNum();
            checkNum += result.getCheckNum();
        }
        calcTotalScore();
    }

    public Long getIncreaseNum() {
        return getLongWithDefaultValue(increaseNum);
    }

    public Long getModificationNum() {
        return getLongWithDefaultValue(modificationNum);
    }

    public Long getCheckNum() {
        return getLongWithDefaultValue(checkNum);
    }

    public Double getTotalScore() {
        return getDoubleWithDefaultValue(totalScore);
    }

    protected void  calcTotalScore() {
        totalScore = getIncreaseNum() * INCREASE_WEIGHT + getModificationNum() * MODIFICATION_WEIGHT + checkNum * CHECK_WEIGHT;
    }
}
