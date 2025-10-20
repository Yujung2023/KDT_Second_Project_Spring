package com.kedu.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kedu.project.dao")
public class Project02Application {
    public static void main(String[] args) {
        SpringApplication.run(Project02Application.class, args);
    }
}