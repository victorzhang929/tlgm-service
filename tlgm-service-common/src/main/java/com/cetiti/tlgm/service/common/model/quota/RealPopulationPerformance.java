package com.cetiti.tlgm.service.common.model.quota;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.mapper.PerformanceTaskMapper;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.REAL_POPULATION;

/**
 * 实有人口基础数据维护得分模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:16:23
 */
public class RealPopulationPerformance extends BasePerformance implements Serializable {

    public RealPopulationPerformance() {}

    public RealPopulationPerformance(PerformanceTaskMapper performanceTaskMapper, BigDecimal userId) throws Exception {
        countPerformance(performanceTaskMapper, userId, REAL_POPULATION);
    }
}
