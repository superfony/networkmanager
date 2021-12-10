package com.fony.networkmanager.webservice.xml.handler;

import org.xml.sax.helpers.DefaultHandler;

/**
 * 继承Android SDK 自带的解析器 SAX 解析方式
 * fony
 */
public abstract class BaseParserHandler extends DefaultHandler {
	public abstract Object getParseContent();

}
