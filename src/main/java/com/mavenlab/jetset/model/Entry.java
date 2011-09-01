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

import org.hibernate.annotations.Index;

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
	@NamedQuery(name = "jetset.query.Entry.findIdFetchAll", 
			query = "Select entry FROM Entry entry " +
					"LEFT JOIN FETCH entry.station " +
					"LEFT JOIN FETCH entry.prize " +
					"WHERE entry.id = :id " +
					"AND entry.status = 'active'"),
	@NamedQuery(name = "jetset.query.Entry.countAll", 
			query = "Select COUNT(id) FROM Entry " +
					"WHERE status != 'inactive'"),
	@NamedQuery(name = "jetset.query.Entry.findFetchAll", 
			query = "Select entry FROM Entry entry " +
					"LEFT JOIN FETCH entry.station " +
					"LEFT JOIN FETCH entry.prize " +
					"WHERE entry.status != 'inactive' " +
					"ORDER BY entry.id"),
	@NamedQuery(name = "jetset.query.Entry.countByMsisdn", 
			query = "Select COUNT(id) FROM Entry " +
					"WHERE status != 'inactive' " +
					"AND msisdn LIKE '%' || :msisdn || '%'"),
	@NamedQuery(name = "jetset.query.Entry.findFetchByMsisdn", 
			query = "Select entry FROM Entry entry " +
					"LEFT JOIN FETCH entry.station " +
					"LEFT JOIN FETCH entry.prize " +
					"WHERE entry.status != 'inactive' " +
					"AND entry.msisdn LIKE '%' || :msisdn || '%' " +
					"ORDER BY entry.id"),
	@NamedQuery(name = "jetset.query.Entry.countByStartDate", 
			query = "Select COUNT(id) FROM Entry " +
					"WHERE status != 'inactive' " +
					"AND createdAt >= :startDate"),
	@NamedQuery(name = "jetset.query.Entry.findFetchByStartDate", 
			query = "Select entry FROM Entry entry " +
					"LEFT JOIN FETCH entry.station " +
					"LEFT JOIN FETCH entry.prize " +
					"WHERE entry.status != 'inactive' " +
					"AND entry.createdAt >= :startDate " +
					"ORDER BY entry.id"),
	@NamedQuery(name = "jetset.query.Entry.countByEndDate", 
			query = "Select COUNT(id) FROM Entry " +
					"WHERE status != 'inactive' " +
					"AND createdAt <= :endDate"),
	@NamedQuery(name = "jetset.query.Entry.findFetchByEndDate", 
			query = "Select entry FROM Entry entry " +
					"LEFT JOIN FETCH entry.station " +
					"LEFT JOIN FETCH entry.prize " +
					"WHERE entry.status != 'inactive' " +
					"AND entry.createdAt <= :endDate " +
					"ORDER BY entry.id"),
	@NamedQuery(name = "jetset.query.Entry.countByDateRange", 
			query = "Select COUNT(id) FROM Entry " +
					"WHERE status != 'inactive' " +
					"AND createdAt BETWEEN :startDate AND :endDate"),
	@NamedQuery(name = "jetset.query.Entry.findFetchByDateRange", 
			query = "Select entry FROM Entry entry " +
					"LEFT JOIN FETCH entry.station " +
					"LEFT JOIN FETCH entry.prize " +
					"WHERE entry.status != 'inactive' " +
					"AND entry.createdAt BETWEEN :startDate AND :endDate " +
					"ORDER BY entry.id"),
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
	@Index(name = "ix_entries_msisdn")
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
	
	public Entry() {
		chance = 1;
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
