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
			query = "SELECT createdAt FROM Entry WHERE station.id = :id " +
					"AND status = 'active' " +
					"ORDER BY id ASC"),
	@NamedQuery(name = "jetset.query.Entry.duplicateCheck", 
				query = "SELECT COUNT(id) FROM Entry " +
						"WHERE receipt = :receipt AND station.id = :stationId ")
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
	
	@Column(name = "nric", nullable = true)
	private String nric;
	
	@Column(name = "receipt", nullable = true)
	private String receipt;
	
	@Column(name = "uob_member", nullable = true)
	private boolean uobMember;
	
	@Column(name = "chances", nullable = true)
	private int chance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "station_id", nullable = true)
	private Station station;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "prize_id", nullable = true)
	private Prize prize;

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

	/**
	 * @return the prize
	 */
	public Prize getPrize() {
		return prize;
	}

	/**
	 * @param prize the prize to set
	 */
	public void setPrize(Prize prize) {
		this.prize = prize;
	}
}
