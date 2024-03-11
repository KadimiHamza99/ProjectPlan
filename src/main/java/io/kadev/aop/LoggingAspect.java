package io.kadev.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@Configuration
@Aspect
@Slf4j
public class LoggingAspect {

    /*@Around("execution(* * io.kadev.controllers.*()")
    public void loggerAspect(){

    }*/

}
