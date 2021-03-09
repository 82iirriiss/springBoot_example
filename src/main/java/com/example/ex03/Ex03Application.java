package com.example.ex03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Ex03Application {

    public static void main(String[] args) {
        SpringApplication.run(Ex03Application.class, args);
    }

}
