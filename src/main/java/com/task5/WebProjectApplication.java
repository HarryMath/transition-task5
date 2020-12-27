package com.task5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class WebProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebProjectApplication.class, args);
    }

}
