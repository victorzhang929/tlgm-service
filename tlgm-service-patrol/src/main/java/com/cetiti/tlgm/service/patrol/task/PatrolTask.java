package com.cetiti.tlgm.service.patrol.task;

import com.cetiti.tlgm.service.patrol.service.PatrolTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.cetiti.tlgm.service.common.constant.MsgConstant.INSERT_FAIL;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.INSERT_SUCCESS;

/**
 * 巡查定时任务
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

    @Scheduled(cron = "0 0 0 * * ?")
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
