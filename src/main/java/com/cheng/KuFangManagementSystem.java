package com.cheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author ChengJW
 * 2020/11/20/020
 */

@SpringBootApplication
@EnableSwagger2
public class KuFangManagementSystem {
    public static void main(String[] args) {
        SpringApplication.run(KuFangManagementSystem.class,args);
    }
}
