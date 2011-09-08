/**
 * Copyright (c) 2011 Mavenlab Private Limited. All rights reserved.
 */
package com.mavenlab.jetset.web;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mavenlab.jetset.controller.EntryController;

/**
 * Class : DownloadDatabase.java
 * 
 * @author <a href="mailto:yogie.kurniawan@mavenlab.com">Yogie Kurniawan</a>
 */
public class DownloadEntriesServlet extends HttpServlet {

	private static final long serialVersionUID = -392144800083179531L;

	@Inject
	private EntryController entryController;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			if (request.getUserPrincipal() == null) {
				throw new RuntimeException("Forbidden to access!");
			}

			String msisdn = request.getParameter("msisdn");
			String start = request.getParameter("start");
			String end = request.getParameter("end");
			String type = request.getParameter("type");
		    
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition",
					"attachment; filename=entries.zip");

			byte data[] = entryController.downloadEntries(type, msisdn, start, end);
			OutputStream out = response.getOutputStream();
			ZipOutputStream zip = new ZipOutputStream(out);

			zip.putNextEntry(new ZipEntry("entries.csv"));
			zip.write(data);
			zip.closeEntry();

			zip.flush();
			zip.close();
			out.flush();

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest
	 * , javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}

}
