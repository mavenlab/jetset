package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "entries")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "entry_type", discriminatorType = DiscriminatorType.STRING, length = 3)
@SequenceGenerator(
		name = "SEQ_ENTRY",
		sequenceName = "sequence_entry",
		initialValue = 1,
		allocationSize = 1
)
@NamedQueries({
	@NamedQuery(name = "jetset.query.Entry.findId", 
			query = "SELECT createdAt FROM Entry WHERE station_id = :id " +
					"AND status = 'active' " +
					"ORDER BY id ASC"),
	@NamedQuery(name = "jetset.query.Entry.duplicateCheck", 
				query = "SELECT COUNT(id) FROM Entry " +
						"WHERE receipt = :receipt AND station_id = :stationId " + 
						"AND WEEK(createdAt) = WEEK(curdate())")
})
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
	
	@Column(name = "uob_member", nullable = true)
	private boolean uobMember;
	
	@Column(name = "chances", nullable = true)
	private int chance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mo_log_id", nullable = false)
	private MOLog moLog;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mt_log_id", nullable = false)
	private MTLog mtLog;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;

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

	public boolean isUobMember() {
		return uobMember;
	}

	public void setUobMember(boolean uobMember) {
		this.uobMember = uobMember;
	}

	/**
	 * @return the mtLog
	 */
	public MTLog getMtLog() {
		return mtLog;
	}

	/**
	 * @param mtLog the mtLog to set
	 */
	public void setMtLog(MTLog mtLog) {
		this.mtLog = mtLog;
	}

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
	 * @return
	 */
	public Station getStation() {
		return station;
	}

	/**
	 * @param station
	 */
	public void setStation(Station station) {
		this.station = station;
	}
}
