package org.anyframe.cloud.cargo.booking.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class CargoBookingFilter implements Filter {

	private static final Logger logger = LoggerFactory
			.getLogger(CargoBookingFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String txId = ((HttpServletRequest) request).getHeader("txId");
		String spanId = ((HttpServletRequest) request).getHeader("spanId");
		String originAddr = ((HttpServletRequest) request).getHeader("x-forwarded-for");
		
		if (txId != null) {
			MDC.put("txId", txId);
			MDC.put("spanId", spanId);
			MDC.put("x-forwarded-for", originAddr);
		}
//		logger.info("CargoBookingFilter run txId : "
//				+ txId + " & spanID : " + spanId);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

}