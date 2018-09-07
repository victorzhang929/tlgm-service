package com.cetiti.tlgm.service.common.constant;

/**
 * 数据层相关常量
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 11:08:05
 */
public final class DatabaseConstant {

    private DatabaseConstant() {
    }

    /**
     * 有效数据
     */
    public static final int VALID = 0;
    /**
     * 失效数据
     */
    public static final int INVALID = -1;

    /**
     * 专职网格员级别
     */
    public static final int GRID_MANAGER_TYPE = 3;

    /**
     * 日志插入类型
     */
    public static final int INSERT_TYPE = 1;
    /**
     * 日志更新类型
     */
    public static final int UPDATE_TYPE = 2;
    /**
     * 日志审核类型
     */
    public static final int CHECK_TYPE = 5;
}
