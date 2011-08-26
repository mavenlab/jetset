package com.mavenlab.jetset.rest;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import com.mavenlab.jetset.controller.EntryController;
import com.mavenlab.jetset.model.MOLog;
import com.mavenlab.jetset.model.SMSEntry;
import com.mavenlab.jetset.model.Station;

@Path("/mo")
@Stateless
public class MOReceiver {

	@Inject
	@Category("jetset.MOReceiver")
	private Logger log;
	
	public final static String PATTERN = "^\\s*SHELL\\s+.+\\s+[A-Z]?[0-9]{7}[A-Z]?\\s+([0-9]\\-)?[0-9]{7}\\s+[0-9]{1,3}\\s+[YN]\\s*$";
	
	public final static String PATTERN_KEYWORD = "SHELL";
	public final static String PATTERN_MEMBER = "\\b[YN]$";
	public final static String PATTERN_RECEIPT = "\\b([0-9]\\-)?[0-9]{7}\\b";
	public final static String PATTERN_NRIC = "\\b[A-Z]?[0-9]{7}[A-Z]?\\b";
	public final static String PATTERN_STATION = "\\b[0-9]{1,3}\\b";
	
	@Inject
	private EntityManager em;
	
	@Inject
	private EntryController entryController;
	
	public static Lock lock = new ReentrantLock();

	private final static int outrouteID = -1;
	private final static String URL = "http://gavri.mavenlab.com/Gavri/GavriSender";
	private final static String username = "mvnprojects2";
	private final static String password = "92279545ff";
	private final static String destination = "Shell";
	private final static String channel = "SMS";
	
	public static AtomicInteger countPrize;
	
	@GET
	@Produces("text/plain")
	public String receive(@QueryParam("moId") String moId, 
			@QueryParam("msisdn") String msisdn, @QueryParam("message") String message) {
		
		try {
			MOReceiver.lock.tryLock(900, TimeUnit.SECONDS);
			log.info("Message: " + message);
			log.info("MSISDN: " + msisdn);
			log.info("moId: " + moId);
			
			MOLog moLog = new MOLog();
			moLog.setMoId(moId);
			moLog.setMessage(message);
			moLog.setMsisdn(msisdn);
			moLog.setDateReceived(new Date());
			em.persist(moLog);
			

			if(message.matches(PATTERN)) {
				//TODO: SEND MT
				return "Invalid Message";
			}

			
//			MTLog mtLog = new MTLog();
//			mtLog.setDestination(msisdn);
//			mtLog.setOriginator(destination);
//			mtLog.setOutrouteId(outrouteID);
//			mtLog.setMoLog(moLog);
//			mtLog.setMessage("GRATZ");
//			em.persist(mtLog);
//			log.info("MT PERSIST XXXXXXXXXXXXX");
//			
			SMSEntry smsEntry = parseMessage(moLog);
			
			if(smsEntry.getStatus().equals("invalid")) {
				//TODO: SEND MT
				return "Invalid Entry";
			}

			
			boolean duplicate = entryController.isDuplicate(smsEntry);
			
			if(duplicate) {
				//TODO: SEND MT DUPE
				smsEntry.setStatus("duplicate");
				return "duplicate";
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		MOReceiver.lock.unlock();
		return "OK";
	}
	
	private SMSEntry parseMessage(MOLog moLog) {
		String message = moLog.getMessage().toUpperCase().trim();
		
		SMSEntry smsEntry = new SMSEntry();
		smsEntry.setMsisdn(moLog.getMsisdn());
		smsEntry.setMoLog(moLog);

		message = message.trim().toUpperCase();
		message = message.replace("SHELL", "").trim();

		String member = StringUtils.substring(message, -1);
		message = StringUtils.substring(message, 0, -2);
		smsEntry.setUobMember(member.equals("Y"));
		int chance = member.equals("Y") ? 1 : 2;
		smsEntry.setChance(chance);
		
		Pattern receiptPattern = Pattern.compile(PATTERN_RECEIPT);
		Matcher receiptMatcher = receiptPattern.matcher(message);

		if(receiptMatcher.find()) {
			String receipt = receiptMatcher.group();
			smsEntry.setReceipt(receipt);
			message = receiptMatcher.replaceFirst("");
		}

		Pattern nricPattern = Pattern.compile(PATTERN_NRIC);
		Matcher nricMatcher = nricPattern.matcher(message);

		if(nricMatcher.find()) {
			String nric = nricMatcher.group();
			smsEntry.setNric(nric);
			message = nricMatcher.replaceFirst("");
		}

		Pattern stationPattern = Pattern.compile(PATTERN_STATION);
		Matcher stationMatcher = stationPattern.matcher(message);

		if(stationMatcher.find()) {
			String stationId = stationMatcher.group();
			try {
				Station station = (Station) em.createNamedQuery("jetset.query.Station.findById").setParameter("id", Integer.parseInt(stationId)).getSingleResult();
				smsEntry.setStation(station);
			} catch(NoResultException e) {
				smsEntry.setStatus("invalid");
			}
			message = stationMatcher.replaceFirst("");
		}

		String name = message.trim();
		smsEntry.setName(name);

		em.persist(smsEntry);
		
		return smsEntry;
	}
//	
//	public static void main(String[] args) {
//		String message = "SHELL samuel Edison s1345678 1-1234567 123 N";
//		System.out.println(message.toUpperCase().matches(PATTERN));
//		
//		message = message.trim().toUpperCase();
//		message = message.replace("SHELL", "").trim();
//		
//		String member = StringUtils.substring(message, -1);
//		message = StringUtils.substring(message, 0, -2);
//		
//		Pattern receiptPattern = Pattern.compile(PATTERN_RECEIPT);
//		Matcher receiptMatcher = receiptPattern.matcher(message);
//
//		if(receiptMatcher.find()) {
//			String receipt = receiptMatcher.group();
//			message = receiptMatcher.replaceFirst("");
//		}
//
//		System.out.println(message);
//
//		Pattern nricPattern = Pattern.compile(PATTERN_NRIC);
//		Matcher nricMatcher = nricPattern.matcher(message);
//
//		if(nricMatcher.find()) {
//			String nric = nricMatcher.group();
//			message = nricMatcher.replaceFirst("");
//		}
//
//		Pattern stationPattern = Pattern.compile(PATTERN_STATION);
//		Matcher stationMatcher = stationPattern.matcher(message);
//
//		if(stationMatcher.find()) {
//			String station = stationMatcher.group();
//			message = stationMatcher.replaceFirst("");
//		}
//
//		String name = message.trim();
//	}
}
