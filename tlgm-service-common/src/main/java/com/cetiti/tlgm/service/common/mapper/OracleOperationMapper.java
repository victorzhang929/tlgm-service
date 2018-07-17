package com.cetiti.tlgm.service.common.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * Oracle操作数据访问接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @create 2018-06-28 15:42:03
 */
@Repository
public interface OracleOperationMapper {

    /**
     * 获得数据库当前月份第一天时间
     * @return
     * @throws Exception
     */
    @Select("SELECT TRUNC(SYSDATE, 'MM') FROM DUAL")
    Timestamp getCurrentMonthFirstDay() throws Exception;

    /**
     * 获取数据库下个月第一天时间
     * @return
     * @throws Exception
     */
    @Select("SELECT TRUNC(ADD_MONTHS(SYSDATE, 1), 'MM') FROM DUAL")
    Timestamp getAfterMonthFirstDay() throws Exception;

    /**
     * 获取数据库当前月份时间
     * @return
     * @throws Exception
     */
    @Select("SELECT TO_CHAR(SYSDATE, 'YYYY-MM') FROM DUAL")
    String getCurrentMonth() throws Exception;
}
