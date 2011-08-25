package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Table(name = "stations")

@NamedQueries({
	@NamedQuery(name = "jetset.query.Station.findStation", 
			query = "FROM Station WHERE id = :stationChecked " +
					"ORDER BY id ASC")
})
public class Station extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 790503819392315123L;

	@Id
	@Column(name = "id", nullable = false)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int number) {
		this.id = number;
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
}
