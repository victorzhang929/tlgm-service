package com.cetiti.tlgm.service.patrol.service.impl;

import java.util.List;
import java.util.concurrent.RecursiveAction;

import com.cetiti.tlgm.service.common.mapper.PatrolTaskMapper;
import com.cetiti.tlgm.service.common.model.patrol.GridMemberPatrol;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 多线程处理巡查插入数据任务
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-18 10:31:11
 */
@Data
@Slf4j
public class MultiThreadPatrolOperation extends RecursiveAction {

    private static final int MAX_OPERATION_NUM = 200;
    private int start;
    private int end;
    private List<GridMemberPatrol> gridMemberPatrols;
    private PatrolTaskMapper patrolTaskMapper;

    public MultiThreadPatrolOperation(int start, int end, List<GridMemberPatrol> gridMemberPatrols, PatrolTaskMapper patrolTaskMapper) {
        this.start = start;
        this.end = end;
        this.gridMemberPatrols = gridMemberPatrols;
        this.patrolTaskMapper = patrolTaskMapper;
    }

    @Override
    protected void compute() {
        log.info("多线程同步插入任务开始");
        if ((end - start) < MAX_OPERATION_NUM) {
            try {
                patrolTaskMapper.insertBatch(gridMemberPatrols.subList(start, end));
            } catch (Exception e) {
                log.error("多线程插入失败", e);
            }
        } else {
            int middle = (start + end) / 2;
            MultiThreadPatrolOperation left = new MultiThreadPatrolOperation(start, middle, gridMemberPatrols, patrolTaskMapper);
            MultiThreadPatrolOperation right = new MultiThreadPatrolOperation(middle, end, gridMemberPatrols, patrolTaskMapper);

            left.fork();
            right.fork();
        }
        log.info("多线程同步插入任务结束");
    }
}
