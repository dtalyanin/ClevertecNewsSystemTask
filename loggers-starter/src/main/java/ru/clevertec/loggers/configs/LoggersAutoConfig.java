package ru.clevertec.loggers.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to automatically scan application components and log successful bean creation
 */
@Configuration
@ComponentScan(basePackages = "ru.clevertec.loggers")
@Slf4j
public class LoggersAutoConfig {

    private static final String LOGGERS_MESSAGE = "Loggers created";

    /**
     * Log advices creating
     */
    @PostConstruct
    public void logCreatingExceptionHandlers() {
        log.info(LOGGERS_MESSAGE);
    }
}