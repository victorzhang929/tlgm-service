package com.cetiti.tlgm.service.common.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Oracle操作数据访问接口
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 15:42:03
 */
@Repository
public interface OracleOperationMapper {

    /**
     * 获得数据库当前月份第一天时间
     *
     * @return 当月第一天日期
     * @throws Exception 抛出异常信息
     */
    @Select("SELECT TRUNC(SYSDATE, 'MM') FROM DUAL")
    Timestamp getCurrentMonthFirstDay() throws Exception;

    /**
     * 获取数据库下个月第一天时间
     *
     * @return 下个月第一天日期
     * @throws Exception 抛出异常信息
     */
    @Select("SELECT TRUNC(ADD_MONTHS(SYSDATE, 1), 'MM') FROM DUAL")
    Timestamp getAfterMonthFirstDay() throws Exception;

    /**
     * 获取数据库当前月份
     *
     * @return 当前月份
     * @throws Exception 抛出异常信息
     */
    @Select("SELECT TO_CHAR(SYSDATE, 'YYYYMM') FROM DUAL")
    String getCurrentMonth() throws Exception;

    /**
     * 通过表名查看数据库中是否存在该表
     *
     * @param tableName 表名
     * @return 存在返回1，不存在返回0
     * @throws Exception 抛出异常信息
     */
    @Select("SELECT COUNT(1) FROM USER_TABLES WHERE TABLE_NAME =UPPER('${tableName}')")
    int checkTableExistByTableName(@Param("tableName") String tableName) throws Exception;
}
