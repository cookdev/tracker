package org.anyframe.cloud.common.gateway.core.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SimpleCORSFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, OPTIONS, PUT");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
		chain.doFilter(req, res);
	}

	public void init(FilterConfig filterConfig) {}

	public void destroy() {}

}