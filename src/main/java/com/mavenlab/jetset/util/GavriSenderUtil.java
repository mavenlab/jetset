package com.mavenlab.jetset.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Element;

/**
 * @author irsan
 * @version 1
 * 
 * Class to send mobile message thru Gavri
 */
public class GavriSenderUtil {
	
	public final static String TEST_RESPONSE_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><MTLog count=\"1\" id=\"9000573\"><status id=\"501\">sending success</status></MTLog>";
	
	public final static String HOST = "http://gavri.mavenlab.com/Gavri/GavriSender";
	
	private final static org.apache.commons.logging.Log log = LogFactory.getLog(GavriSenderUtil.class);
	
	/**
	 * send text message (SMS) through gavri
	 * 
	 * @param url
	 * @param username
	 * @param password
	 * @param outrouteId
	 * @param to
	 * @param from
	 * @param message
	 * @param moid
	 * @param tpoaType
	 * @return
	 */
	public static Map<String, Object> sendTextMessage(String url, 
			String username, String password, int outrouteId, 
			String to, String from, String message, int moid, String tpoaType) {
		
		if(outrouteId < 0) {
			return parseGavriResponse(new ByteArrayInputStream(TEST_RESPONSE_XML.getBytes()));
		}
		
		if(tpoaType == null) {
			tpoaType = "ALPHA NUMERIC";
		}
		
		url = url == null ? HOST : url;
		
		HttpClient client = new HttpClient();
		
		NameValuePair [] nvps = new NameValuePair[6];
		
		nvps[0] = new NameValuePair("id", Integer.toString(outrouteId));
		nvps[1] = new NameValuePair("to", to);
		nvps[2] = new NameValuePair("from", from);
		nvps[3] = new NameValuePair("message", message);
		nvps[4] = new NameValuePair("moid", Integer.toString(moid));
		nvps[5] = new NameValuePair("tpoaType", tpoaType);
		
		HttpMethod method = new GetMethod(url);
		method.setQueryString(nvps);
		
		client.getState().setCredentials(
				AuthScope.ANY,
				new UsernamePasswordCredentials(username, password));
		
		method.setDoAuthentication(true);
		
		Map<String, Object> resp = new HashMap<String, Object>();

		/*
		 * default values
		 */
		resp.put("id", -1);
		resp.put("count", 0);
		
		try {
			int status = client.executeMethod(method);

			if(status == HttpStatus.SC_OK) {
				resp = parseGavriResponse(method.getResponseBodyAsStream());
			}
		} catch (Exception e) {
			log.error("EXECUTE CONNECTION EXCEPTION: " + e.getMessage());
			resp.put("statusId", 603);
			resp.put("statusDesc", e.getMessage());
		}

		return resp;
	}
	
	/**
	 * utitily method to parse xml response from gavri
	 * 
	 * @param inputStream
	 * @return
	 */
	private static Map<String, Object> parseGavriResponse(InputStream inputStream) {
		HashMap<String, Object> resp = new HashMap<String, Object>();

		/*
		 * default values
		 */
		resp.put("id", -1);
		resp.put("count", 0);
		resp.put("statusId", "-501");
		resp.put("statusDesc", "mock successfule");
		
		try {
			Element root = (Element) DOMUtil.inputStreamToDOM(inputStream).getElementsByTagName("MTLog").item(0);
			
			String mtLogID = root.getAttribute("id");
			String count = root.getAttribute("count");
			
			Element statusNode = (Element) root.getElementsByTagName("status").item(0);
			String id = statusNode.getAttribute("id");
			String statusDesc = statusNode.getTextContent();

			resp.put("id", mtLogID);
			try {
				resp.put("count", new Integer(count));
			} catch(NumberFormatException e) {
				resp.put("count", 0);
			}
			resp.put("statusId", id);
			resp.put("statusDesc", statusDesc);
		} catch (Exception e) {
			log.error("PARSE RESPONSE EXCEPTION: " + e.getMessage());
			resp.put("statusId", 603);
			resp.put("statusDesc", e.getMessage());
		}
		
		return resp;
	}
}