package com.cetiti.tlgm.service.common.model.performance.quota;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.mapper.PerformanceTaskMapper;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.KEY_UNIT;

/**
 * 重点单位基础数据维护得分绩效模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:21:04
 */
public class KeyUnitPerformance extends BasePerformance implements Serializable {

    public KeyUnitPerformance() {}

    public KeyUnitPerformance(PerformanceTaskMapper performanceTaskMapper, BigDecimal userId) throws Exception {
        countPerformance(performanceTaskMapper, userId, KEY_UNIT);
    }
}
