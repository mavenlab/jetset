package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mavenlab.jetset.model.Keyword;
import com.mavenlab.jetset.model.ReplyMessage;

@NamedQueries({
	@NamedQuery(name = "Jetset.KeywordField.activeRegex", 
			query = "SELECT keywordField FROM KeywordField keywordField " +
			"WHERE keywordField.keyword.id = :keywordID " + 
			"AND keywordField.status.status = 'active' " +
			"ORDER BY keywordField.position ASC")
})

@Entity
@Table(name = "keyword_fields")
@SequenceGenerator(
		name = "SEQ_KEYFIELD",
		sequenceName = "sequence_keyfield",
		initialValue = 1,
		allocationSize = 1
)
public class KeywordField extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2440147590351754379L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_KEYFIELD")
	private int id;
	
	@Column(name = "pattern", nullable = true)
	private String pattern;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "position", nullable = true)
	private int position;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "keyword", nullable = false)
	private Keyword keyword;
	
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
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
