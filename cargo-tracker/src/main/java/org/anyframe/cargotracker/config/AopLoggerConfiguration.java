package org.anyframe.cargotracker.config;

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

	@AfterReturning("execution(* org.anyframe.cargotracker.application..*.*(..))")
	public void applicationLogService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		MDC.put("method", methodName);
		logger.info("Cargotracker application Logger : " + methodName);
	}
	
	@AfterReturning("execution(* org.anyframe.cargotracker.interfaces..*.*(..))")
	public void interfacesLogService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		MDC.put("method", methodName);
		logger.info("Cargotracker interfaces Logger : " + methodName);
	}
	
	@AfterReturning("execution(* org.anyframe.cargotracker.infrastructure..*.*(..))")
	public void infrastructureLogService(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		MDC.put("method", methodName);
		logger.info("Cargotracker infrastructure Logger : " + methodName);
	}
}
