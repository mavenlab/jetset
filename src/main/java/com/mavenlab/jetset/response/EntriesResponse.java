package com.mavenlab.jetset.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mavenlab.jetset.model.Entry;
import com.mavenlab.jetset.model.SMSEntry;
import com.mavenlab.jetset.util.Pagination;

public class EntriesResponse extends Response {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2825065901774563792L;

	private List<Map<String, Object>> entries;
	private Pagination pagination;
	
	public EntriesResponse() {
		super();
		entries = new ArrayList<Map<String, Object>>();
		pagination = new Pagination(50);
	}
	
	public void addEntry(Entry entry) {
		Map<String, Object> entryMap = new HashMap<String, Object>();
		entryMap.put("entry", entry);
		entryMap.put("type", entry instanceof SMSEntry ? "SMS" : "WEB");
		entries.add(entryMap);
	}

	/**
	 * @return the entries
	 */
	public List<Map<String, Object>> getEntries() {
		return entries;
	}

	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<Map<String, Object>> entries) {
		this.entries = entries;
	}

	/**
	 * @return the pagination
	 */
	public Pagination getPagination() {
		return pagination;
	}

	/**
	 * @param pagination the pagination to set
	 */
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
}