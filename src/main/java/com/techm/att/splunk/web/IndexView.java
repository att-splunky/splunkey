package com.techm.att.splunk.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.techm.att.bone.jsf.ViewScopedComponent;

@ViewScopedComponent("indexView")
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private List<String> submittedValues = new ArrayList<>();
    
    private Set<String> resources = new HashSet<String>();
    
	private String conversationid;
	private String inputparameter;
	
    public List<String> getSubmittedValues() {
        return submittedValues;
    }

    public void setSubmittedValues(List<String> submittedValues) {
        this.submittedValues = submittedValues;
    }

	public String getConversationid() {
		return conversationid;
	}

	public void setConversationid(String conversationid) {
		this.conversationid = conversationid;
	}

	public String getInputparameter() {
		return inputparameter;
	}

	public void setInputparameter(String inputparameter) {
		this.inputparameter = inputparameter;
	}

	public Set<String> getResources() {
		return resources;
	}

	public void setResources(Set<String> resources) {
		this.resources = resources;
	}
	
	
}
