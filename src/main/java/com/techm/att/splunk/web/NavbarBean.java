package com.techm.att.splunk.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.techm.att.bone.spring.AntPathMatcherUtils;
import com.techm.att.bone.spring.RequestScopedComponent;
import com.techm.att.bone.spring.RequestUtils;
import com.techm.rest.client.ResourceLoader;

@RequestScopedComponent("navbarBean")
public class NavbarBean {
    
    @Autowired
    private HttpServletRequest request;
    
    @Autowired
	private IndexView indexView;
    
    private String requestPath; 
    
    private String activeMenuItem;
    
    public String getActiveMenuItem() {
        if (activeMenuItem == null) {
            activeMenuItem = resolveActiveMenuItem();
        }
        
        return activeMenuItem;
    }

    private String resolveActiveMenuItem() {
        if (pathMatches("/;/index.*")) {
        	try {
				indexView.setResources(ResourceLoader.getResources());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return "home";
        } else if (pathMatches("/cities/**")) {
            return "cities";
        }
        
        return "unknown";
    }
    
    private boolean pathMatches(String pathPattern) {
        if (requestPath == null) {
            requestPath = RequestUtils.getOriginatingRequestPath(request);
        }
        
        return AntPathMatcherUtils.matches(pathPattern, requestPath);
    }
    
    
}
