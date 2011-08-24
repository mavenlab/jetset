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

@Entity
@Table(name = "entries")
@SequenceGenerator(
		name = "SEQ_ENTRY",
		sequenceName = "sequence_entry",
		initialValue = 1,
		allocationSize = 1
)
public class Entry extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2465327037821486215L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTRY")
	private int id;
	
	@Column(name = "msisdn", nullable = false)
	private String msisdn;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "nric", nullable = true)
	private String nric;
	
	@Column(name = "receipt", nullable = true)
	private String receipt;
	
	@Column(name = "station", nullable = true)
	private String station;
	
	@Column(name = "uob_member", nullable = true)
	private boolean uobMember;
	
	@Column(name = "chances", nullable = true)
	private int chance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mo_log_id", nullable = false)
	private MOLog moLog;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "mt_log_id", nullable = false)
//	private MTLog mtLog;

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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public boolean isUobMember() {
		return uobMember;
	}

	public void setUobMember(boolean uobMember) {
		this.uobMember = uobMember;
	}

	/**
	 * @return the moLog
	 */
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
	 * @return the mtLog
	 */
//	public MTLog getMtLog() {
//		return mtLog;
//	}
//
//	/**
//	 * @param mtLog the mtLog to set
//	 */
//	public void setMtLog(MTLog mtLog) {
//		this.mtLog = mtLog;
//	}

	/**
	 * @return the chance
	 */
	public int isChance() {
		return chance;
	}

	/**
	 * @param chance the chance to set
	 */
	public void setChance(int chance) {
		this.chance = chance;
	}
}
