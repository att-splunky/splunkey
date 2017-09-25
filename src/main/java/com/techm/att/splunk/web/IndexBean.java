package com.techm.att.splunk.web;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.techm.att.bone.spring.RequestScopedComponent;
import com.techm.rest.client.ResourceLoader;
import com.techm.rest.client.SplunkSearchResp;

@RequestScopedComponent("indexBean")
public class IndexBean {
    
	@Autowired
	private IndexSession indexSession;
	
	
	public void submit() {
		SplunkSearchResp.init(indexSession.getConversationid().trim(), indexSession.getInputparameter());
		System.out.println("Check resource created or not!!!");
		indexSession.getSubmittedValues().add(indexSession.getInputparameter());
		indexSession.getSubmittedValues().add(indexSession.getConversationid());
		//System.out.println("Key Set:::: "+ SplunkSearchResp.getListMap().keySet());
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Value submitted."));
	}
	
	public void reset() {
	   // indexSession.getSubmittedValues().clear();
		System.out.println("ResultList in indexSession:: "+ ResourceLoader.getResultXMLFiles());
		
		indexSession.setResultList(ResourceLoader.getResultXMLFiles());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Form reset."));
	}
	
	
	
	public void poulateRecords(){
		indexSession.setListMap(SplunkSearchResp.getListMap());
	}

}
