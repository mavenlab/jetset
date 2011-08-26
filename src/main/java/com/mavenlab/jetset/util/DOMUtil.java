package com.mavenlab.jetset.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class DOMUtil {

	public static Document inputStreamToDOM(InputStream xmlStream) throws SAXException, IOException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(xmlStream);
		return document;
	}
	
    public static Document buildDocument() throws ParserConfigurationException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		return db.newDocument();
    }
    
    public static void transformToOutputStream(Element root, OutputStream out) throws TransformerException {
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
    	Transformer transformer = transformerFactory.newTransformer();
    	transformer.transform(new DOMSource(root), new StreamResult(out));
    }
    
    public static String transformToXMLString(Element root) throws TransformerException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	transformToOutputStream(root, baos);
    	return baos.toString();
    }
}
