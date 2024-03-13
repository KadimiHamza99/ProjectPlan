package io.kadev.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@EnableAspectJAutoProxy
@Configuration
@Aspect
@Slf4j
public class LoggingAspect {

    /*
     * General Logging Aspect for services
     * */
    @Pointcut(value="execution(* io.kadev.controllers.*.*(..))")
    public void generalServiceLoggerPointcut(){ }

    @Around("generalServiceLoggerPointcut()")
    public Object generalServiceLoggerAdvise(ProceedingJoinPoint pjd) throws Throwable {
        String methodName = pjd.getSignature().getName();
        String className = pjd.getTarget().getClass().getSimpleName();
        final StopWatch stopWatch= new StopWatch();
        Object[] args = pjd.getArgs();
        log.info(className + "::" + methodName + ": Start Execution " + Arrays.toString(args));
        stopWatch.start();
        Object object = pjd.proceed();
        stopWatch.stop();
        log.info(className + "::" + methodName + " : Execution finished " + stopWatch.getTotalTimeMillis() + "ms");
        return object;
    }

    @AfterThrowing(pointcut = "execution(* io.kadev.controllers.*.*(..))", throwing = "e")
    public void generalServiceLoggerAdvise(JoinPoint jp, Throwable e){
        String methodName = jp.getSignature().getName();
        String className = jp.getTarget().getClass().getSimpleName();
        log.error(className + "::" + methodName + ": Execution failed because "+ e.getMessage());
    }

}
