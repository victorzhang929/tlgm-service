package com.cetiti.tlgm.service.common.model.performance.quota;

import java.io.Serializable;

import lombok.Data;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.MAX_SCORE;

/**
 * 基础数据维护得分绩效模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:26:20
 */
@Data
public class BasicDataMaintenancePerformance extends BasePerformance implements Serializable {

    private ResidencePerformance residence;
    private RealPopulationPerformance realPopulation;
    private EntOrgPerformance entOrg;
    private KeySitePerformance keySite;
    private KeyUnitPerformance keyUnit;
    private KeyFacilitiesPerformance keyFacilities;

    /**
     * 计算基础数据维护增长总数
     */
    private void calcIncreaseNum() {
        increaseNum = residence.getIncreaseNum() + realPopulation.getIncreaseNum()
                + entOrg.getIncreaseNum() + keyUnit.getIncreaseNum()
                + keySite.getIncreaseNum() + keyFacilities.getIncreaseNum();
    }

    /**
     * 计算基础数据维护修改总数
     */
    private void calcModificationNum() {
        modificationNum = residence.getModificationNum() + realPopulation.getModificationNum()
                + entOrg.getModificationNum() + keyUnit.getModificationNum()
                + keySite.getModificationNum() + keyFacilities.getModificationNum();
    }

    /**
     * 计算基础数据维护审核总数
     */
    private void calcCheckNum() {
        checkNum = residence.getCheckNum() + realPopulation.getCheckNum()
                + entOrg.getCheckNum() + keyUnit.getCheckNum()
                + keySite.getCheckNum() + keyFacilities.getCheckNum();
    }

    /**
     * 计算基础数据维护总分
     */
    @Override
    public void calcTotalScore() {
        calcIncreaseNum();
        calcModificationNum();
        calcCheckNum();
        super.calcTotalScore();
        //分数最大值为50分
        totalScore = totalScore > MAX_SCORE ? MAX_SCORE : totalScore;
    }
}
