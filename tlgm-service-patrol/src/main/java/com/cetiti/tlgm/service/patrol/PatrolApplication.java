package com.cetiti.tlgm.service.patrol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 巡查启动类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-07-17 16:42:08
 */
@SpringBootApplication(scanBasePackages = {"com.cetiti.tlgm.service.common.*","com.cetiti.tlgm.service.patrol.*"})
@MapperScan("com.cetiti.tlgm.service.patrol.mapper")
public class PatrolApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatrolApplication.class, args);
    }
}
