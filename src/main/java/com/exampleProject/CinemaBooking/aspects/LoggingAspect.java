package com.exampleProject.CinemaBooking.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.exampleProject.CinemaBooking.services.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing method: {}", joinPoint.getSignature().toShortString());
        logger.debug("Arguments: {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.exampleProject.CinemaBooking.services.*.*(..))", returning = "object")
    public void logAfterReturning(JoinPoint joinPoint, Object object) {
        logger.info("Method {} executed successfully", joinPoint.getSignature().toShortString());
        if (object != null) {
            logger.debug("Returned value: {}", object);
        }
    }

    @AfterThrowing(pointcut = "execution(* com.exampleProject.CinemaBooking.services.*.*(..))", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature().toShortString(), exception.getMessage(), exception);
    }
}
