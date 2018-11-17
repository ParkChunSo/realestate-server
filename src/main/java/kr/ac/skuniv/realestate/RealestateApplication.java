package kr.ac.skuniv.realestate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RealestateApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealestateApplication.class, args);
    }
}