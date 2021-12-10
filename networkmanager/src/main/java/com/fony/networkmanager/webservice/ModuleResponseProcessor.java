package com.fony.networkmanager.webservice;

public interface ModuleResponseProcessor {
	/**
	 * 解析完成后的数据处理
	 * @param webServiceBaseHelp
	 * @param parseObj
     * 解析完成后的对象
	 */
	void processResponse(WebServiceBaseHelp webServiceBaseHelp, Object parseObj);
}
