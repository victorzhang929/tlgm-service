package com.cetiti.tlgm.service.common.model.patrol;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 巡查时长和里程数据传输模型
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-17 20:57:47
 */
@Data
public class DurationMileageDTO implements Serializable {

    private Date patrolTime;
    private Double longitude;
    private Double latitude;
}
