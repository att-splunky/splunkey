package com.techm.att.splunk.web;

import com.techm.att.bone.spring.RequestScopedComponent;
import com.techm.att.splunk.constants.SplunkyConstants;

@RequestScopedComponent("loginBean")
public class LoginBean {
    
	
	
    private static final String EVENT_LOGOUT = "logout";

    private static final String EVENT_LOGIN_FAILURE = "loginFailure";
    
    private String event;
    
    private String errorMessage;

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
        if (EVENT_LOGOUT.equals(event)) {
        	
    		System.out.println("Session Attribute with name [ " + SplunkyConstants.USER_LOGIN);
        
        	
        	System.out.println(" ] has been removed " );
        	
        	/*String[] attributeNames = RequestContextHolder.currentRequestAttributes().getAttributeNames(RequestAttributes.SCOPE_SESSION);
    		for (int i = 0; i < attributeNames.length; i++) {
    			System.out.println("Session Attribute with Name ["+attributeNames[i]+"] has been removed " );
    			RequestContextHolder.currentRequestAttributes().removeAttribute(attributeNames[i], RequestAttributes.SCOPE_SESSION);
    			
    		}*/

            errorMessage = "You are logged out.";
        } else if (EVENT_LOGIN_FAILURE.equals(event)) {
            // TODO: handle different login failure reasons (should be saved in the session by Spring Security)
            errorMessage = "Invalid username or password.";
        }
    }
    
}
