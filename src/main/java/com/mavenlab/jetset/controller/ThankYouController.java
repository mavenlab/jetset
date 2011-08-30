package com.mavenlab.jetset.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.solder.logging.Category;

import com.mavenlab.jetset.model.Entry;

@ManagedBean
@RequestScoped
public class ThankYouController {

	@Inject
	@Category("jetset.ThankYouController")
	private Logger log;
	
	@Inject
	private EntryController entryController;

	private int id;
	
	public Entry getEntry() {
		log.info("GET ENTRY: " + id);
		Entry entry = entryController.getEntryFetchAll(id);
		return entry;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}