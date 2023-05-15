package ru.clevertec.loggers.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "ru.clevertec.loggers")
@Slf4j
public class LoggersAutoConfig {

    private static final String LOGGERS_MESSAGE = "Loggers created";

    @PostConstruct
    public void logCreatingExceptionHandlers() {
        log.info(LOGGERS_MESSAGE);
    }
}