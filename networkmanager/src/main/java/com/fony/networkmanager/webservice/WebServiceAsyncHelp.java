package com.fony.networkmanager.webservice;
import com.fony.networkmanager.config.Const;

/**
 * fony
 * 2021年12月03日09:55:48
 * 异步调用的webservice 的实现
 */
public class WebServiceAsyncHelp {
    public static WebServiceAsyncHelp webServiceAsyncHelp = new WebServiceAsyncHelp();
    public static WebServiceAsyncHelp getInstance() {
        return webServiceAsyncHelp;
    }
    public  WebServiceBaseHelp getWebServiceBaseHelp(){
        WebServiceBaseHelp webServiceBaseHelp=new WebServiceBaseHelp();
        webServiceBaseHelp.setSoapAction(Const.SoapAction);
        webServiceBaseHelp.setServiceUrl(Const.ServerUrl);
        webServiceBaseHelp.setServiceNameSpace(Const.serviceNameSpace);
        return  webServiceBaseHelp;
    }
}
