package com.mavenlab.jetset.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pagination implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -816375536529799166L;

	protected int limit = 5;
	protected int offset = 0;
	protected long total = 0;

	protected List<PaginationListener> listeners;
	
	/**
	 * default pagination
	 */
	public Pagination() {
		listeners = new ArrayList<PaginationListener>();
	}
	
	public Pagination(int limit) {
		this.limit = limit;
		listeners = new ArrayList<PaginationListener>();
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public long getTotal() {
		return total;
	}
	
	public void setTotal(long total) {
		this.total = total;
		if(offset > total) {
			offset = 0;
		}
	}

	public void next() {
		if(isNext()) {
			offset += limit;
		}
		
		for(PaginationListener listener: listeners) {
			listener.onNext();
		}
	}

	public void prev() {
		if(isPrev()) {
			offset -= limit;
		} else {
			offset = 0;
		}
		
		for(PaginationListener listener: listeners) {
			listener.onPrev();
		}
	}

	public boolean isNext() {
		int nextOffset = offset + limit;
		return nextOffset < total;
	}

	public boolean isPrev() {
		int prevOffset = offset - limit;
		return prevOffset >= 0;
	}

	public List<Integer> getPageNumbers() {
		int totalPage = (int) (total / limit) + (total % limit > 0 ? 1 : 0);
		
		List<Integer> pages = new ArrayList<Integer>();
		
		for(int i = 0; i < totalPage; i++) {
			pages.add(i + 1);
		}
		
		return pages;
	}

	public int getCurrentPageNumber() {
		return ((int) offset / limit) + 1;
	}
	
	public int getLastPageNumber() {
		List<Integer> pages = getPageNumbers();
		if(pages.size() > 0) {
			return pages.get(pages.size() - 1);
		}
		return 0;
	}

	public void setPage(int page) {
		int theOffset = (page - 1) * limit;
		
		if(theOffset < 0) {
			offset = 0;
		} else if(offset > total) {
			offset = (int) (total - limit);
		} else {
			offset = theOffset;
		}
		
		for(PaginationListener listener: listeners) {
			listener.onPageSet(page);
		}
	}

	public void addPaginationListener(PaginationListener listener) {
		listeners.add(listener);
	}
}