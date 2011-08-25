package com.mavenlab.jetset.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.mavenlab.jetset.model.Entry;
import com.mavenlab.jetset.model.MTLog;
import com.mavenlab.jetset.model.MOLog;
import com.mavenlab.jetset.model.Station;

@Path("/mo")
@Stateless
public class MOReceiver {

	public final static SimpleDateFormat SDF_MO_TIMESTAMP = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	@Inject
	@Category("jetset.MOReceiver")
	private Logger log;
	
	@Inject
	private EntityManager em;
	
	public static Lock lock = new ReentrantLock();
	private String timestamp;
	private String keywordChecked = "SHELL";
	
	private final static int outrouteID = -1;
	private final static String URL = "http://gavri.mavenlab.com/Gavri/GavriSender";
	private final static String username = "mvnprojects2";
	private final static String password = "92279545ff";
	private final static String destination = "Shell";
	
	public static AtomicInteger countPrize;
	
	@GET
	@Produces("text/plain")
	public String receive(@QueryParam("moId") String moId, 
			@QueryParam("msisdn") String msisdn, @QueryParam("message") String message) {
		
		timestamp = SDF_MO_TIMESTAMP.format(new Date());
		
		try {
			MOReceiver.lock.tryLock(900, TimeUnit.SECONDS);
			timestamp = SDF_MO_TIMESTAMP.format(new Date());
			log.info("Message: " + message);
			log.info("MSISDN: " + msisdn);
			log.info("moId: " + moId);
			log.info("timestamp: " + timestamp);
			
			MOLog moLog = new MOLog();
			moLog.setMoId(moId);
			moLog.setMessage(message);
			moLog.setMsisdn(msisdn);
			moLog.setDateReceived(SDF_MO_TIMESTAMP.parse(timestamp));
			em.persist(moLog);
			
			MTLog mtLog = new MTLog();
			mtLog.setDestination(msisdn);
			mtLog.setOriginator(destination);
			mtLog.setOutrouteId(outrouteID);
			mtLog.setMoLog(moLog);
			mtLog.setMessage("GRATZ");
			em.persist(mtLog);
			log.info("MT PERSIST XXXXXXXXXXXXX");
//			
			Entry entry = new Entry();
			entry.setMsisdn(msisdn);
			entry.setMoLog(moLog);
			entry.setMtLog(mtLog);
			parseMessage(message, entry);
			
			
			em.persist(entry);
//			
////			if(entry.getStatus().getStatus().equals("active") && this.checkDuplicate(mapEntryFields, keyword, mtLog))
////				entry.getStatus().setStatus("duplicate");
////			else
////				mtLog.setMessage(keyword.getValid().getMessage());
//			
//			if(entry.getStatus().getStatus().equals("active"))
//				mtLog.setMessage(keyword.getValid().getMessage());
//			else 
//				mtLog.setMessage(keyword.getInvalid().getMessage());
//			
//			replyMessage(mtLog);
//			em.persist(mtLog);
//			entry.setMtLog(mtLog);
//
//			em.persist(entry);
//			
//			this.insertEntryField(mapEntryFields, entry);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		MOReceiver.lock.unlock();
		return "OK";
	}
	
	public Entry parseMessage(String message, Entry entry) {
		String[] messages = message.split("[\\s]+");
		
		int lastIndexMessages = messages.length-1;
		int beginIndexMessages = 1;
		
		String name;
		String nric;
		String receipt;
		Station station = null;
		boolean member;
		int chance = 0;
		String timeNow = SDF_MO_TIMESTAMP.format(new Date());
		Date duplicate;
		
		log.info("TIME NOW " + timeNow);
		
		String keyword2 = messages[0];
		entry.setStatus("active");
		
		if(!keyword2.toUpperCase().matches("SHELL")) {
			
			entry.setStatus("invalid");
			
			return entry;
		}		
		
		int i=beginIndexMessages;
		int l=lastIndexMessages;
		
		String patternNRIC = "[A-Z]?[0-9]{7}[A-Z]?";
		String patternReceipt = "([123][-\\s])?[0-9]{1,7}";
		
		log.info("MEMBER XXXXXXXXXX " + messages[l]);
		member = false;
		if (messages[l].toUpperCase().equals("Y")) 
			member = true;
		else if (messages[l].toUpperCase().equals("N")) 
			member = false;
		l--;
		
		if(member==true)
			chance = 2;
		else
			chance = 1;
		
		log.info("STATION XXXXXXXXXX " + messages[l]);
		try {
			int stationId = Integer.parseInt(messages[l]);
			station = (Station) em.createNamedQuery("jetset.query.Station.findById")
					.setParameter("id", stationId)
					.getSingleResult();
			log.info("STATION QUERY: " + station.getId() + " ---- " + station.getName());
		} catch(NumberFormatException e) {
			log.info("Invalid Station Number " + e.getMessage());
			entry.setStatus("invalidstation1");
		} catch(NoResultException e) {
			log.info("Station Number cannot be found " + e.getMessage());
			entry.setStatus("invalidstation2");
			e.printStackTrace();
		}

		l--;
		
		name = "";
		while(!messages[i].toUpperCase().matches(patternNRIC)) {
			name = name + " " + messages[i];
			i++;
		}
		if(name.equals("")) {
			entry.setStatus("invalidname");
		}
		
		nric = "";
		if(!messages[i].toUpperCase().matches(patternNRIC)) {
			nric = "";
			entry.setStatus("invalidnric");
		}else{
			nric = messages[i];
			i++;
		}

		receipt = "";
		log.info("RECEIPT XXXXXXXXXX " + messages[i]);
		String[] receipt2;
		// started to check if the receipt only contain 000 or not
		if(!messages[i].matches(patternReceipt)) // check if invalid
			entry.setStatus("invalidRRRR1");
		else {
		
			if(i==l) { //check if the digit is x-xxxx
				receipt = messages[i];
				receipt2 = receipt.split("");
				
				if(receipt2[2].matches("-") || receipt2[2].matches(" ")) {
					for(int x=3;x<=receipt2.length;x++) {
						log.info("X, receipt2  length " + receipt2[x] + "," +receipt2.length);
						if(Integer.parseInt(receipt2[x]) > 0){
							log.info("MASUK IF");
							entry.setStatus("active");
							break;
							
						}else{
							entry.setStatus("invalidRRRR2");
							log.info("MASUK ELSE");
						}
					}				
				} else {
					for(int x=1;x<=receipt2.length;x++) {
						if(Integer.parseInt(receipt2[x]) > 0)
							break;
						else
							entry.setStatus("invalidRRRRR3");
					}				
				}
			} else {
				String temp = messages[i] + " " + messages[l];
				receipt = temp;
				receipt2 = receipt.split("");
				
				if(receipt2[2].matches("-") || receipt2[2].matches(" ")) {
					for(int x=3;x<=receipt2.length;x++) {
						if(Integer.parseInt(receipt2[x]) > 0)
							break;
						else
							entry.setStatus("invalidRRRRR4");
					}				
				} else {
					for(int x=1;x<=receipt2.length;x++) {
						if(Integer.parseInt(receipt2[x]) > 0)
							break;
						else
							entry.setStatus("invalidRRRRR5");
					}				
				}
			}
		}
		
		if(!entry.getStatus().matches("invalid")){
			try{
				duplicate = (Date) em.createNamedQuery("jetset.query.Entry.findId")
						.setParameter("id", station)
						.getSingleResult();
				log.info(duplicate);
				if(duplicate != null){
					if(timeNow.equals(duplicate.toString())){
						entry.setStatus("duplicate");
					}
					
				}
			} catch(NumberFormatException e) {
				log.info("Invalid date " + e.getMessage());
			} catch(NoResultException e) {
				log.info("No result " + e.getMessage());
			}
			
		}
		
		entry.setName(name);
		entry.setNric(nric);
		entry.setReceipt(receipt);
		entry.setStation(station);
		entry.setUobMember(member);
		entry.setChance(chance);
		
		
		
		return entry;
	
	}
}
