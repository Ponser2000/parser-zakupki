package com.ponser2000.parserzakupki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main class for Java Repository Template.
 */

@EnableScheduling
@SpringBootApplication
public class ParserZakupkiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParserZakupkiApplication.class, args);
    }
}
