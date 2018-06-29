package com.cetiti.tlgm.service.performance.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.cetiti.tlgm.service.performance.model.FullTimeGridMember;
import com.cetiti.tlgm.service.performance.model.Performance;
import com.cetiti.tlgm.service.performance.model.quota.BasePerformance;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 绩效任务数据访问接口
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 15:33:39
 */
@Repository
public interface PerformanceTaskMapper {

    /**
     * 查询当月专职网格员的绩效
     * @return
     * @throws Exception
     */
    List<FullTimeGridMember> listFullTimeGridMember() throws Exception;

    /**
     * 插入当月专职网格员信息入绩效表，无绩效信息，表设计中绩效字段默认值0
     * @param fullTimeGridMember 专职网格员
     * @throws Exception
     */
    void insertWithoutPerformance(FullTimeGridMember fullTimeGridMember) throws Exception;

    /**
     * 计算专职网格员模块当月新增、修改、审核数量
     * @param userId 专职网格员id
     * @param moduleType 模块名称
     * @param currentMonth 当前月份
     * @return
     * @throws Exception
     */
    BasePerformance countPerformance(BigDecimal userId, int moduleType, String currentMonth) throws Exception;

    /**
     * 列出当月绩效表专职网格员id
     * @param currentMonth 当前月份
     * @return
     * @throws Exception
     */
    @Select("SELECT USER_ID FROM T_PERFORMANCE WHERE TO_CHAR(CRATE_TIME, 'YYYY-MM') = #{currentMonth}")
    List<BigDecimal> listCurrentMonthUserId(String currentMonth) throws Exception;

    /**
     * 根据userIds批量删除
     * @param userIds 待删除userIds集合
     * @param currentMonth 当前月份
     * @throws Exception
     */
    void batchDeleteUserIds(@Param("userIds") List<BigDecimal> userIds, @Param("currentMonth") String currentMonth) throws Exception;

    /**
     * 插入或者更新
     * @param performance 绩效
     * @param currentMonth  当前月份
     * @throws Exception
     */
    void saveOrUpdate(Performance performance, String currentMonth) throws Exception;
}
