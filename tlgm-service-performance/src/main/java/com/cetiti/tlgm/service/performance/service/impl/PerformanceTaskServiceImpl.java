package com.cetiti.tlgm.service.performance.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetiti.tlgm.service.common.CommonUtil;
import com.cetiti.tlgm.service.performance.mapper.GridCommunityTownshipMapper;
import com.cetiti.tlgm.service.performance.mapper.OracleOperationMapper;
import com.cetiti.tlgm.service.performance.mapper.PerformanceTaskMapper;
import com.cetiti.tlgm.service.performance.model.FullTimeGridMember;
import com.cetiti.tlgm.service.performance.model.Performance;
import com.cetiti.tlgm.service.performance.model.quota.BasePerformance;
import com.cetiti.tlgm.service.performance.model.quota.BasicDataMaintenancePerformance;
import com.cetiti.tlgm.service.performance.model.quota.DynamicEventReportPerformance;
import com.cetiti.tlgm.service.performance.model.quota.EntOrgPerformance;
import com.cetiti.tlgm.service.performance.model.quota.KeyFacilitiesPerformance;
import com.cetiti.tlgm.service.performance.model.quota.KeySitePerformance;
import com.cetiti.tlgm.service.performance.model.quota.KeyUnitPerformance;
import com.cetiti.tlgm.service.performance.model.quota.RealPopulationPerformance;
import com.cetiti.tlgm.service.performance.model.quota.ResidencePerformance;
import com.cetiti.tlgm.service.performance.service.PerformanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.ENT_ORG;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.KEY_FACILITIES;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.KEY_SITE;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.KEY_UNIT;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.REAL_POPULATION;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.RESIDENCE;
import static com.cetiti.tlgm.service.performance.constant.PerformanceConstant.TIAN_QUE_DYNAMIC_EVENT_REPORT_URL;

/**
 * 绩效业务接口实现类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 14:50:16
 */
@Service("performanceTaskService")
@Slf4j
public class PerformanceTaskServiceImpl implements PerformanceTaskService {

    @Autowired
    private GridCommunityTownshipMapper gridCommunityTownshipMapper;
    @Autowired
    private OracleOperationMapper oracleOperationMapper;
    @Autowired
    private PerformanceTaskMapper performanceTaskMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertFullTimeGridMembersPerMonth() throws Exception {
        List<FullTimeGridMember> fullTimeGridMembers = performanceTaskMapper.listFullTimeGridMember();
        for (FullTimeGridMember fullTimeGridMember : fullTimeGridMembers) {
            insert(fullTimeGridMember);
        }
    }

    private void insert(FullTimeGridMember fullTimeGridMember) throws Exception {
        fullTimeGridMember.fixAllName(gridCommunityTownshipMapper);
        log.info("insert full-time grid member's performance: {}", fullTimeGridMember.toString());
        performanceTaskMapper.insertWithoutPerformance(fullTimeGridMember);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateFullTimeGridMembersPerHour() throws Exception {
        String currentMonth = oracleOperationMapper.getCurrentMonth();
        List<Performance> performances = calcPerformance(currentMonth);
        List<BigDecimal> userIdsInDB = performanceTaskMapper.listCurrentMonthUserId(currentMonth);
        BigDecimal userId;
        for (Performance performance : performances) {
            userId = performance.getUserId();
            for (BigDecimal userIdInDB : userIdsInDB) {
                //如果userId在数据库中存在，则更新
                if (userIdInDB.equals(userId)) {
                    performance.fixAllName(gridCommunityTownshipMapper);
                    performanceTaskMapper.saveOrUpdate(performance, currentMonth);
                    userIdsInDB.remove(userIdInDB);
                    break;
                }
            }
        }
        performanceTaskMapper.batchDeleteUserIds(userIdsInDB, currentMonth);
    }

    /**
     * 计算当前月份绩效
     *
     * @param currentMonth
     * @return
     * @throws Exception
     */
    private List<Performance> calcPerformance(String currentMonth) throws Exception {

        List<FullTimeGridMember> fullTimeGridMembers = performanceTaskMapper.listFullTimeGridMember();
        List<Performance> performances = new ArrayList<>(fullTimeGridMembers.size());

        Timestamp currentMonthFirstDay = oracleOperationMapper.getCurrentMonthFirstDay();
        Timestamp afterMonthFirstDay = oracleOperationMapper.getAfterMonthFirstDay();
        List<DynamicEventReportPerformance> dynamicEventReportPerformances =
                getDynamicEventReportPerformanceFormTianque(currentMonthFirstDay, afterMonthFirstDay);

        for (FullTimeGridMember fullTimeGridMember : fullTimeGridMembers) {
            Performance performance = new Performance();
            performance.setBasicDataMaintenancePerformance(constructPerformance(fullTimeGridMember.getUserId(), currentMonth));
            if (null == dynamicEventReportPerformances && !dynamicEventReportPerformances.isEmpty()) {
                for (DynamicEventReportPerformance dynamicEventReportPerformance : dynamicEventReportPerformances) {
                    //天阙username对应全科网格loginname
                    if (StringUtils.compare(dynamicEventReportPerformance.getUsername(), fullTimeGridMember.getLoginname()) == 0) {
                        performance.setDynamicEventReportPerformance(dynamicEventReportPerformance);
                        dynamicEventReportPerformances.remove(dynamicEventReportPerformance);
                        break;
                    }
                }
            }
            performance.calcTotalScore();
            performances.add(performance);
        }
        return performances;
    }

    /**
     * 计算专职网格员当前月份基础数据维护绩效得分
     * @param userId 用户id
     * @param currentMonth 当前月份
     * @return
     * @throws Exception
     */
    private BasicDataMaintenancePerformance constructPerformance(BigDecimal userId, String currentMonth) throws Exception {
        BasicDataMaintenancePerformance basicDataMaintenancePerformance = new BasicDataMaintenancePerformance();

        basicDataMaintenancePerformance.setResidencePerformance((ResidencePerformance) countPerformance(userId, currentMonth, RESIDENCE));
        basicDataMaintenancePerformance.setRealPopulationPerformance((RealPopulationPerformance) countPerformance(userId, currentMonth, REAL_POPULATION));
        basicDataMaintenancePerformance.setEntOrgPerformance((EntOrgPerformance) countPerformance(userId, currentMonth, ENT_ORG));
        basicDataMaintenancePerformance.setKeyUnitPerformance((KeyUnitPerformance) countPerformance(userId, currentMonth, KEY_UNIT));
        basicDataMaintenancePerformance.setKeySitePerformance((KeySitePerformance) countPerformance(userId, currentMonth, KEY_SITE));
        basicDataMaintenancePerformance.setKeyFacilitiesPerformance((KeyFacilitiesPerformance) countPerformance(userId, currentMonth, KEY_FACILITIES));

        basicDataMaintenancePerformance.calcTotalScore();
        return basicDataMaintenancePerformance;
    }

    /**
     * 计算专职网格员大模块各个子模块当前月份绩效总和
     * @param userId 用户id
     * @param currentMonth 当前月份
     * @param moduleTypes 大模块（数组，各个子模块组成）
     * @return
     * @throws Exception
     */
    private BasePerformance countPerformance(BigDecimal userId, String currentMonth, int[] moduleTypes) throws Exception {
        long increaseNum = 0L;
        long modificationNum = 0L;
        long checkNum = 0L;
        for (int moduleType : moduleTypes) {
            BasePerformance basePerformance = performanceTaskMapper.countPerformance(userId, moduleType, currentMonth);
            increaseNum += basePerformance.getIncreaseNum();
            modificationNum += basePerformance.getModificationNum();
            checkNum += basePerformance.getCheckNum();
        }
        return new BasePerformance(increaseNum, modificationNum, checkNum);
    }

    /**
     * 从天阙获取动态事件绩效得分
     *
     * @param startTime
     * @param endTime
     * @return
     * @throws Exception
     */
    private List<DynamicEventReportPerformance> getDynamicEventReportPerformanceFormTianque(Timestamp startTime, Timestamp endTime) throws Exception {
        List<DynamicEventReportPerformance> dynamicEventReportPerformances;

        try {
            dynamicEventReportPerformances = new LinkedList<>();
            String url = TIAN_QUE_DYNAMIC_EVENT_REPORT_URL;
            String param = getURIParam(startTime, endTime);
            String result = CommonUtil.sendPostRequest(url, param);

            JSONArray array = JSON.parseArray(result);
            int size = array.size();
            if (size > 0) {
                for (int i = 0; i < size; i++) {
                    JSONObject obj = array.getJSONObject(i);
                    dynamicEventReportPerformances.add(JSONObject.toJavaObject(obj, DynamicEventReportPerformance.class));
                }
            }
        } catch (Exception e) {
            log.error("{} can not connect!", TIAN_QUE_DYNAMIC_EVENT_REPORT_URL);
            throw new Exception(TIAN_QUE_DYNAMIC_EVENT_REPORT_URL + " can not connect!", e);
        }

        return dynamicEventReportPerformances;
    }

    /**
     * 构建URI参数
     *
     * @param startTime 开始时间（月初）
     * @param endTime   结束时间（月末）
     * @return
     */
    private String getURIParam(Timestamp startTime, Timestamp endTime) {
        StringBuffer param = new StringBuffer();
        param.append("&startTime=");
        param.append(startTime.getTime());
        param.append("&endTime=");
        param.append(endTime.getTime());
        return String.valueOf(param);
    }
}
