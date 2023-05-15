package ru.clevertec.exceptions.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.exceptions")
@Slf4j
public class ExceptionsAutoConfig {

    private static final String HANDLER_MESSAGE = "Exception handlers created";

    @PostConstruct
    public void logCreatingExceptionHandlers() {
        log.info(HANDLER_MESSAGE);
    }
}