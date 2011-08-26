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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRIZE")
	private int id;

	@Column(name = "name", length = 200, nullable = false)
	private String name;

	@Column(name = "sms_message", columnDefinition = "text", nullable = false)
	private String smsMessage;

	@Column(name = "web_message", columnDefinition = "text", nullable = false)
	private String webMessage;

	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	/**
	 * default constructor
	 */
	public Prize() {
		super();
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

	/**
	 * @return the smsMessage
	 */
	public String getSmsMessage() {
		return smsMessage;
	}

	/**
	 * @param smsMessage the smsMessage to set
	 */
	public void setSmsMessage(String smsMessage) {
		this.smsMessage = smsMessage;
	}

	/**
	 * @return the webMessage
	 */
	public String getWebMessage() {
		return webMessage;
	}

	/**
	 * @param webMessage the webMessage to set
	 */
	public void setWebMessage(String webMessage) {
		this.webMessage = webMessage;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the quantity
	 */
	public String getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
