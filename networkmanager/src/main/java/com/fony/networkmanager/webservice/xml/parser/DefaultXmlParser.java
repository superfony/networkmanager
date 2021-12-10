package com.fony.networkmanager.webservice.xml.parser;

import org.xml.sax.InputSource;

/**
 * 
 * @version 1.0
 * 
 */
public abstract class DefaultXmlParser implements BaseXmlParser {
	// 解析完成后数据放入content中
	private Object content;
	// 待解析的xml数据
	private String xml;
	private InputSource inputSource;
	public DefaultXmlParser() {
	}

	public DefaultXmlParser(String xml) {
		this.xml = xml;
	}
	/**
	 *
	 */
	public Object getContent() {
		return this.content;
	}
	/**
	 *
	 */
	public void setContent(Object content) {
		this.content = content;
	}

	public String getXml() {
		return xml;
	}

	@Override
	public void setXml(String xml) {
		this.xml = xml;
	}

	public InputSource getInputSource() {
		return inputSource;
	}

	public void setInputSource(InputSource inputSource) {
		this.inputSource = inputSource;
	}

}
