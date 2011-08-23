package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "mo_logs")
@SequenceGenerator(
		name = "SEQ_MO",
		sequenceName = "sequence_mo",
		initialValue = 1,
		allocationSize = 1
)
public class MOLog extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6762281060523505632L;

	public final static String STATUS_PENDING = "0";
	public final static String STATUS_SUCCESS = "101";
	public final static String STATUS_FAILED = "100";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MO")
	private int id;
	
	@Column(name = "keyword", nullable = false)
	private String keyword;
	
	@Column(name = "message", nullable = false)
	private String message;
	
	@Column(name = "mo_id", nullable = false)
	private String moId;
	
	@Column(name = "msisdn", nullable = false)
	private String msisdn;
	
	@Column(name = "date_received", nullable = false)
	private String timestamp;

	@Column(name = "route_status", length = 5, nullable = false)
	private String routeStatus;
	
	public MOLog() {
		routeStatus = STATUS_PENDING;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the moId
	 */
	public String getMoId() {
		return moId;
	}

	/**
	 * @param moId the moId to set
	 */
	public void setMoId(String moId) {
		this.moId = moId;
	}

	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}

	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the routeStatus
	 */
	public String getRouteStatus() {
		return routeStatus;
	}

	/**
	 * @param routeStatus the routeStatus to set
	 */
	public void setRouteStatus(String routeStatus) {
		this.routeStatus = routeStatus;
	}
}
