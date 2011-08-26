package com.mavenlab.jetset.controller;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import com.mavenlab.jetset.model.Prize;

@Stateless
public class PrizeController {

	@Inject
	@Category("jetset.PrizeController")
	private Logger log;
	
	@Inject
	private EntityManager em;

	public Prize createPrize(String name, String smsMessage, String webMessage, int quantity) {
		log.info("CREATE PRIZE: " + name + ", " + smsMessage + ", " + webMessage + ", " + quantity);
		Prize prize = new Prize();
		prize.setName(name);
		prize.setSmsMessage(smsMessage);
		prize.setWebMessage(webMessage);
		prize.setQuantity(quantity);
		
		em.persist(prize);
		return prize;
	}
}