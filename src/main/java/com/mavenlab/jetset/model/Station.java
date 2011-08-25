package com.mavenlab.jetset.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "station")
@SequenceGenerator(
		name = "SEQ_STATION",
		sequenceName = "sequence_station",
		initialValue = 1,
		allocationSize = 1
)

public class Station extends EntityBase{

	/**
	 * 
	 */
	private static final long serialVersionUID = 790503819392315123L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MT")
	private int id;
	
	@Column(name = "station_number", nullable = false)
	private String number;
	
	@Column(name = "name", nullable = false)
	private String name;
}
