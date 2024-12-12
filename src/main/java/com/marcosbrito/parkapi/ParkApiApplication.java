package com.marcosbrito.parkapi;

import org.modelmapper.internal.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
//@ComponentScan(basePackages = {"com.marcosbrito.parkapi", "com.marcosbrito.parkapi.dtos.mapper"})
public class ParkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkApiApplication.class, args);
    }

}
