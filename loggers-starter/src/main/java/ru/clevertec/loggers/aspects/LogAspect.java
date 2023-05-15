package ru.clevertec.loggers.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("@within(ru.clevertec.loggers.annotations.ControllerLog) && " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void logControllerClass() {}


    @Pointcut("execution(* ru.clevertec.*.controllers.*.*(..)) && " +
            "@annotation(ru.clevertec.loggers.annotations.ControllerLog)")
    public void logControllerMethod() {}

    @Before("logControllerClass() || logControllerMethod()")
    public void logBeforeExecutionMethod(JoinPoint joinPoint) {
        log.info("Called method {}() with params {} in {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterReturning(value = "logControllerClass() || logControllerMethod()", returning = "value")
    public void logAfterReturningValue(JoinPoint joinPoint, ResponseEntity<?> value) {
        log.info("Method {}() with params {} in {} return response {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                joinPoint.getTarget().getClass().getSimpleName(),
                value);
    }

    @AfterThrowing(value = "logControllerClass() || logControllerMethod()", throwing = "exception")
    public void logAfterReturningValue(JoinPoint joinPoint, Exception exception) {
        log.info("Method {}() with params {} in {} throws exception {} with message '{}'",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                joinPoint.getTarget().getClass().getSimpleName(),
                exception.getClass().getSimpleName(),
                exception.getMessage());
    }
}
