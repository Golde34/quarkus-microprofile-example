package com.example.springlra;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupLogger implements ApplicationListener<ApplicationReadyEvent> {
    private final long start = System.currentTimeMillis();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        long duration = System.currentTimeMillis() - start;
        System.out.println("Spring Boot service started in " + duration + " ms");
    }
}
