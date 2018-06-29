package com.cetiti.tlgm.service.performance.model.quota;

import java.io.Serializable;

import lombok.Data;

import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.MAX_SCORE;

/**
 * 基础数据维护得分绩效模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:26:20
 */
@Data
public class BasicDataMaintenancePerformance extends BasePerformance implements Serializable {

    private ResidencePerformance residencePerformance = new ResidencePerformance();
    private RealPopulationPerformance realPopulationPerformance = new RealPopulationPerformance();
    private EntOrgPerformance entOrgPerformance = new EntOrgPerformance();
    private KeyUnitPerformance keyUnitPerformance = new KeyUnitPerformance();
    private KeySitePerformance keySitePerformance = new KeySitePerformance();
    private KeyFacilitiesPerformance keyFacilitiesPerformance = new KeyFacilitiesPerformance();

    /**
     * 计算基础数据维护增长总数
     */
    private void calcIncreaseNum() {
        increaseNum = residencePerformance.getIncreaseNum() + realPopulationPerformance.getIncreaseNum()
                + entOrgPerformance.getIncreaseNum() + keyUnitPerformance.getIncreaseNum()
                + keySitePerformance.getIncreaseNum() + keyFacilitiesPerformance.getIncreaseNum();
    }

    /**
     * 计算基础数据维护修改总数
     */
    private void calcModificationNum() {
        modificationNum = residencePerformance.getModificationNum() + realPopulationPerformance.getModificationNum()
                + entOrgPerformance.getModificationNum() + keyUnitPerformance.getModificationNum()
                + keySitePerformance.getModificationNum() + keyFacilitiesPerformance.getModificationNum();
    }

    /**
     * 计算基础数据维护审核总数
     */
    private void calcCheckNum() {
        checkNum = residencePerformance.getCheckNum() + realPopulationPerformance.getCheckNum()
                + entOrgPerformance.getCheckNum() + keyUnitPerformance.getCheckNum()
                + keySitePerformance.getCheckNum() + keyFacilitiesPerformance.getCheckNum();
    }

    /**
     * 计算基础数据维护总分
     */
    @Override
    public void calcTotalScore() {
        calcCheckNum();
        calcModificationNum();
        calcCheckNum();
        super.calcTotalScore();
        //分数最大值为50分
        totalScore = totalScore > MAX_SCORE ? MAX_SCORE : totalScore;
    }
}
