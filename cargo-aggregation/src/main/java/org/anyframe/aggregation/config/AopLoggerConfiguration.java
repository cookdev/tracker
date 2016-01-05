package org.anyframe.aggregation.config;

import org.apache.log4j.MDC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopLoggerConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(AopLoggerConfiguration.class);
	
	@AfterReturning("execution(* org.anyframe.aggregation.application..*.*(..))")
	public void applicationLogService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		MDC.put("method", methodName);
		logger.info("Aggregation application Logger : " + methodName);
	}
	
	@AfterReturning("execution(* org.anyframe.aggregation.interfaces..*.*(..))")
	public void interfacesLogService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		MDC.put("method", methodName);
		logger.info("Aggregation interfaces Logger : " + methodName);
	}
	
	@AfterReturning("execution(* org.anyframe.aggregation.infrastructure..*.*(..))")
	public void infrastructureLogService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		MDC.put("method", methodName);
		logger.info("Aggregation infrastructure Logger : " + methodName);
	}
}
