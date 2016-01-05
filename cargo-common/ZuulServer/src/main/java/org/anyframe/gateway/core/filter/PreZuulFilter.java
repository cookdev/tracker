package org.anyframe.gateway.core.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

public class PreZuulFilter extends ZuulFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(PreZuulFilter.class);

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		String txId = ctx.getRequest().getHeader("txId");
		String remoteAddr = ctx.getRequest().getRemoteAddr();
		// Generating txId, spanId
		txId = UUID.randomUUID().toString();
		MDC.put("txId", txId);
		MDC.put("spanId", "0");

		ctx.addZuulRequestHeader("txId", txId);
		ctx.addZuulRequestHeader("spanId", "1"); // send request header : ++spanId 
        ctx.addZuulRequestHeader("x-forwarded-for", remoteAddr);
		
//		logger.info("#####Start##### ZuulFilter run txId :" + txId
//				+ " & spanId : 0");

		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 10;
	}

}