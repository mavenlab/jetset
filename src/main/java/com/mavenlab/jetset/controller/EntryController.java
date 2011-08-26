package com.mavenlab.jetset.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.mavenlab.jetset.model.Entry;

@Stateless
public class EntryController {

	@Inject
	private EntityManager em;
	
	public boolean isDuplicate(Entry entry) {
		
		long count = (Long) em.createNamedQuery("jetset.query.Entry.duplicateCheck").
								setParameter("receipt", entry.getReceipt()).
								setParameter("stationId", entry.getStation().getId()).
								getSingleResult();

		return (count > 1);
	}
	
}
