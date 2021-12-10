package com.fony.networkmanager.webservice.request;
import java.io.Serializable;
import java.util.Map;

/**
 * @author fony
 * webservice 请求参数线程类
 * 2021年03月24日22:44:15
 */
public class BaseRequest implements Runnable, Serializable {
	private static final long serialVersionUID = -3995079047509316570L;
	protected int connectTimeout = 20000;
	protected int readTimeout = 20000;

	protected String uriStr = null;
	protected RequestCallback requestCallback = null;
	protected RequestType requestType = RequestType.WEBSERVICE;
	protected String serviceNameSpace = null;
	protected String serviceUrl = null;

	public void setSoapAction(String soapAction) {
		this.soapAction = soapAction;
	}

	protected  String soapAction;

	public enum RequestType {
		TXT, WEBSERVICE
	}

	public void run() {
	}
	protected String buildAndExecuteWebServiceRequest() throws Exception {
		return null;
	}

	protected Map<String, String> buildWebServiceRequest() {
		return null;
	}


	protected void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	protected void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}



	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setServiceNameSpace(String serviceNameSpace) {
		this.serviceNameSpace = serviceNameSpace;
	}

}
