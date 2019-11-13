package com.seven;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableTransactionManagement    //事务支持
@MapperScan("com.seven.dao") //扫描mapper
@SpringBootApplication
public class Admin {

    public static void main(String[] args) {
        SpringApplication.run(Admin.class,args);

    }

}
