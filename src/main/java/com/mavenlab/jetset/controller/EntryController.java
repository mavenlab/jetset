package com.mavenlab.jetset.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.mavenlab.jetset.model.Entry;

@Stateless
public class EntryController {
	
	public static final int WEEK1 = 20110911;
	public static final int WEEK2 = 20110918;
	public static final int WEEK3 = 20110925;
	public static final int WEEK4 = 20111002;
	public static final int WEEK5 = 20111009;
	public static final int WEEK6 = 20111016;
	public static final int WEEK7 = 20111023;
	public static final int WEEK8 = 20111030;
	public static final int WEEK9 = 20111106;
	public static final int WEEK10 = 20111113;
	public static final int WEEK11 = 20111120;
	public static final int WEEK12 = 20111130;

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
		
//		String dt = new SimpleDateFormat("yyyyMMdd").format(new Date());
//		int dtNow = Integer.parseInt(dt);
//		
//		Calendar calStart = Calendar.getInstance();
//		calStart.setTime(new Date());
//
//		Calendar calEnd = Calendar.getInstance();
//		calEnd.setTime(new Date());
//		
//		if(dtNow <= WEEK1) {
//			calStart.set(2011, Calendar.SEPTEMBER, 1, 0, 0, 0);
//			calEnd.set(2011, Calendar.SEPTEMBER, 11, 23, 59, 59);
//		} else if(dtNow <= WEEK2) {
//			calStart.set(2011, Calendar.SEPTEMBER, 12, 0, 0, 0);
//			calEnd.set(2011, Calendar.SEPTEMBER, 18, 23, 59, 59);
//		} else if(dtNow <= WEEK3) {
//			calStart.set(2011, Calendar.SEPTEMBER, 19, 0, 0, 0);
//			calEnd.set(2011, Calendar.SEPTEMBER, 25, 23, 59, 59);
//		} else if(dtNow <= WEEK4) {
//			calStart.set(2011, Calendar.SEPTEMBER, 26, 0, 0, 0);
//			calEnd.set(2011, Calendar.OCTOBER, 02, 23, 59, 59);
//		} else if(dtNow <= WEEK5) {
//			calStart.set(2011, Calendar.OCTOBER, 3, 0, 0, 0);
//			calEnd.set(2011, Calendar.OCTOBER, 9, 23, 59, 59);
//		} else if(dtNow <= WEEK6) {
//			calStart.set(2011, Calendar.OCTOBER, 10, 0, 0, 0);
//			calEnd.set(2011, Calendar.OCTOBER, 16, 23, 59, 59);
//		} else if(dtNow <= WEEK7) {
//			calStart.set(2011, Calendar.OCTOBER, 17, 0, 0, 0);
//			calEnd.set(2011, Calendar.OCTOBER, 23, 23, 59, 59);
//		} else if(dtNow <= WEEK8) {
//			calStart.set(2011, Calendar.OCTOBER, 24, 0, 0, 0);
//			calEnd.set(2011, Calendar.OCTOBER, 30, 23, 59, 59);
//		} else if(dtNow <= WEEK9) {
//			calStart.set(2011, Calendar.OCTOBER, 31, 0, 0, 0);
//			calEnd.set(2011, Calendar.NOVEMBER, 6, 23, 59, 59);
//		} else if(dtNow <= WEEK10) {
//			calStart.set(2011, Calendar.NOVEMBER, 7, 0, 0, 0);
//			calEnd.set(2011, Calendar.NOVEMBER, 13, 23, 59, 59);
//		} else if(dtNow <= WEEK11) {
//			calStart.set(2011, Calendar.NOVEMBER, 14, 0, 0, 0);
//			calEnd.set(2011, Calendar.NOVEMBER, 20, 23, 59, 59);
//		} else if(dtNow <= WEEK12) {
//			calStart.set(2011, Calendar.NOVEMBER, 21, 0, 0, 0);
//			calEnd.set(2011, Calendar.NOVEMBER, 30, 23, 59, 59);
//		}
		
		long count = (Long) em.createNamedQuery("jetset.query.Entry.duplicateCheck").
								setParameter("receipt", entry.getReceipt()).
								setParameter("stationId", entry.getStation().getId()).
//								setParameter("start", calStart.getTime()).
//								setParameter("end", calEnd.getTime()).
								getSingleResult();

		return (!web && count > 1) || (web && count > 0);
	}
	
}
