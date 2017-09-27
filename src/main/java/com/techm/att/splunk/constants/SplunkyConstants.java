package com.techm.att.splunk.constants;

import javax.faces.context.FacesContext;



public class SplunkyConstants {
	public static final String SPLUNKY_RESOURCES_PATH = FacesContext
			.getCurrentInstance().getExternalContext()
			.getRealPath("/resources/").toString();
	
	public static final String SPLUNKY_URL = "http://api360.web.att.com/atw-service/log/splunk/search?search=";
}