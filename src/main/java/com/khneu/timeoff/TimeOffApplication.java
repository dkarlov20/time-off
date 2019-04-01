package com.khneu.timeoff;

import com.khneu.timeoff.mapper.Mapper;
import com.khneu.timeoff.repository.TimeOffRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TimeOffApplication implements CommandLineRunner {
    @Autowired
    TimeOffRequestRepository timeOffRequestRepository;
    @Autowired
    Mapper mapper;

    public static void main(String[] args) {
        SpringApplication.run(TimeOffApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
