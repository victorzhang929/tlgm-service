package com.cetiti.tlgm.service.common.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.cetiti.tlgm.service.common.model.patrol.DurationMileageDTO;
import com.cetiti.tlgm.service.common.model.patrol.GridMemberPatrol;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 巡查任务数据访问接口
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @create 2018-07-17 19:57:25
 */
@Repository
public interface PatrolTaskMapper {

    /**
     * 查询昨日日期
     *
     * @return 昨天日期
     * @throws Exception 抛出异常信息
     */
    @Select("SELECT TO_CHAR(SYSDATE - 1, 'YYYY-MM-DD') FROM DUAL")
    String getTheDayBeforeToday() throws Exception;

    /**
     * 查询当前专职网格员巡查信息
     *
     * @return 当前专职网格员巡查信息
     * @throws Exception 抛出异常信息
     */
    List<GridMemberPatrol> listGridMemberPatrol() throws Exception;

    /**
     * 查询前一天在线时刻和经纬度
     *
     * @param userId 用户ID
     * @return 前一天在线时间和经纬度
     * @throws Exception 抛出异常信息
     */
    List<DurationMileageDTO> listTimeAndLocation(BigDecimal userId) throws Exception;

    /**
     * 批量插入专职网格员时长和里程
     *
     * @param gridMemberPatrols 网格员巡查信息
     * @param tableName         表名（分表）
     * @throws Exception 抛出异常信息
     */
    void insertBatch(@Param("gridMemberPatrols") List<GridMemberPatrol> gridMemberPatrols, @Param("tableName") String tableName)
            throws Exception;

    /**
     * 每个月创建巡查表，分表
     *
     * @param tableName 表名（分表）
     * @throws Exception 抛出异常信息
     */
    void createPatrolTable(@Param("tableName") String tableName) throws Exception;
}
