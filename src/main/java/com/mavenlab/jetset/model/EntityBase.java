package com.mavenlab.jetset.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class EntityBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1317671392593253169L;
	
	public final static String STATUS_ACTIVE = "active";
	public final static String STATUS_INACTIVE = "inactive";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;
	
	@Column(name = "creator", nullable = false, length = 200)
	private String creator;
	
	@Column(name = "updater", nullable = false, length = 200)
	private String updater;
	
	@Column(name = "status", nullable = false, length = 20)
	private String status;
	
	public EntityBase() {
		status = STATUS_ACTIVE;
		creator = "System";
		updater = "System";
		createdAt = new Date();
		updatedAt = createdAt;
	}
	
	public EntityBase(String username) {
		status = STATUS_ACTIVE;
		creator = username;
		updater = username;
		createdAt = new Date();
		updatedAt = createdAt;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdOn) {
		this.createdAt = createdOn;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date modifiedOn) {
		this.updatedAt = modifiedOn;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return the updater
	 */
	public String getUpdater() {
		return updater;
	}

	/**
	 * @param updater the updater to set
	 */
	public void setUpdater(String updater) {
		this.updater = updater;
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

	@PreUpdate
	public void updateTimeStamp() {
		updatedAt = new Date();
	}
}
