package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mavenlab.jetset.model.MOLog;

@Entity
@Table(name = "mt_logs")
@SequenceGenerator(
		name = "SEQ_MT",
		sequenceName = "sequence_mt",
		initialValue = 1,
		allocationSize = 1
)
public class MTLog extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2378033160872931826L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MT")
	private int id;
	
	@Column(name = "message", nullable = false)
	private String message;
	
	@Column(name = "outrouteId", nullable = false)
	private int outrouteId;
	
	@Column(name = "originator", nullable = false)
	private String originator;
	
	@Column(name = "destination", nullable = false)
	private String destination;
	
	@Column(name = "mtLogId", nullable = false)
	private String mtLogId;
	
	@Column(name = "statusId", nullable = false)
	private String statusId;
	
	@Column(name = "mt_count", nullable = false)
	private int count;
	
	private MOLog moLog;
	private Keyword keyword;
	

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
	 * @return the outrouteId
	 */
	public int getOutrouteId() {
		return outrouteId;
	}

	/**
	 * @param outrouteId the outrouteId to set
	 */
	public void setOutrouteId(int outrouteId) {
		this.outrouteId = outrouteId;
	}

	/**
	 * @return the originator
	 */
	public String getOriginator() {
		return originator;
	}

	/**
	 * @param originator the originator to set
	 */
	public void setOriginator(String originator) {
		this.originator = originator;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the mtLogId
	 */
	public String getMtLogId() {
		return mtLogId;
	}

	/**
	 * @param mtLogId the mtLogId to set
	 */
	public void setMtLogId(String mtLogId) {
		this.mtLogId = mtLogId;
	}

	/**
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId the statusId to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}

	/**
	 * @return the moLog
	 */
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="mo_log_id", nullable=false)
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
	 * @return the keyword
	 */
	public Keyword getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
}
