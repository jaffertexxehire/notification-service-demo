package dev.jaffer.demonotificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableKafka
//@EnableAsync
//@EnableSqs
public class DemoNotificationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoNotificationServiceApplication.class, args);
    }

}
