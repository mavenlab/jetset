package com.mavenlab.jetset.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SMS")
public class SMSEntry extends Entry {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1047371976228046915L;

	/**
	 * default constructor
	 */
	public SMSEntry() {
		super();
	}
}