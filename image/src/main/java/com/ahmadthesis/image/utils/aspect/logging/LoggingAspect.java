package com.ahmadthesis.image.utils.aspect.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  @Pointcut("within(@org.springframework.stereotype.Repository *)" +
          " || within(@org.springframework.stereotype.Service *)" +
          " || within(@org.springframework.web.bind.annotation.RestController *)")
  public void logPointCut() {
  }

  @Around("logPointCut()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("Test!");
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Enter: {}.{}() with argument[s] = {}", joinPoint.getSignature().getDeclaringTypeName(),
              joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
    }
    try {
      Object result = joinPoint.proceed();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(), result);
      }
      return result;
    } catch (IllegalArgumentException e) {
      LOGGER.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
              joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
      throw e;
    }
  }

//  @Before("logPointCut()")
//  public void logAllMethodCallAdvice() {
//    System.out.println("In Aspect");
//  }
}
