package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mavenlab.jetset.model.Keyword;
import com.mavenlab.jetset.model.MOLog;
import com.mavenlab.jetset.model.MTLog;

@Entity
@Table(name = "entries")
public class Entry extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2465327037821486215L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTRY")
	private int id;
	
	@Column(name = "entry", nullable = true)
	private String entry;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword", nullable = false)
	private Keyword keyword;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mo_log_id", nullable = false)
	private MOLog moLog;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "mt_log_id", nullable = false)
	private MTLog mtLog;

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
	 * @return the entry
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * @param entry the entry to set
	 */
	public void setEntry(String entry) {
		this.entry = entry;
	}

	/**
	 * @return the keyword
	 */
	public Keyword getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
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
	public MTLog getMtLog() {
		return mtLog;
	}

	/**
	 * @param mtLog the mtLog to set
	 */
	public void setMtLog(MTLog mtLog) {
		this.mtLog = mtLog;
	}
}
