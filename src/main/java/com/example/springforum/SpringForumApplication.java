package com.example.springforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringForumApplication.class, args);
    }

}
