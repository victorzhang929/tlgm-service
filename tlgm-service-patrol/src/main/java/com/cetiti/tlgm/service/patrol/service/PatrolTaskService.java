package com.cetiti.tlgm.service.patrol.service;

/**
 * 巡查业务接口
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @create 2018-07-17 17:16:17
 */
public interface PatrolTaskService {

    /**
     * 保存专职网格员每天的巡查时长和里程
     *
     * @throws Exception
     */
    void insertPatrolDurationAndMileage() throws Exception;

    /**
     * 每个月创建巡查表，分表备份
     *
     * @throws Exception
     */
    void doCreateTablePerMonth() throws Exception;
}
