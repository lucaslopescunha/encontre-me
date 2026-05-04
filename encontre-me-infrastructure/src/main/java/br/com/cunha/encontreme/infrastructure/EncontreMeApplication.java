package br.com.cunha.encontreme.infrastructure;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.TimeZone;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class EncontreMeApplication {
    public static void main(String[] args) {
        SpringApplication.run(EncontreMeApplication.class, args);
    }
}
