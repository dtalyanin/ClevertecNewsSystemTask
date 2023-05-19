package ru.clevertec.exceptions.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to automatically scan application components and log successful bean creation
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec.exceptions")
@Slf4j
public class ExceptionsAutoConfig {

    private static final String HANDLER_MESSAGE = "Exception handlers created";

    /**
     * Log exception handlers creating
     */
    @PostConstruct
    public void logCreatingExceptionHandlers() {
        log.info(HANDLER_MESSAGE);
    }
}