package com.cetiti.tlgm.service.patrol.service.impl;

import java.util.LinkedList;
import java.util.List;

import com.cetiti.tlgm.service.common.constant.PatrolConstant;
import com.cetiti.tlgm.service.common.mapper.GridCommunityTownshipMapper;
import com.cetiti.tlgm.service.common.mapper.OracleOperationMapper;
import com.cetiti.tlgm.service.common.mapper.PatrolTaskMapper;
import com.cetiti.tlgm.service.common.model.patrol.DurationMileageDTO;
import com.cetiti.tlgm.service.common.model.patrol.GridMemberPatrol;
import com.cetiti.tlgm.service.common.util.ChineseCalendarUtil;
import com.cetiti.tlgm.service.patrol.service.PatrolTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.cetiti.tlgm.service.common.constant.PatrolConstant.PATROL_TABLE_NAME;
import static com.cetiti.tlgm.service.common.util.CommonUtil.getDistance;
import static com.cetiti.tlgm.service.common.util.CommonUtil.getTimeInterval;

/**
 * 巡查业务接口实现类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-17 17:17:04
 */
@Service("patrolTaskService")
@Slf4j
public class PatrolTaskServiceImpl implements PatrolTaskService {

    @Autowired
    private PatrolTaskMapper patrolTaskMapper;

    @Autowired
    private GridCommunityTownshipMapper gridCommunityTownshipMapper;

    @Autowired
    private OracleOperationMapper oracleOperationMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertPatrolDurationAndMileage() throws Exception {
        //昨日日期yyyy-MM-dd
        String calendar = patrolTaskMapper.getTheDayBeforeToday();
        log.info("{} patrol's duration and mileage counting start");
        if (ChineseCalendarUtil.isWorkday(calendar)) {
            handlerPatrolDurationAndMileage();
        } else{
            log.info("{} is not work day, we will not count！", calendar);
        }
    }

    /**
     * 处理在线时长和里程计算统计工作
     *
     * @throws Exception
     */
    private void handlerPatrolDurationAndMileage() throws Exception {
        //当前月表名
        String currentPatrolTableName = getCurrentMonthPatrolTableName();
        //按月份分表，当前是否存在表，不存在则创建
        checkIfPatrolTableNotExistCreate(currentPatrolTableName);

        List<GridMemberPatrol> gridMemberPatrols = patrolTaskMapper.listGridMemberPatrol();
        //计算时长和里程
        for (GridMemberPatrol gridMemberPatrol : gridMemberPatrols) {
            List<DurationMileageDTO> durationMileages = new LinkedList<>(patrolTaskMapper.listTimeAndLocation(gridMemberPatrol.getUserId()));
            int size = durationMileages.size();
            double duration = 0.0;
            double mileage = 0.0;
            for (int i = size - 1; i > 0; i--) {
                double interval = getTimeInterval(durationMileages.get(i).getPatrolTime(), durationMileages.get(i - 1).getPatrolTime());
                if (interval <= PatrolConstant.ONLINE_TIME_STANDARD) {
                    duration += interval;
                }
                mileage += getDistance(durationMileages.get(i).getLongitude(), durationMileages.get(i).getLatitude(),
                        durationMileages.get(i - 1).getLongitude(), durationMileages.get(i - 1).getLatitude());
            }
            gridMemberPatrol.fixAllName(gridCommunityTownshipMapper);
            gridMemberPatrol.setDuration(duration);
            gridMemberPatrol.setMileage(mileage);
        }

        //批量插入数据库
        if (!gridMemberPatrols.isEmpty()) {
            patrolTaskMapper.insertBatch(gridMemberPatrols, currentPatrolTableName);
        }
    }

    /**
     * 获取当前月份表名
     *
     * @return
     * @throws Exception
     */
    private String getCurrentMonthPatrolTableName() throws Exception {
        return PATROL_TABLE_NAME + oracleOperationMapper.getCurrentMonth();
    }

    /**
     * 检查当月巡查表是否存在，若不存在创建
     *
     * @return
     * @throws Exception
     */
    private void checkIfPatrolTableNotExistCreate(String currentPatrolTableName) throws Exception {
        int count = oracleOperationMapper.checkTableExistByTableName(currentPatrolTableName);
        if (count <= 0) {
            patrolTaskMapper.createPatrolTable(currentPatrolTableName);
            log.info("create current month patrol table:{}", currentPatrolTableName);
        }
    }
}
