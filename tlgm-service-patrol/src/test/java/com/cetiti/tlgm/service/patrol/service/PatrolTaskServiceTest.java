package com.cetiti.tlgm.service.patrol.service;

import com.cetiti.tlgm.service.patrol.PatrolApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 巡查任务业务测试类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-18 09:50:39
 */
@SpringBootTest(classes = PatrolApplication.class)
@RunWith(SpringRunner.class)
public class PatrolTaskServiceTest {

    @Autowired
    private PatrolTaskService patrolTaskService;

    @Test
    public void testInsertPatrolDurationAndMileage() throws Exception {
        patrolTaskService.insertPatrolDurationAndMileage();
    }
}
