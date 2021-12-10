package com.fony.networkmanager.webservice.xml.parser.demo;

import com.fony.networkmanager.webservice.xml.handler.BaseParserHandler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class DemoParserHandler extends BaseParserHandler {
    private StringBuilder builder;
    private DemoResponse loginResponse;

    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String name)
            throws SAXException {
        super.endElement(uri, localName, name);
        if ("userid".equalsIgnoreCase(localName)) {
            loginResponse.userBean.userid = trim(builder.toString());
        } else if ("chname".equalsIgnoreCase(localName)) {
            loginResponse.userBean.chname = trim(builder.toString());
        } else if ("ename".equalsIgnoreCase(localName)) {
            loginResponse.userBean.ename = trim(builder.toString());
        } else if ("rosecode".equalsIgnoreCase(localName)) {
            loginResponse.userBean.rosecode = trim(builder.toString());
        } else if ("rosename".equalsIgnoreCase(localName)) {
            loginResponse.userBean.rosename = trim(builder.toString());
        } else if ("result".equalsIgnoreCase(localName)) {
            loginResponse.result = trim(builder.toString());
        } else if ("message".equalsIgnoreCase(localName)) {
            loginResponse.message = trim(builder.toString());
        }
        builder.setLength(0);
    }

    public String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        builder = new StringBuilder();
        loginResponse = new DemoResponse();
    }

    @Override
    public void startElement(String uri, String localName, String name,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, name, attributes);
        if ("userinfo".equalsIgnoreCase(localName)) {
            loginResponse.userBean = new UserBean();
        } else if ("recordInfo".equalsIgnoreCase(localName)) {

        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    private String trim(String strToTrim) {
        if (null == strToTrim) {
            return null;
        }
        return strToTrim.trim();
    }

    @Override
    public Object getParseContent() {
        return loginResponse;
    }
}
