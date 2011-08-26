package com.mavenlab.jetset.rest;

import java.util.Date;
import java.util.Map;
import java.util.Random;
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
import com.mavenlab.jetset.controller.PrizeController;
import com.mavenlab.jetset.model.MOLog;
import com.mavenlab.jetset.model.MTLog;
import com.mavenlab.jetset.model.Prize;
import com.mavenlab.jetset.model.SMSEntry;
import com.mavenlab.jetset.model.Station;
import com.mavenlab.jetset.util.GavriSenderUtil;

@Path("/mo")
@Stateless
public class MOReceiver {

	@Inject
	@Category("jetset.MOReceiver")
	private Logger log;
	
	public final static String PATTERN = "^\\s*SHELL\\s+.+\\s+[A-Z]?[0-9]{7}[A-Z]?\\s+([1-3]\\-[0-9]{7}|[0-9]{5}|1\\-[0-9]{5})\\s+[0-9]{1,3}\\s+[YN]\\s*$";
	
	public final static String PATTERN_KEYWORD = "SHELL";
	public final static String PATTERN_MEMBER = "\\b[YN]$";
	public final static String PATTERN_RECEIPT = "\\b([1-3]\\-[0-9]{7}|[0-9]{5}|1\\-[0-9]{5})\\b";
	public final static String PATTERN_NRIC = "\\b[A-Z]?[0-9]{7}[A-Z]?\\b";
	public final static String PATTERN_STATION = "\\b[0-9]{1,3}\\b";
	
	public final static String INVALID_MESSAGE = "Invalid entry. Pls check ur SMS is sent as <SHELL><NRIC/Passport><Receipt no><Station no><UOB Y/N> & resend.For assistance, call 1800-467-4355 Mon-Fri, 9am-5pm.";
	public final static String DUPLICATE_MESSAGE = "Thank you for your SMS. We have already received this entry. Please check your SMS is correct. For assitance, call 1800-467-4355 Mon-Fri, 9am-5pm.";

	@Inject
	private EntityManager em;
	
	@Inject
	private EntryController entryController;
	
	@Inject
	private PrizeController prizeController;
	
	public static Lock lock = new ReentrantLock();

	private final static int outrouteId = 2;
	private final static String username = "mvnprojects2";
	private final static String password = "92279545ff";
	private final static String orga = "SHELL";
	
	public static AtomicInteger countPrize;
	
	@GET
	@Produces("text/plain")
	public synchronized String receive(@QueryParam("moId") int moId, 
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

			MTLog mtLog = new MTLog();
			mtLog.setDestination(msisdn);
			mtLog.setOriginator(orga);
			mtLog.setOutrouteId(outrouteId);
			mtLog.setMoLog(moLog);

			if(message.matches(PATTERN)) {
				mtLog.setMessage(INVALID_MESSAGE);
				replyMessage(mtLog);
				return "Invalid Message";
			}

			
			SMSEntry smsEntry = parseMessage(moLog);
			
			if(smsEntry.getStatus().equals("invalid")) {
				mtLog.setMessage(INVALID_MESSAGE);
				replyMessage(mtLog);
				return "Invalid Entry";
			}

			boolean duplicate = entryController.isDuplicate(smsEntry);
			
			if(duplicate) {
				smsEntry.setStatus("duplicate");
				mtLog.setMessage(INVALID_MESSAGE);
				replyMessage(mtLog);
				return "duplicate";
			}
			
			Prize prize = prizeController.getRandomPrize();
			
			if(prize == null) {
				smsEntry.setStatus("no prize");
				mtLog.setMessage(DUPLICATE_MESSAGE);
				replyMessage(mtLog);
				return "no prize";
			}
			
			smsEntry.setPrize(prize);
			mtLog.setMessage(prize.getSmsMessage());
			replyMessage(mtLog);

			return "OK";
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} finally {
			MOReceiver.lock.unlock();
		}
		
		return "LOCKED";
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

		em.persist(smsEntry);
		
		return smsEntry;
	}
	
	/**
	 * get random prize
	 * 
	 * @return
	 */
	public Prize getRandomPrize() {
		Random rnd = new Random(System.currentTimeMillis());
		int prizeId = rnd.nextInt(3) + 1;
		try {
			Prize prize = (Prize) em.createNamedQuery("jetset.query.Prize.findById").setParameter("id", prizeId).getSingleResult();
			int quantity = prize.getQuantity() - 1;
			prize.setQuantity(quantity);
			return prize;
		} catch (NoResultException e) {
			log.error("PRIZE WITH ID NOT FOUND: " + prizeId);
		}
		return null;
	}

	private void replyMessage(MTLog mtLog) {
	       Map<String, Object> resp = GavriSenderUtil.sendTextMessage(null, username, password, 
	                   outrouteId, orga, mtLog.getDestination(), mtLog.getMessage(), 
	                   mtLog.getMoLog().getMoId(), null);
	       
	       log.info("REPLY: " + resp);
	       
	       mtLog.setMtLogId((String) resp.get("id"));
	       mtLog.setCount((Integer) resp.get("count"));
	       mtLog.setStatusId(resp.get("statusId").toString());
	       em.persist(mtLog);
	   }
}
