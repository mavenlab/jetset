package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("WEB")
public class WebEntry extends Entry {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1047371976228046915L;

	@Column(name = "payment_mode", length = 50)
	private String paymentMode;
	
	@Column(name = "fuel_grade", length = 50)
	private String fuelGrade;
	
	@Column(name = "cc_type", length = 50)
	private String ccType;
	
	@Column(name = "email", length = 50)
	private String email;
	/**
	 * default constructor
	 */
	public WebEntry() {
		super();
	}

	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode() {
		return paymentMode;
	}

	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	/**
	 * @return the fuelGrade
	 */
	public String getFuelGrade() {
		return fuelGrade;
	}

	/**
	 * @param fuelGrade the fuelGrade to set
	 */
	public void setFuelGrade(String fuelGrade) {
		this.fuelGrade = fuelGrade;
	}

	/**
	 * @return the ccType
	 */
	public String getCcType() {
		return ccType;
	}

	/**
	 * @param ccType the ccType to set
	 */
	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
}
