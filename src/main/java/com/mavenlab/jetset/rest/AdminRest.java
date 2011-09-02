package com.mavenlab.jetset.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.mavenlab.jetset.controller.PrizeController;

@Path("/admin")
@Stateless
public class AdminRest {

	@Inject
	private PrizeController prizeController;

	@Path("/init")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String init() {
		prizeController.createPrize("Sunkist", 
				"Thank you for participating. Purchase a 250ml Sunkist no sugar added juice at $1 at any 7E@Shell by 31/10/11 with this SMS and Shell Escape Card. T&C apply.", 
				"Thank you for participating. Print out this page and purchase a 250ml bottle of Sunkist no sugar added orange juice at only $1 at any 7Eleven@Shell by 31/10/11 with your Shell Escape Card. T&Cs apply.",
				30000);
	
		prizeController.createPrize("Face Spa",
				"Thank you for participating. Enjoy a complimentary Hydrating Face Spa at Body Contours by 30/10/11 with this SMS and Shell Escape Card. T&C apply. Call 68411141",
				"Thank you for participating. Print out this page and redeem for a complimentary Hydrating Face Spa (60mins/$180) at Body Contours by 30/10/11 with your Shell Escape Card. T&C apply. Call 6841 1141 to book.",
				40000);
	
		prizeController.createPrize("Donut",
				"Thank you for participating. Purchase 8 donuts at Donut Factory and get 4 more FREE at $12.50 by 31/10/11 with this SMS and Shell Escape Card. T&C apply.",
				"Thank you for participating. Print out this page and purchase 8 donuts at Donut Factory and get 4 more FREE at $12.50 by 31/10/11 with your Shell Escape Card. T&C apply.",
				40000);
		return "ok";
	}

}
