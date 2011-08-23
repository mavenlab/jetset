package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mavenlab.jetset.model.ReplyMessage;

@NamedQueries({
	@NamedQuery(name = "Jetset.Keyword.findKeyword", 
			query = "SELECT keyword FROM Keyword keyword " +
			"WHERE keyword.keyword = :keywordChecked " + 
			"AND keyword.status.status = 'active'")
})
@Entity
@Table(name = "keywords")
	
public class Keyword extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5383192300763112245L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KEY")
	private int id;
	
	@Column(name = "keyword", nullable = false)
	private String keyword;
	
	@Column(name = "pattern", nullable = false)
	private String pattern;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "valid_message", nullable = true)
	private ReplyMessage valid;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "invalid_message", nullable = true)
	private ReplyMessage invalid;

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
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the pattern
	 */
	public String getPattern() {
		return pattern;
	}

	/**
	 * @param pattern the pattern to set
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	/**
	 * @return the valid
	 */
	public ReplyMessage getValid() {
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(ReplyMessage valid) {
		this.valid = valid;
	}

	/**
	 * @return the invalid
	 */
	public ReplyMessage getInvalid() {
		return invalid;
	}

	/**
	 * @param invalid the invalid to set
	 */
	public void setInvalid(ReplyMessage invalid) {
		this.invalid = invalid;
	}
	
	
}
