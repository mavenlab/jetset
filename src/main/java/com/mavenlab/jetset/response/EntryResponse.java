/**
 * Copyright (c) 2011 Mavenlab Private Limited. All rights reserved.
 */
package com.mavenlab.jetset.response;

import com.mavenlab.jetset.model.Entry;
import com.mavenlab.jetset.model.MOLog;
import com.mavenlab.jetset.model.MTLog;
import com.mavenlab.jetset.model.SMSEntry;

/**
 * Class : EntryResponse.java
 * 
 * @author <a href="mailto:yogie.kurniawan@mavenlab.com">Yogie Kurniawan</a>
 */
public class EntryResponse extends Response {

	private static final long serialVersionUID = -4453053466974168986L;

	private Entry entry;
	private String incomingMsg;
	private String outgoingMsg;
	private String sentStatus;

	/**
	 * @param entry
	 */
	public EntryResponse(Entry entry) {
		super();
		this.entry = entry;
		if (entry instanceof SMSEntry) {
			this.incomingMsg = ((SMSEntry) entry).getMoLog().getMessage();
			this.outgoingMsg = ((SMSEntry) entry).getMtLog().getMessage();
			this.sentStatus = ((SMSEntry) entry).getMtLog().getStatusId()
					.equals("501") ? "Success" : "Failed";
		}
	}

	/**
	 * @return the entry
	 */
	public Entry getEntry() {
		return entry;
	}

	/**
	 * @param entry
	 *            the entry to set
	 */
	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	/**
	 * @return the incomingMsg
	 */
	public String getIncomingMsg() {
		return incomingMsg;
	}

	/**
	 * @param incomingMsg
	 *            the incomingMsg to set
	 */
	public void setIncomingMsg(String incomingMsg) {
		this.incomingMsg = incomingMsg;
	}

	/**
	 * @return the outgoingMsg
	 */
	public String getOutgoingMsg() {
		return outgoingMsg;
	}

	/**
	 * @param outgoingMsg
	 *            the outgoingMsg to set
	 */
	public void setOutgoingMsg(String outgoingMsg) {
		this.outgoingMsg = outgoingMsg;
	}

	/**
	 * @return the sentStatus
	 */
	public String getSentStatus() {
		return sentStatus;
	}

	/**
	 * @param sentStatus
	 *            the sentStatus to set
	 */
	public void setSentStatus(String sentStatus) {
		this.sentStatus = sentStatus;
	}

}
