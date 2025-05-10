package com.example.commandeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CommandeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommandeServiceApplication.class, args);
    }

}
