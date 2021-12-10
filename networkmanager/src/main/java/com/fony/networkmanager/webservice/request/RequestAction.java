package com.fony.networkmanager.webservice.request;
import android.os.Bundle;
import com.fony.networkmanager.webservice.request.BaseRequest.RequestType;
import com.fony.networkmanager.webservice.request.model.PageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * fony
 * webservice 请求参数设置
 */
public class RequestAction {
	public RequestType requestType = RequestType.WEBSERVICE;

	public String serviceName;
	/** 是否启用分页(传递分页参数) */
	public boolean isPageBeanEnable = false;
	public PageBean pageBean;
	public Bundle queryBundle;
	public List<String> queryKeys;

	public RequestAction() {
		pageBean = new PageBean();
		queryBundle = new Bundle();
		queryKeys = new ArrayList<String>();
	}

	public RequestAction reset() {
		queryKeys.clear();
		queryBundle.clear();
		pageBean.reset();
		return this;
	}

	public void putParam(String key, String value) {
		if (queryBundle.containsKey(key)) {
			queryBundle.putString(key, value);
		} else {
			queryKeys.add(key);
			queryBundle.putString(key, value);
		}
	}
	
	@Override
	public String toString() {
		return "RequestAction [requestType=" + requestType + ", serviceName="
				+ serviceName + ", pageBean=" + pageBean + ", queryBundle="
				+ queryBundle + ", queryKeys=" + queryKeys + "]";
	}
}
