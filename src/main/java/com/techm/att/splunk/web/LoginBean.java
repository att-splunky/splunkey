package com.techm.att.splunk.web;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.techm.att.bone.spring.RequestScopedComponent;
import com.techm.att.bone.spring.SessionScopedComponent;
import com.techm.att.splunk.service.security.UserDetailsServiceImpl;

@SessionScopedComponent("loginBean")
public class LoginBean {
    
    private static final String EVENT_LOGOUT = "logout";

    private static final String EVENT_LOGIN_FAILURE = "loginFailure";
    
    private String event;
    
    
    
    
    public UserDetails getUser() {
    	 String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	this.user = userService.loadUserByUsername(name);
		System.out.println("name .." + name);
		return user;
	}


	public void setUser(UserDetails user) {
		if (this.user == null) {

            String name = SecurityContextHolder.getContext().getAuthentication().getName();
			this.user = userService.loadUserByUsername(name);
			System.out.println("name .." + name);
        }
	}

	private String errorMessage;

    @Autowired
    UserDetailsServiceImpl userService;
    
    private UserDetails user;

    protected UserDetails getCurrentUser() {
        if (user == null) {
            user = userService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return user;
    }
    
    
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }

    // TODO: localize messages
    public void init() {
    	errorMessage="Successfull Login";
        if (EVENT_LOGOUT.equals(event)) {
            errorMessage = "You are logged out.";
        } else if (EVENT_LOGIN_FAILURE.equals(event)) {
            // TODO: handle different login failure reasons (should be saved in the session by Spring Security)
            errorMessage = "Invalid username or password.";
        }
    }
    
}
