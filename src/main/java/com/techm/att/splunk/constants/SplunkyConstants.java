package com.techm.att.splunk.constants;

import javax.faces.context.FacesContext;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class SplunkyConstants {
	public static final String SPLUNKY_RESOURCES_PATH = FacesContext
			.getCurrentInstance().getExternalContext()
			.getRealPath("/resources/").toString();

	public static final String SPLUNKY_URL = "http://api360.web.att.com/atw-service/log/splunk/search?search=";

	public static final String USER_LOGIN = (String) RequestContextHolder
			.currentRequestAttributes().getAttribute(RequestAttributes.REFERENCE_SESSION, RequestAttributes.SCOPE_SESSION);
}