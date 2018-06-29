package com.cetiti.tlgm.service.performance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * 专职网格员模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-28 14:53:43
 */
@Data
public class FullTimeGridMember extends GridCommunityTownship implements Serializable{

    private BigDecimal userId;
    private String name;
    private String loginname;
}
