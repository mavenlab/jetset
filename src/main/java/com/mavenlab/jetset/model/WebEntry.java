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

	@Column(name = "name", length = 200, nullable = true)
	private String name;

	@Column(name = "payment_mode", length = 50)
	private String paymentMode;
	
	@Column(name = "fuel_grade", length = 50)
	private String fuelGrade;
	
	@Column(name = "cc_type", length = 50)
	private String ccType;

	/**
	 * default constructor
	 */
	public WebEntry() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
