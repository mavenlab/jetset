package com.mavenlab.jetset.controller;

import java.util.Random;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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

	/**
	 * create prize
	 * 
	 * @param name
	 * @param smsMessage
	 * @param webMessage
	 * @param quantity
	 * @return
	 */
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
}