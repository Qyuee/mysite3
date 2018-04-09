package com.cafe24.mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExecutionTimeAspect { 
	
	@Around( "execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..)) || execution(* *..controller.*.*(..))" ) 
	public Object roundAdvice(ProceedingJoinPoint pjp) throws Throwable{
		
		StopWatch sw=new StopWatch(); 
		sw.start();
		
		Object result = pjp.proceed(); 
		
		sw.stop();
		
		Long executionTime=sw.getTotalTimeMillis();
		
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String TaskName = className+"."+methodName;
		
		System.out.println("[Execution] "+TaskName+"-"+executionTime+"Milis");
		
		return result;
	}
}
 