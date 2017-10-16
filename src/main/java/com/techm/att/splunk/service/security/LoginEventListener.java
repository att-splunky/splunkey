package com.techm.att.splunk.service.security;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Service
public class LoginEventListener implements ApplicationListener<InteractiveAuthenticationSuccessEvent> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginEventListener.class);
	@Override
	public void onApplicationEvent(InteractiveAuthenticationSuccessEvent event) {
		UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
		LOGGER.info("User {} logged in", userDetails.getUsername());
		
		RequestContextHolder.currentRequestAttributes().setAttribute(RequestAttributes.REFERENCE_SESSION, userDetails.getUsername(), RequestAttributes.SCOPE_SESSION);
	}
    
}
