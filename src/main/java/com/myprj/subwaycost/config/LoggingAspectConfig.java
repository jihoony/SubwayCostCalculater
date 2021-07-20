package com.myprj.subwaycost.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Slf4j
@Aspect
@Component
public class LoggingAspectConfig {

    @Around("execution(* com.myprj.subwaycost.api..*(..)))")
    public Object controllerLoggingAspect(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return loggingProcess(proceedingJoinPoint);
    }

    private Object loggingProcess(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        log.info(">>> ");
        log.info(">>> ================== start [" + className + "." + methodName + "]");

        final StopWatch watch = new StopWatch();

        //Measure method execution time
        watch.start();
        Object result = proceedingJoinPoint.proceed();
        watch.stop();

        if (result != null){
            log.info("return object: [" + result.getClass().getName() + "]");
        }
        //Log method execution time
        log.info("Execution time of " + className + "." + methodName + " "
                + ":: " + watch.getTotalTimeMillis() + " ms");

        log.info("<<< ================== end [" + className + "." + methodName + "]");
        log.info("<<<");

        return result;
    }
}