package com.fony.networkmanager.webservice.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


/**
 * HTTP Response
 */
public class Response {
	private int statusCode;
	private Document responseAsDocument = null;
	private String responseAsString = null;
	private InputStream is;

	private boolean streamConsumed = false;

	private static ThreadLocal<DocumentBuilder> builders = new ThreadLocal<DocumentBuilder>() {

		@Override
		protected DocumentBuilder initialValue() {
			try {
				return DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
			} catch (ParserConfigurationException ex) {
				throw new ExceptionInInitializerError(ex);
			}
		}
	};

	public Response() {

	}



	Response(String content) {
		this.responseAsString = content;
	}

	public int getStatusCode() {
		return statusCode;
	}



	/**
	 * 以InputStream形式返回Response.<br>
	 * 不可以在asString() or asDcoument()之后调用<br>
	 */
	public InputStream asStream() {
		if (streamConsumed) {
			throw new IllegalStateException("Stream has already been consumed.");
		}
		return is;
	}

	/**
	 * 以String形式返回Response
	 */
	public String asString() throws Exception {
		if (null == responseAsString) {
			BufferedReader br;
			try {
				InputStream stream = asStream();
				if (null == stream) {
					return null;
				}
				br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
				StringBuffer buf = new StringBuffer();
				String line;
				while (null != (line = br.readLine())) {
					buf.append(line).append("\n");
				}
				this.responseAsString = buf.toString();
				if (Configuration.isDalvik()) {
					this.responseAsString = unescape(responseAsString);
				}
				stream.close();
				streamConsumed = true;
			} catch (NullPointerException npe) {
				throw new Exception(npe.getMessage(), npe);
			} catch (IOException ioe) {
				throw new Exception(ioe.getMessage(), ioe);
			}
		}
		return responseAsString;
	}

	/**
	 * 以Document形式返回Response
	 *
	 * @return response body as Document
	 * @throws
	 */
	public Document asDocument() throws Exception {
		if (null == responseAsDocument) {
			try {
				this.responseAsDocument = builders.get().parse(
						new ByteArrayInputStream(asString().getBytes("UTF-8")));
			} catch (SAXException saxe) {
				throw new Exception(
						"The response body was not well-formed:\n"
								+ responseAsString, saxe);
			} catch (IOException ioe) {
				throw new Exception(
						"There's something with the connection.", ioe);
			}
		}
		return responseAsDocument;
	}

	/**
	 * 以JSONObject形式返回Response
	 *
	 * @return response body as JSONObject
	 * @throws
	 */
	public JSONObject asJSONObject() throws Exception {
		try {
			return new JSONObject(asString());
		} catch (JSONException jsone) {
			throw new Exception(jsone.getMessage() + ":"
					+ this.responseAsString, jsone);
		}
	}

	/**
	 * 以JSONArray形式返回Response
	 *
	 * @return response body as JSONArray
	 * @throws Exception
	 */
	public JSONArray asJSONArray() throws Exception {
		try {
			return new JSONArray(asString());
		} catch (Exception jsone) {
			throw new Exception(jsone.getMessage() + ":"
					+ this.responseAsString, jsone);
		}
	}

	public InputStreamReader asReader() {
		try {
			return new InputStreamReader(is, "UTF-8");
		} catch (java.io.UnsupportedEncodingException uee) {
			return new InputStreamReader(is);
		}
	}

	private static Pattern escaped = Pattern.compile("&#([0-9]{3,5});");

	/**
	 * 
	 * @param original
	 *            The string to be unescaped.
	 * @return The unescaped string
	 */
	public static String unescape(String original) {
		Matcher mm = escaped.matcher(original);
		StringBuffer unescaped = new StringBuffer();
		while (mm.find()) {
			mm.appendReplacement(unescaped, Character.toString((char) Integer
					.parseInt(mm.group(1), 10)));
		}
		mm.appendTail(unescaped);
		return unescaped.toString();
	}

	@Override
	public String toString() {
		if (null != responseAsString) {
			return responseAsString;
		}
		return "Response{" + "statusCode=" + statusCode + ", response="
				+ responseAsDocument + ", responseString='" + responseAsString
				+ '\'' + ", is=" + is + ", httpResponse="  + '}';
	}

	public String getResponseAsString() {
		return responseAsString;
	}

	public void setResponseAsString(String responseAsString) {
		this.responseAsString = responseAsString;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
