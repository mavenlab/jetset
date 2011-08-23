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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.mavenlab.jetset.model.Entry;

@NamedQueries({
	@NamedQuery(name = "JetSet.EntryField.findDuplicate", 
			query = "SELECT COUNT(*) FROM EntryField entryField " +
			"WHERE entryField.name = 'receipt number' " +
			"AND entryField.field = :receiptNumber " +
			"AND entryField.entry.status.status = 'active'")
})

@Entity
@Table(name = "entry_fields")
@SequenceGenerator(
		name = "SEQ_ENTRYF",
		sequenceName = "sequence_entryf",
		initialValue = 1,
		allocationSize = 1
)
public class EntryField extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4883526605791769107L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTRYF")
	private int id;
	
	@Column(name = "field", nullable = true)
	private String field;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "entry", nullable = false)
	private Entry entry;

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
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @param field the field to set
	 */
	public void setField(String field) {
		this.field = field;
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
	 * @return the entry
	 */
	public Entry getEntry() {
		return entry;
	}

	/**
	 * @param entry the entry to set
	 */
	public void setEntry(Entry entry) {
		this.entry = entry;
	}
	
	
}
