package com.mavenlab.jetset.rest;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
	
	//public final static String PATTERN = "^\\s*(TSHELL|S(HELL)?)\\s+\\w+\\s+([1-3]\\-[0-9]{6}|([1-3]\\-)?[0-9]{5})\\s+[0-9]{1,2}\\s+[YN]\\s*$";
	//public final static String PATTERN = "^\\s*(TSHELL|S(HELL)?)\\s+\\w+\\s+([1-3]\\-[0-9]{5,6}|[0-9]{5})\\s+[0-9]{1,2}\\s+[YN]\\s*$";
	public final static String PATTERN = "^\\s*(TSHELL|S(HELL)?)\\s+\\w+\\s+([1-3]\\-[0-9]{1,7}|[0-9]{1,7})\\s+[0-9]{1,2}\\s+[YN]\\s*$";
	
	public final static String PATTERN_KEYWORD = "^\\s*(TSHELL|S(HELL)?)\\s?";
	public final static String PATTERN_MEMBER = "\\b[YN]$";
	//public final static String PATTERN_RECEIPT = "\\b([1-3]\\-[0-9]{6}|([1-3]\\-)?[0-9]{5})\\b";
	//public final static String PATTERN_RECEIPT = "\\b([1-3]\\-[0-9]{5,6}|[0-9]{5})\\b";
	public final static String PATTERN_RECEIPT = "\\b([1-3]\\-[0-9]{1,7}|[0-9]{1,7})\\b";
	//public final static String PATTERN_RECEIPT14 = "^([1-3]\\-)?[0-9]{5}$";
	public final static String PATTERN_NRIC = "\\b[A-Z]?[0-9]{7}[A-Z]?\\b";
	public final static String PATTERN_STATION = "\\b[0-9]{1,2}\\b";
	
	public final static String INVALID_MESSAGE = "Invalid entry. Pls check ur SMS is sent as <S><NRIC/Passport><Receipt no><Station no><UOB Y/N> & resend.For assistance, call 1800-467-4355 Mon-Fri, 9am-5pm.";
	public final static String DUPLICATE_MESSAGE = "Thank you for your SMS. We have already received this entry. Please check your SMS is correct. For assistance, call 1800-467-4355 Mon-Fri, 9am-5pm.";

	@Inject
	private EntityManager em;
	
	@Inject
	private EntryController entryController;
	
	@Inject
	private PrizeController prizeController;
	
//	@Inject
//	private StationController stationController;
	
	public static Lock lock = new ReentrantLock();

	private final static int outrouteId = 59;
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
			
			if(!message.toUpperCase().matches(PATTERN)) {
				log.info("INVALID PATTERN: " + message);
				mtLog.setMessage(INVALID_MESSAGE);
				replyMessage(mtLog);
				return "Invalid Message";
			}

			
			SMSEntry smsEntry = parseMessage(moLog);
			
			if(smsEntry.getStatus().equals("invalid")) {
				mtLog.setMessage(INVALID_MESSAGE);
				replyMessage(mtLog);
				smsEntry.setMtLog(mtLog);
				log.info("INVALID ENTRY: " + message);
				return "Invalid Entry";
			}

			boolean duplicate = entryController.isDuplicate(smsEntry, false);
			
			if(duplicate) {
				smsEntry.setStatus("duplicate");
				mtLog.setMessage(DUPLICATE_MESSAGE);
				replyMessage(mtLog);
				smsEntry.setMtLog(mtLog);
				return "duplicate";
			}
			
			Prize prize = prizeController.getRandomPrize();
			
			if(prize == null) {
				smsEntry.setStatus("no prize");
				//TODO SEND ALERT SMS
				return "no prize";
			}
			
			smsEntry.setPrize(prize);
			mtLog.setMessage(prize.getSmsMessage());
			replyMessage(mtLog);
			smsEntry.setMtLog(mtLog);

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
		String messages[] = message.split("\\s+");
		
		String member;
		
		int index = 1;
		int lastIndex = messages.length - 1;
		member = messages[lastIndex];
		
		if (member.length() == 1) {
			smsEntry.setChance(member.equals("Y") ? 2 : 1);
			smsEntry.setUobMember(member.equals("Y") ? true : false);
		} else {
			smsEntry.setStatus("invalid");
		}
		lastIndex--;
		
		try {
			int id = Integer.parseInt(messages[lastIndex]);
			Station station = (Station) em.createNamedQuery("jetset.query.Station.findById")
					.setParameter("id", id).getSingleResult();
			smsEntry.setStation(station);
		} catch(NoResultException e) {
			smsEntry.setStatus("invalid");
		} catch(NumberFormatException e) {
			smsEntry.setStatus("invalid");
		}
		lastIndex--;
		
		if (messages[lastIndex] == null || !messages[lastIndex].matches(PATTERN_RECEIPT)) {
			smsEntry.setStatus("invalid");
		} else {
			smsEntry.setReceipt(messages[lastIndex]);
		}
		lastIndex--;
		
		if (messages[lastIndex] == null) { //|| !messages[index].matches(PATTERN_NRIC)) {
			smsEntry.setStatus("invalid");
		} else {
			smsEntry.setNric(messages[lastIndex]);
		}
		index++;
		
		log.info("PERSIST SMS ENTRY");
		em.persist(smsEntry);		
		return smsEntry;
	}

	private void replyMessage(MTLog mtLog) {
		Map<String, Object> resp = GavriSenderUtil.sendTextMessage(null, username, password, 
               outrouteId, mtLog.getDestination(), orga, mtLog.getMessage(), 
               mtLog.getMoLog().getMoId(), null);
   
		log.info("REPLY: " + resp);
		mtLog.setMtLogId((String) resp.get("id"));
		mtLog.setCount((Integer) resp.get("count"));
		mtLog.setStatusId(resp.get("statusId").toString());
		em.persist(mtLog);
	}
	
	public static void main(String [] args) {
//		String pattern = "^\\s*SHELL\\s+[A-Z]?[0-9]{7}[A-Z]?\\s+([1-3]\\-[0-9]{6}|[0-9]{5}|1\\-[0-9]{5})$";
		
		
		String message = "Tshell 111 54321 14 N";
		System.out.println(message.toUpperCase().matches(PATTERN));
		message = message.toUpperCase().replaceAll(PATTERN_KEYWORD, "").trim();
		System.out.println(message);
	}
}
