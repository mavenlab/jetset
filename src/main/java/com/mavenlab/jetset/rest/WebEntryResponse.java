package com.mavenlab.jetset.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class WebEntryResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8175964450277165780L;

	public final static String STATUS_OK = "ok";
	public final static String STATUS_FAILED = "failed";
	
	private String status;
	private List<Map<String, String>> messages;
	
	/**
	 * default constructor
	 */
	public WebEntryResponse() {
		status = STATUS_OK;
		messages = new ArrayList<Map<String,String>>();
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void addMessage(String field, String message) {
		HashMap<String, String> messageMap = new HashMap<String, String>();
		messageMap.put("name", field);
		messageMap.put("message", message);
		messages.add(messageMap);
	}

	/**
	 * @return the messages
	 */
	public List<Map<String, String>> getMessages() {
		return messages;
	}

	/**
	 * @param messages the messages to set
	 */
	public void setMessages(List<Map<String, String>> messages) {
		this.messages = messages;
	}
}