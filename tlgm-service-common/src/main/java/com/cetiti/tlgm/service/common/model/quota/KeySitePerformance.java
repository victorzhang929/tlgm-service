package com.cetiti.tlgm.service.common.model.quota;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.mapper.PerformanceTaskMapper;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.KEY_SITE;

/**
 * 重点场所
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 17:19:19
 */
public class KeySitePerformance extends BasePerformance implements Serializable {

    public KeySitePerformance() {}

    public KeySitePerformance(PerformanceTaskMapper performanceTaskMapper, BigDecimal userId) throws Exception {
        countPerformance(performanceTaskMapper, userId, KEY_SITE);
    }
}
