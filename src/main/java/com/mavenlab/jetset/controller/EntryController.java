package com.mavenlab.jetset.controller;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.mavenlab.jetset.model.Entry;

@Stateless
public class EntryController {

	@Inject
	private EntityManager em;
	
	public Entry getEntryFetchAll(int id) {
		try {
			return (Entry) em.createNamedQuery("jetset.query.Entry.findIdFetchAll").setParameter("id", id).getSingleResult();
		} catch(NoResultException e) {
		}

		return null;
	}
	
	public boolean isDuplicate(Entry entry, boolean web) {
		
		long count = (Long) em.createNamedQuery("jetset.query.Entry.duplicateCheck").
								setParameter("receipt", entry.getReceipt()).
								setParameter("stationId", entry.getStation().getId()).
								getSingleResult();

		return (!web && count > 1) || (web && count > 0);
	}

	/**
	 * count all
	 * 
	 * @return
	 */
	public long countAll() {
		return (Long) em.createNamedQuery("jetset.query.Entry.countAll").getSingleResult();
	}
	
	/**
	 * fetch all with offset and limit
	 * 
	 * @param offset
	 * @param limit
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Entry> fetchAll(int offset, int limit) {
		return em.createNamedQuery("jetset.query.Entry.findFetchAll").
				setFirstResult(offset).
				setMaxResults(limit).
				getResultList();
	}

	/**
	 * count by msisdn
	 * 
	 * @param msisdn
	 * @return
	 */
	public long countByMsisdn(String msisdn) {
		return (Long) em.createNamedQuery("jetset.query.Entry.countByMsisdn").setParameter("msisdn", msisdn).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<Entry> fetchByMsisdn(String msisdn, int offset, int limit) {
		return em.createNamedQuery("jetset.query.Entry.findFetchByMsisdn").
				setParameter("msisdn", msisdn).
				setFirstResult(offset).
				setMaxResults(limit).
				getResultList();
	}
	
	/**
	 * count by date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public long countByDate(Date startDate, Date endDate) {
		if(startDate != null && endDate != null) {
			return (Long) em.createNamedQuery("jetset.query.Entry.countByDateRange").
					setParameter("startDate", startDate).
					setParameter("endDate", endDate).
					getSingleResult();
		} else if(startDate != null) {
			return (Long) em.createNamedQuery("jetset.query.Entry.countByStartDate").
					setParameter("startDate", startDate).
					getSingleResult();
		} else if(endDate != null) {
			return (Long) em.createNamedQuery("jetset.query.Entry.countByEndDate").
					setParameter("endDate", endDate).
					getSingleResult();
		}
		
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	public List<Entry> fetchByDate(Date startDate, Date endDate, int offset, int limit) {
		if(startDate != null && endDate != null) {
			return em.createNamedQuery("jetset.query.Entry.findFetchByDateRange").
					setParameter("startDate", startDate).
					setParameter("endDate", endDate).
					setFirstResult(offset).
					setMaxResults(limit).
					getResultList();
		} else if(startDate != null) {
			return em.createNamedQuery("jetset.query.Entry.findFetchByStartDate").
					setParameter("startDate", startDate).
					setFirstResult(offset).
					setMaxResults(limit).
					getResultList();
		} else if(endDate != null) {
			return em.createNamedQuery("jetset.query.Entry.findFetchByEndDate").
					setParameter("endDate", endDate).
					setFirstResult(offset).
					setMaxResults(limit).
					getResultList();
		}
		
		return null;
	}
}