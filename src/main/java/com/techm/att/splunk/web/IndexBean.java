package com.techm.att.splunk.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.techm.att.bone.spring.RequestScopedComponent;
import com.techm.rest.client.SplunkSearchResp;

@RequestScopedComponent("indexBean")
public class IndexBean {
    
	@Autowired
	private IndexView indexView;
	
	public void submit() {
		SplunkSearchResp.init(indexView.getConversationid().trim(), indexView.getInputparameter().trim());
		System.out.println("Check resource created or not!!!");
		indexView.getSubmittedValues().add(indexView.getInputparameter());
		indexView.getSubmittedValues().add(indexView.getConversationid());
	    
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Value submitted."));
	}
	
	public void reset() {
	    indexView.getSubmittedValues().clear();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Form reset."));
	}
	
}
