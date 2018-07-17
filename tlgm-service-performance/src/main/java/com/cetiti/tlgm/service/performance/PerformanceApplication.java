package com.cetiti.tlgm.service.performance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 绩效启动类
 *
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 14:23:47
 */
@SpringBootApplication(scanBasePackages = {"com.cetiti.tlgm.service.common.*","com.cetiti.tlgm.service.performance.*"})
@MapperScan("com.cetiti.tlgm.service.common.mapper")
public class PerformanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceApplication.class, args);
    }
}
