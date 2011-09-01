package com.mavenlab.jetset.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@DiscriminatorValue("SMS")
public class SMSEntry extends Entry {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1047371976228046915L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mo_log_id", nullable = true)
	private MOLog moLog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mt_log_id", nullable = true)
	private MTLog mtLog;

	/**
	 * default constructor
	 */
	public SMSEntry() {
		super();
	}

	/**
	 * @return the moLog
	 */
	@XmlTransient
	@JsonIgnore
	public MOLog getMoLog() {
		return moLog;
	}

	/**
	 * @param moLog the moLog to set
	 */
	public void setMoLog(MOLog moLog) {
		this.moLog = moLog;
	}

	/**
	 * @return the mtLog
	 */
	@XmlTransient
	@JsonIgnore
	public MTLog getMtLog() {
		return mtLog;
	}

	/**
	 * @param mtLog the mtLog to set
	 */
	public void setMtLog(MTLog mtLog) {
		this.mtLog = mtLog;
	}
}