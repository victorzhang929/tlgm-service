package com.cetiti.tlgm.service.performance.model.quota;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.performance.mapper.PerformanceTaskMapper;

import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.ENT_ORG;

/**
 * 企业组织基础数据维护得分绩效模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:18:12
 */
public class EntOrgPerformance extends BasePerformance implements Serializable {

    public EntOrgPerformance() {}

    public EntOrgPerformance(PerformanceTaskMapper performanceTaskMapper, BigDecimal userId) throws Exception {
        countPerformance(performanceTaskMapper, userId, ENT_ORG);
    }

}
