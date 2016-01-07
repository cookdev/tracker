package org.anyframe.cloud.common.gateway.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.anyframe.cloud.common.gateway.auth.exception.AuthorizationException;
import org.anyframe.cloud.common.gateway.auth.exception.HttpClientError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class CheckAccessTokenFilter extends ZuulFilter {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Value("${anyframe.cloud.auth.tokenUrl}")
	private String getTokenUrl;

	@Value("${anyframe.cloud.auth.checkTokenUrl}")
	private String checkTokenUrl;

	private static final Logger logger = LoggerFactory.getLogger(CheckAccessTokenFilter.class);

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();

		try {
			String authHeader = ctx.getRequest().getHeader("Authorization");
			if(authHeader == null) {
				authHeader = "bearer ";
			}
			String[] bearer_token = authHeader.trim().split(" ");
			String accessToken = bearer_token[bearer_token.length-1];
			logger.debug("##### Token Validation ##### Start");
			ResponseEntity<String> exchange = restTemplate.exchange(checkTokenUrl, HttpMethod.GET, null, String.class, accessToken);
			logger.debug("##### Token Validation ##### Code : " + exchange.getStatusCode().value());
			logger.debug("##### Token Validation ##### Body : " + exchange.getBody());
		} catch (HttpClientErrorException e){
			HttpClientError clientError = null;
			try {
				clientError = jacksonObjectMapper.readValue(e.getResponseBodyAsString(), HttpClientError.class);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			throw new AuthorizationException(HttpStatus.UNAUTHORIZED, clientError.getError_description(), e.getResponseBodyAsByteArray(), null);
		} catch (Exception e) {
			throw new AuthorizationException(HttpStatus.INTERNAL_SERVER_ERROR, "An error has occurred during the authentication process", e.getMessage().getBytes(), null);
		}
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		return !request.getRequestURI().startsWith("/auth/oauth");
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
