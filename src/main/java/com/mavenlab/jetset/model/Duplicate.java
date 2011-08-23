package com.mavenlab.jetset.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.mavenlab.jetset.model.KeywordField;
import com.mavenlab.jetset.model.ReplyMessage;

@Entity
@Table(name = "duplicates")
public class Duplicate extends EntityBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2698497583231687468L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MT")
	private int id;

	@ManyToMany
	@JoinTable(name = "duplicate_keyword_fields", 
		joinColumns = { @JoinColumn(name = "duplicate_id") },
		inverseJoinColumns = { @JoinColumn(name = "keyword_field_id") })
	private Set<KeywordField> keywordField;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "duplicate_message", nullable = true)
	private ReplyMessage duplicate;

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
	 * @return the keywordField
	 */
	public Set<KeywordField> getKeywordField() {
		return keywordField;
	}

	/**
	 * @param keywordField the keywordField to set
	 */
	public void setKeywordField(Set<KeywordField> keywordField) {
		this.keywordField = keywordField;
	}

	/**
	 * @return the duplicate
	 */
	public ReplyMessage getDuplicate() {
		return duplicate;
	}

	/**
	 * @param duplicate the duplicate to set
	 */
	public void setDuplicate(ReplyMessage duplicate) {
		this.duplicate = duplicate;
	}
}
