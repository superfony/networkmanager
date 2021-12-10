package com.fony.networkmanager.webservice.xml.parser.dom;

import com.fony.networkmanager.webservice.model.StatusEntity;
import com.fony.networkmanager.webservice.xml.parser.DefaultXmlParser;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DefaultDomParser extends DefaultXmlParser {
	protected StatusEntity response;
	protected Document document;
	protected DocumentBuilder builder;

	public DefaultDomParser() {
		// 创建解析器
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	private void init() {
		try {
			document = builder.parse(new InputSource(new ByteArrayInputStream(
					getXml().getBytes("utf-8"))));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (SAXException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void parse() {
		init();
	}

	@Override
	public Object getContent() {
		return response;
	}

}
