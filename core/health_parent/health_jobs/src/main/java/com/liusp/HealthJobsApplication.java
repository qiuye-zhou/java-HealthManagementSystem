package com.liusp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//启动定时任务
@SpringBootApplication
public class HealthJobsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthJobsApplication.class, args);
    }

}
