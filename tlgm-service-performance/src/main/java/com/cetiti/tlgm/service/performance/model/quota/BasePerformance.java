package com.cetiti.tlgm.service.performance.model.quota;

import java.io.Serializable;

import lombok.Data;

import static com.cetiti.tlgm.service.common.CommonUtil.getDoubleScore;
import static com.cetiti.tlgm.service.common.CommonUtil.getLongNumber;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.CHECK_WEIGHT;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.INCREASE_WEIGHT;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.MODIFICATION_WEIGHT;

/**
 * 基础数据维护绩效基类模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:09:52
 */
@Data
public class BasePerformance implements Serializable{

    protected Long increaseNum;
    protected Long modificationNum;
    protected Long checkNum;
    protected Double totalScore;

    public BasePerformance(){}

    public BasePerformance(long increaseNum, long modificationNum, long checkNum) {
        this.increaseNum = increaseNum;
        this.modificationNum = modificationNum;
        this.checkNum = checkNum;
        calcTotalScore();
    }

    public Long getIncreaseNum() {
        return getLongNumber(increaseNum);
    }

    public Long getModificationNum() {
        return getLongNumber(modificationNum);
    }

    public Long getCheckNum() {
        return getLongNumber(checkNum);
    }

    public Double getTotalScore() {
        return getDoubleScore(totalScore);
    }

    protected void  calcTotalScore() {
        totalScore = getIncreaseNum() * INCREASE_WEIGHT + getModificationNum() * MODIFICATION_WEIGHT + checkNum * CHECK_WEIGHT;
    }
}
