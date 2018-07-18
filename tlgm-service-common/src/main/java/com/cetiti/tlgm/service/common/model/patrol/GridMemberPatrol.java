package com.cetiti.tlgm.service.common.model.patrol;

import java.io.Serializable;
import java.math.BigDecimal;

import com.cetiti.tlgm.service.common.model.GridCommunityTownship;
import lombok.Data;

import static com.cetiti.tlgm.service.common.util.CommonUtil.getDoubleWithDefaultValue;

/**
 * 网格员巡查信息
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-17 19:48:37
 */
@Data
public class GridMemberPatrol extends GridCommunityTownship implements Serializable {
    private BigDecimal id;
    private BigDecimal userId;
    private String loginname;
    private String name;
    private Double duration;
    private Double mileage;

    public Double getDuration() {
        return getDoubleWithDefaultValue(duration);
    }

    public Double getMileage() {
        return getDoubleWithDefaultValue(mileage);
    }
}
