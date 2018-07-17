package com.cetiti.tlgm.service.common.model.quota;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.mapper.PerformanceTaskMapper;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.RESIDENCE;


/**
 * 实有房屋基础数据维护得分绩效模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:15:29
 */
public class ResidencePerformance extends BasePerformance implements Serializable {

    public ResidencePerformance() {}

    public ResidencePerformance(PerformanceTaskMapper performanceTaskMapper, BigDecimal userId) throws Exception {
        countPerformance(performanceTaskMapper, userId, RESIDENCE);
    }
}
