package uz.dev.streamingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StreamingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StreamingServiceApplication.class, args);
    }

}
