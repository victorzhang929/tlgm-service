package com.cetiti.tlgm.service.patrol.service;

import java.math.BigDecimal;
import java.util.List;

import com.cetiti.tlgm.service.common.mapper.PatrolTaskMapper;
import com.cetiti.tlgm.service.common.model.patrol.DurationMileageDTO;
import com.cetiti.tlgm.service.patrol.PatrolApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 巡查业务测试类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-17 20:46:23
 */
@SpringBootTest(classes = PatrolApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class PatrolTaskMapperTest {

    @Autowired
    private PatrolTaskMapper patrolTaskMapper;

    @Test
    public void testListTime() throws Exception {
        BigDecimal userId = new BigDecimal(9931);
        List<DurationMileageDTO> durationMileages = patrolTaskMapper.listTimeAndLocation(userId);
        for (DurationMileageDTO durationMileage : durationMileages) {
            System.out.println(durationMileage.getPatrolTime() + "+" + durationMileage.getLatitude() + "+" + durationMileage.getLongitude());
        }
    }
}
