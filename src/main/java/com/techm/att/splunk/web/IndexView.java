package com.techm.att.splunk.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.techm.att.bone.jsf.ViewScopedComponent;
import com.techm.rest.client.ConversationBean;

@ViewScopedComponent("indexView")
public class IndexView implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private List<String> submittedValues = new ArrayList<>();
    
    private Set<String> resources = new HashSet<String>();
    
    private Map<String, List<ConversationBean>> listMap = new HashMap<String, List<ConversationBean>>();
    
   // private List<String> resultList = new ArrayList<String>();
    
    private Map<String,String> resultMap = new HashMap<String,String>();
    
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

	public Map<String, List<ConversationBean>> getListMap() {
		return listMap;
	}

	public void setListMap(Map<String, List<ConversationBean>> listMap) {
		this.listMap = listMap;
	}

	public Map<String, String> getResultMap() {
		return resultMap;
	}

	public void setResultMap(Map<String, String> resultMap) {
		this.resultMap = resultMap;
	}

	

	
	
	
}
