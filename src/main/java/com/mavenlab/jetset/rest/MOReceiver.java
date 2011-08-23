package com.mavenlab.jetset.rest;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import com.mavenlab.jetset.model.Entry;
import com.mavenlab.jetset.model.MTLog;
import com.mavenlab.jetset.model.MOLog;

@Path("/mo")
@Stateless
public class MOReceiver {

	public final static SimpleDateFormat SDF_MO_TIMESTAMP = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	
	@Inject
	@Category("jetset.MOReceiver")
	private Logger log;
	
	@Inject
	private EntityManager em;
	
	@QueryParam("moId") 
	private String moId;
	
	@QueryParam("msisdn") 
	private String msisdn; 
	
	@QueryParam("message") 
	private String message;
	
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
	public String receive(String id, String msisdn, String message) {
		try {
			MOReceiver.lock.tryLock(900, TimeUnit.SECONDS);
			
			log.info("Message: " + message);
			log.info("MSISDN: " + msisdn);
			log.info("moId: " + moId);
			
			MOLog moLog = new MOLog();
			moLog.setMoId(moId);
			moLog.setMessage(message);
			moLog.setMsisdn(msisdn);
			moLog.setDateReceived(SDF_MO_TIMESTAMP.parse(timestamp));
			em.persist(moLog);
			
//			MTLog mtLog = new MTLog();
//			mtLog.setDestination(msisdn);
//			mtLog.setOriginator(destination);
//			mtLog.setOutrouteId(outrouteID);
//			mtLog.setKeyword(keyword);
//			mtLog.setMoLog(moLog);
//			mtLog.setMessage("");
//			
//			Entry entry = new Entry();
//			entry.setEntry(message);
//			entry.setKeyword(keyword);
//			entry.setMoLog(moLog);
//			HashMap<String, String> mapEntryFields = this.parseMessage(entry, keyword, mtLog);
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
	
//	public HashMap<String,String> parseMessage(Entry entry,Keyword keyword, MTLog mtLog) {
//		String[] messages = message.split("[\\s]+");
//		int lastIndexMessages = messages.length-1;
//		int beginIndexMessages = 1;
//		
//		List<KeywordField> keywordFields = keywordFieldsListing(keyword);
//		HashMap<String, String> mapEntryFields = new HashMap<String, String>();
//		
//		String keyword2 = messages[0];
//		
//		if(!message.toUpperCase().matches(keyword.getPattern())) {
//			
//			entry.getStatus().setStatus("invalid");
//			
//			mapEntryFields.put("keyword", keyword2.trim());
//			mapEntryFields.put("name", "");
//			mapEntryFields.put("nric", "");
//			mapEntryFields.put("receipt", "");
//			
//			return mapEntryFields;
//		}		
//		
//		int i=beginIndexMessages;
//		int l=lastIndexMessages;
//		
//		String receipt = "";
//		receipt = messages[l];
//		l--;
//		if(receipt.equals("")){
//			entry.getStatus().setStatus("invalid");
//		}
//		
//		String nric = "";
//		if(!messages[l].toUpperCase().matches(keywordFields.get(1).getPattern())) {
//			nric = "";
//			entry.getStatus().setStatus("invalid");
//		}else{
//			nric = messages[l];
//			l--;
//		}
//		
//		String name = "";
//		while(i<=l) {
//			name = name + " " + messages[i];
//			i++;
//		}
//		if(name.equals("")) {
//			entry.getStatus().setStatus("invalid");
//		}
//		
//		mapEntryFields.put("keyword", keyword2.trim());
//		mapEntryFields.put("name", name.trim());
//		mapEntryFields.put("nric", nric.trim());
//		mapEntryFields.put("receipt", receipt.trim());
//		
//		return mapEntryFields;
//	
//	}
}
