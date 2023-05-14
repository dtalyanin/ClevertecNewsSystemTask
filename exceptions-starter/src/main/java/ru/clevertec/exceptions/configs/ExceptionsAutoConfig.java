package ru.clevertec.exceptions.configs;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.clevertec.exceptions.advices.GlobalExceptionHandler;
import ru.clevertec.exceptions.advices.NewsExceptionHandler;
import ru.clevertec.exceptions.advices.UsersExceptionHandler;

@Configuration
@Slf4j
public class ExceptionsAutoConfig {

    private static final String HANDLER_MESSAGE = "Exception handlers created";

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public NewsExceptionHandler newsExceptionHandler() {
        return new NewsExceptionHandler();
    }

    @Bean
    public UsersExceptionHandler usersExceptionHandler() {
        return new UsersExceptionHandler();
    }

    @PostConstruct
    public void logCreatingExceptionHandlers() {
        log.info(HANDLER_MESSAGE);
    }
}
