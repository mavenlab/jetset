package com.mavenlab.jetset.controller;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

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
	
	public Station getStationById(int id) {
		try {
			return (Station) em.createNamedQuery("jetset.query.Station.findById").setParameter("id", id).getSingleResult();
		} catch(NoResultException e) {
		}
		
		return null;
	}
}