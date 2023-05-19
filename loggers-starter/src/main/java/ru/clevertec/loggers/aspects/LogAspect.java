package ru.clevertec.loggers.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Aspect for intercepting controllers methods calls and logging its
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * Pointcut for intercepting method calls of class that has ControllerLog annotation
     * and mark as RestController
     */
    @Pointcut("@within(ru.clevertec.loggers.annotations.ControllerLog) && " +
            "@within(org.springframework.web.bind.annotation.RestController)")
    public void logControllerClass() {}

    /**
     * Pointcut for intercepting method calls that have ControllerLog annotation and be located
     * in controllers folder
     */
    @Pointcut("execution(* ru.clevertec.*.controllers.*.*(..)) && " +
            "@annotation(ru.clevertec.loggers.annotations.ControllerLog)")
    public void logControllerMethod() {}

    /**
     * Log method name, params and class of calling method
     * @param joinPoint join point on which aspect was called
     */
    @Before("logControllerClass() || logControllerMethod()")
    public void logBeforeExecutionMethod(JoinPoint joinPoint) {
        log.info("Called method {}() with params {} in {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                joinPoint.getTarget().getClass().getSimpleName());
    }

    /**
     * Log method name, params, class and returned value of calling method
     * @param joinPoint join point on which aspect was called
     * @param value returned response entity after executing operation
     */
    @AfterReturning(value = "logControllerClass() || logControllerMethod()", returning = "value")
    public void logAfterReturningValue(JoinPoint joinPoint, ResponseEntity<?> value) {
        log.info("Method {}() with params {} in {} return response {}",
                joinPoint.getSignature().getName(),
                joinPoint.getArgs(),
                joinPoint.getTarget().getClass().getSimpleName(),
                value);
    }

    /**
     * Log method name, params, class and exception thrown by calling method
     * @param joinPoint join point on which aspect was called
     * @param exception exception that was thrown from method
     */
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
