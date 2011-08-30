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
	
	@Column(name = "email", length = 50)
	private String email;
	
	@Column(name = "member")
	private boolean member;

	@Column(name = "agree")
	private boolean agree;
	
	@Column(name = "subscribe")
	private boolean subscribe;

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

	/**
	 * @return the member
	 */
	public boolean isMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(boolean member) {
		this.member = member;
	}

	/**
	 * @return the agree
	 */
	public boolean isAgree() {
		return agree;
	}

	/**
	 * @param agree the agree to set
	 */
	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	/**
	 * @return the subscribe
	 */
	public boolean isSubscribe() {
		return subscribe;
	}

	/**
	 * @param subscribe the subscribe to set
	 */
	public void setSubscribe(boolean subscribe) {
		this.subscribe = subscribe;
	}
	
}
