package com.mavenlab.jetset.util;

/**
 * @author irsan
 *
 */
public interface PaginationListener {

	public void onPrev();
	public void onPageSet(int page);
	public void onNext();
}