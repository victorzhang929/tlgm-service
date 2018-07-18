package com.cetiti.tlgm.service.common.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.cetiti.tlgm.service.common.model.patrol.DurationMileageDTO;
import com.cetiti.tlgm.service.common.model.patrol.GridMemberPatrol;
import org.apache.ibatis.annotations.Param;
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
     * 查询当前专职网格员巡查信息
     *
     * @return
     * @throws Exception
     */
    List<GridMemberPatrol> listGridMemberPatrol() throws Exception;

    /**
     * 查询前一天在线时刻和经纬度
     *
     * @param userId
     * @return
     * @throws Exception
     */
    List<DurationMileageDTO> listTimeAndLocation(BigDecimal userId) throws Exception;

    /**
     * 批量插入专职网格员时长和里程
     *
     * @param gridMemberPatrols
     * @param tableName
     * @throws Exception
     */
    void insertBatch(@Param("gridMemberPatrols") List<GridMemberPatrol> gridMemberPatrols,@Param("tableName") String tableName)
            throws Exception;

    /**
     * 每个月创建巡查表，分表
     *
     * @param tableName
     * @throws Exception
     */
    void createPatrolTable(@Param("tableName") String tableName) throws Exception;
}
