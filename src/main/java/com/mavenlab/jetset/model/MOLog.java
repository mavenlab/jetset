package com.mavenlab.jetset.model;

import java.util.Date;

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

	public final static String STATUS_PENDING = "PENDING";
	public final static String STATUS_SUCCESS = "SUCCESS";
	public final static String STATUS_FAILED = "FAILED";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MO")
	private int id;
	
	@Column(name = "message", nullable = false, length = 250)
	private String message;
	
	@Column(name = "mo_id", nullable = false)
	private int moId;
	
	@Column(name = "msisdn", length = 20, nullable = false)
	private String msisdn;
	
	@Column(name = "date_received", nullable = false)
	private Date dateReceived;

	public MOLog() {
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
	public int getMoId() {
		return moId;
	}

	/**
	 * @param moId the moId to set
	 */
	public void setMoId(int moId) {
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
	 * @return the dateReceived
	 */
	public Date getDateReceived() {
		return dateReceived;
	}

	/**
	 * @param dateReceived the dateReceived to set
	 */
	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

}
