package com.cgg.zkstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZkStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkStudyApplication.class, args);
    }

}
