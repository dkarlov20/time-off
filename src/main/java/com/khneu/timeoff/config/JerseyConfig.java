package com.khneu.timeoff.config;

import com.khneu.timeoff.controller.v1.TimeOffRequestController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig(){
        registerClasses(TimeOffRequestController.class);
    }
}
