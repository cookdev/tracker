package org.anyframe.cloud.cargo.tracker.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CargoTrackerFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(CargoTrackerFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String txId = ((HttpServletRequest) request).getHeader("txId");
		String spanId = ((HttpServletRequest) request).getHeader("spanId");
		String originAddr = ((HttpServletRequest) request).getHeader("x-forwarded-for");
		
		if (txId != null) {
			MDC.put("txId", txId);
			MDC.put("spanId", spanId);
			MDC.put("x-forwarded-for", originAddr);
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}