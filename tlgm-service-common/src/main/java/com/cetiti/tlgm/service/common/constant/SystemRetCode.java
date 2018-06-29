package com.cetiti.tlgm.service.common.constant;

/**
 * 系统返回码
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @create 2018-06-27 11:20:51
 */
public enum SystemRetCode {
    SUCCESS(0x0000),
    FAILURE(0xFFFFF),

    NOT_FOUND(0x1000),
    ACCESS_DENY(0x1001),
    USER_EXISTED(0x1002),
    USER_NOT_EXISTED(0x1003),
    PASSWORD_ERROR(0x1004),
    USER_NOT_LOGIN(0x1005),
    SESSION_EXPIRED(0x1006),
    EXTERN_KEY_FAILE(0x1007),
    PERMISSION_DENY(0x1008),
    VERIFY_DENY(0x1009),

    QUERY_DENY(0x2001),
    ADD_DENY(0x2002),
    UPDATE_DENY(0x2003),
    DELETE_DENY(0x2004),
    UNIQUENESS_DENY(0x2005),
    SOMEONE_DENY(0x2006),
    UNIQUENESS_UPDATE(0x2007);

    private int code;

    SystemRetCode(int code) {
        this.code = code;
    }
}
