package com.mavenlab.jetset.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import com.mavenlab.jetset.model.Entry;
import com.mavenlab.jetset.model.SMSEntry;
import com.mavenlab.jetset.model.Station;
import com.mavenlab.jetset.model.WebEntry;

@Path("/web")
@Stateless
public class WebReceiver {
	
	public final static SimpleDateFormat SDF_MO_TIMESTAMP = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

	@Inject
	@Category("jetset.WebReceiver")
	private Logger log;
	
	@Inject
	private EntityManager em;
	public static Lock lock = new ReentrantLock();
	private String timestamp;
	private final static String channel = "WEB";
	
	public String receive(@QueryParam("name") String name, 
			@QueryParam("nric") String nric, 
			@QueryParam("number") String number,
			@QueryParam("email") String email,
			@QueryParam("payment") String payment,
			@QueryParam("cc") String cc,
			@QueryParam("station") Station station,
			@QueryParam("receipt") String receipt,
			@QueryParam("grade") String grade)	{	
		try {
			WebReceiver.lock.tryLock(900, TimeUnit.SECONDS);
		
			timestamp = SDF_MO_TIMESTAMP.format(new Date());
			
		
			WebEntry webEntry = new WebEntry();
			webEntry.setName(name);
			webEntry.setNric(nric);
			webEntry.setMsisdn(number);
			webEntry.setEmail(email);
			webEntry.setPaymentMode(payment);
			webEntry.setCcType(cc);
			webEntry.setStation(station);
			webEntry.setReceipt(receipt);
			webEntry.setFuelGrade(grade);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}
}

