package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "prizes")
@SequenceGenerator(
		name = "SEQ_PRIZE",
		sequenceName = "sequence_prize",
		initialValue = 1,
		allocationSize = 1
)
public class Prize extends EntityBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1774525295004082676L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MT")
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "message", nullable = false)
	private String message;

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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
