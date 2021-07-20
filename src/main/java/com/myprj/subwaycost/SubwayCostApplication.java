package com.myprj.subwaycost;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SubwayCostApplication  {

    public static void main(String[] args) {
        SpringApplication.run(SubwayCostApplication.class, args);
    }
}
