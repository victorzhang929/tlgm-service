package com.cetiti.tlgm.service.patrol.task;

import com.cetiti.tlgm.service.patrol.service.PatrolTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.cetiti.tlgm.service.common.constant.MsgConstant.CREATE_TABLE_FAIL;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.CREATE_TABLE_SUCCESS;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.INSERT_FAIL;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.INSERT_SUCCESS;

/**
 * 巡查定时任务(按月进行分表)
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-17 16:46:54
 */
@Component
@Slf4j
public class PatrolTask {

    @Autowired
    private PatrolTaskService patrolTaskService;

    /**
     * 每个月第一天凌晨0点30分创建一张巡查表
     */
    @Scheduled(cron = "0 30 0 1 * ?")
    public void doCreateTablePerMonth() {
        log.info("create table per month start");
        try {
            patrolTaskService.doCreateTablePerMonth();
            log.info("{}, create table per month end", CREATE_TABLE_SUCCESS);
        } catch (Exception e) {
            log.info(CREATE_TABLE_FAIL, e);
        }
    }

    /**
     * 每天凌晨3点计算前一天专职网格员在线时长和里程数
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void insertPatrolDurationAndMileage() {
        log.info("day insert operation start");
        try {
            patrolTaskService.insertPatrolDurationAndMileage();
            log.info("{}, day insert operation end", INSERT_SUCCESS);
        } catch (Exception e) {
            log.error(INSERT_FAIL, e);
        }
    }

}
