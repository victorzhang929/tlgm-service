package com.cetiti.tlgm.service.performance.task;

import com.cetiti.tlgm.service.performance.service.PerformanceTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.cetiti.tlgm.service.common.constant.MsgConstant.INSERT_FAIL;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.INSERT_SUCCESS;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.UPDATE_FAIL;
import static com.cetiti.tlgm.service.common.constant.MsgConstant.UPDATE_SUCCESS;

/**
 * 绩效定时任务
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 14:47:48
 */
@Component
@Slf4j
public class PerformanceTask {

    @Autowired
    private PerformanceTaskService performanceTaskService;

    /**
     * 每个月1号0点插入所有专职网格员信息
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void insertFullTimeGridMembersPerMonth() {
        log.info("day one insert operation start");
        try {
            performanceTaskService.insertFullTimeGridMembersPerMonth();
            log.info("{}, day one insert operation end", INSERT_SUCCESS);
        } catch (Exception e) {
            log.error(INSERT_FAIL, e);
        }
    }

    /**
     * 每小时各个时间段更新所有专职网格员信息
     */
    @Scheduled(cron = "0 5,15,25,35,45,55 * * * ?")
    public void updateFullTimeGridMembersPerHour() {
        log.info("hour update operation start");
        try {
            performanceTaskService.updateFullTimeGridMembersPerHour();
            log.info("{}, hour update operation end", UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error(UPDATE_FAIL, e);
        }
    }

}
