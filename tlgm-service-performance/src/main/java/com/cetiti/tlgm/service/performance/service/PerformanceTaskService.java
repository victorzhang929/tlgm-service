package com.cetiti.tlgm.service.performance.service;

/**
 * 绩效业务接口
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 14:49:13
 */
public interface PerformanceTaskService {
    /**
     * 每个月初初始化插入数据库所有网格员绩效记录
     *
     * @throws Exception 抛出异常信息
     */
    void insertFullTimeGridMembersPerMonth() throws Exception;

    /**
     * 每个月小个小时更新数据库所有网格员绩效记录
     *
     * @throws Exception 抛出异常信息
     */
    void updateFullTimeGridMembersPerHour() throws Exception;
}
