package com.cetiti.tlgm.service.performance.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cetiti.tlgm.service.common.CommonUtil;
import com.cetiti.tlgm.service.common.mapper.GridCommunityTownshipMapper;
import com.cetiti.tlgm.service.common.mapper.OracleOperationMapper;
import com.cetiti.tlgm.service.common.mapper.PerformanceTaskMapper;
import com.cetiti.tlgm.service.common.model.performance.GridMemberPerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.BasicDataMaintenancePerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.DynamicEventReportPerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.EntOrgPerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.KeyFacilitiesPerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.KeySitePerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.KeyUnitPerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.RealPopulationPerformance;
import com.cetiti.tlgm.service.common.model.performance.quota.ResidencePerformance;
import com.cetiti.tlgm.service.performance.service.PerformanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.cetiti.tlgm.service.common.constant.PerformanceConstant.TIAN_QUE_DYNAMIC_EVENT_REPORT_URL;

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
        List<GridMemberPerformance> gridMemberPerformances = performanceTaskMapper.listGridMemberPerformance();
        for (GridMemberPerformance gridMemberPerformance : gridMemberPerformances) {
            insert(gridMemberPerformance);
        }
    }

    private void insert(GridMemberPerformance gridMemberPerformance) throws Exception {
        gridMemberPerformance.fixAllName(gridCommunityTownshipMapper);
        log.info("insert full-time grid member's performance: {}", gridMemberPerformance.toString());
        performanceTaskMapper.insertWithoutPerformance(gridMemberPerformance);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateFullTimeGridMembersPerHour() throws Exception {
        List<GridMemberPerformance> gridMemberPerformances = performanceTaskMapper.listGridMemberPerformance();
        //计算所有专职网格员绩效
        calcTotalPerformance(gridMemberPerformances);
        List<BigDecimal> userIdsInDB = performanceTaskMapper.listCurrentMonthUserId();
        BigDecimal userId;
        for (GridMemberPerformance gridMemberPerformance : gridMemberPerformances) {
            //插入或者更新
            gridMemberPerformance.fixAllName(gridCommunityTownshipMapper);
            performanceTaskMapper.saveOrUpdate(gridMemberPerformance);

            userId = gridMemberPerformance.getUserId();
            for (BigDecimal userIdInDB : userIdsInDB) {
                if (userIdInDB.equals(userId)) {
                    userIdsInDB.remove(userIdInDB);
                    break;
                }
            }
        }
        if (null != userIdsInDB && userIdsInDB.size() > 0) {
            performanceTaskMapper.batchDeleteUserIds(userIdsInDB);
        }
    }

    /**
     * 计算专职网格员当前月份基础数据维护绩效得分
     *
     * @param userId
     * @return
     * @throws Exception
     */
    private BasicDataMaintenancePerformance calcBasicPerformance(BigDecimal userId) throws Exception {

        BasicDataMaintenancePerformance basicDataMaintenancePerformance = new BasicDataMaintenancePerformance();

        basicDataMaintenancePerformance.setResidence(new ResidencePerformance(performanceTaskMapper, userId));
        basicDataMaintenancePerformance.setRealPopulation(new RealPopulationPerformance(performanceTaskMapper, userId));
        basicDataMaintenancePerformance.setEntOrg(new EntOrgPerformance(performanceTaskMapper, userId));
        basicDataMaintenancePerformance.setKeyUnit(new KeyUnitPerformance(performanceTaskMapper, userId));
        basicDataMaintenancePerformance.setKeySite(new KeySitePerformance(performanceTaskMapper, userId));
        basicDataMaintenancePerformance.setKeyFacilities(new KeyFacilitiesPerformance(performanceTaskMapper, userId));
        basicDataMaintenancePerformance.calcTotalScore();

        return basicDataMaintenancePerformance;
    }

    /**
     * 计算当前月份绩效
     *
     * @return
     * @throws Exception
     */
    private List<GridMemberPerformance> calcTotalPerformance(List<GridMemberPerformance> gridMemberPerformances) throws Exception {
        //计算当月动态事件绩效得分
        Timestamp currentMonthFirstDay = oracleOperationMapper.getCurrentMonthFirstDay();
        Timestamp afterMonthFirstDay = oracleOperationMapper.getAfterMonthFirstDay();
        List<DynamicEventReportPerformance> dynamicEventReportPerformances =
                getDynamicEventReportPerformanceFormTianque(currentMonthFirstDay, afterMonthFirstDay);

        for (GridMemberPerformance gridMemberPerformance : gridMemberPerformances) {
            //计算当月基础数据维护绩效得分
            gridMemberPerformance.setBasic(calcBasicPerformance(gridMemberPerformance.getUserId()));

            if (null != dynamicEventReportPerformances && !dynamicEventReportPerformances.isEmpty()) {
                for (DynamicEventReportPerformance dynamicEventReportPerformance : dynamicEventReportPerformances) {
                    //天阙username对应全科网格loginname
                    if (StringUtils.compare(dynamicEventReportPerformance.getUsername(), gridMemberPerformance.getLoginname()) == 0) {
                        gridMemberPerformance.setDynamic(dynamicEventReportPerformance);
                        dynamicEventReportPerformances.remove(dynamicEventReportPerformance);
                        break;
                    }
                }
            }
            gridMemberPerformance.calcTotalScore();
        }
        return gridMemberPerformances;
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
