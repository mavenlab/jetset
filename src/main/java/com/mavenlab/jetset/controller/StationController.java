package com.mavenlab.jetset.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.mavenlab.jetset.model.Station;

@Stateless
@Named
public class StationController {

	@Inject
	private EntityManager em;

	@SuppressWarnings("unchecked")
	public List<Station> getStations() {
		return em.createNamedQuery("jetset.query.Station.findActive").getResultList();
	}
}