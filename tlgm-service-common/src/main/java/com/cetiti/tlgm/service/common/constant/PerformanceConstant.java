package com.cetiti.tlgm.service.common.constant;

import static com.cetiti.tlgm.service.common.constant.ModuleConstant.BUSSTATION;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.ENTERPRISE;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.FIREHYDRANT;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.FOREIGNER;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.GASSTATION;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.HOSPITAL;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.INTERNETBAR;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.MIGRANT;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.OILSTATION;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.PERMANENT;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.RELIGIOUS;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.SCHOOL;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.SOCIALORGANIZE;
import static com.cetiti.tlgm.service.common.constant.ModuleConstant.SUBSTATION;

/**
 * 绩效常量
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 14:46:15
 */
public class PerformanceConstant {

    /**
     * 绩效新增权重
     */
    public final static double INCREASE_WEIGHT = 1.0;
    /**
     * 新增数目
     */
    public final static String INCREASE_NUM = "increaseNum";
    /**
     * 绩效修改权重
     */
    public final static double MODIFICATION_WEIGHT = 0.5;
    /**
     * 更新数目
     */
    public final static String MODIFICATION_NUM = "modificationNum";
    /**
     * 绩效核查权重
     */
    public final static double CHECK_WEIGHT = 0.2;
    /**
     * 核查数目
     */
    public final static String CHECK_NUM = "checkNum";
    /**
     * 其他
     */
    public final static String OTHERS = "others";

    /**
     * 动态事件得分和基础数据维护得分月绩效最高分均是50分
     */
    public static final Double MAX_SCORE = 50.0;

    /**
     * 天阙计算动态事件地址
     */
    public final static String TIAN_QUE_DYNAMIC_EVENT_REPORT_URL = "http://60.191.18.40:9010/issueEffectiveStat/findIssueEffectiveDate.action";

    /**
     * 实有房屋模块
     */
    public final static int[] RESIDENCE = {ModuleConstant.RESIDENCE};
    /**
     * 实有人口模块
     */
    public final static int[] REAL_POPULATION = {PERMANENT, MIGRANT, FOREIGNER};
    /**
     * 企业组织模块
     */
    public final static int[] ENT_ORG = {ENTERPRISE, SOCIALORGANIZE};
    /**
     * 重点单位模块
     */
    public final static int[] KEY_UNIT = {SCHOOL, HOSPITAL, RELIGIOUS};
    /**
     * 重点场所模块
     */
    public final static int[] KEY_SITE = {INTERNETBAR, GASSTATION, OILSTATION, BUSSTATION};
    /**
     * 重点设施模块
     */
    public final static int[] KEY_FACILITIES = {SUBSTATION, FIREHYDRANT};
}
