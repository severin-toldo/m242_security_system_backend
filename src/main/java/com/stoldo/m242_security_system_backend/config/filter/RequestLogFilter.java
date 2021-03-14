package com.stoldo.m242_security_system_backend.config.filter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.java.Log;

@Log
public class RequestLogFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {}

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String logMsg = MessageFormat.format("Request on {0} was answered with Status {1}", httpRequest.getRequestURL(), httpResponse.getStatus());
		log.log(Level.INFO, logMsg);
		
		chain.doFilter(request, response);
	}
}
