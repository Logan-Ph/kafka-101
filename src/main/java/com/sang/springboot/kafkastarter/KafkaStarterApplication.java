package com.sang.springboot.kafkastarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStarterApplication.class, args);
    }

}
