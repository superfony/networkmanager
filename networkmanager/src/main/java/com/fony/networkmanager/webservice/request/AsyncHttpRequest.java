package com.fony.networkmanager.webservice.request;

import android.os.Bundle;
import android.text.TextUtils;


import com.fony.networkmanager.webservice.model.Response;
import com.fony.networkmanager.webservice.request.model.RequestParameter;
import com.fony.networkmanager.webservice.xml.handler.BaseParserHandler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <E>
 * @author fony
 * @Title 异步Http请求
 * @Descriptoin 线程的终止工作交给线程池, 当activity停止的时候, 设置回调函数为false ,就不会执行回调方法
 */
public class AsyncHttpRequest<E> extends BaseRequest {
    private static final long serialVersionUID = -3509046726762405265L;
    protected List<RequestParameter> parameters = null;
    private RequestAction requestAction;
    private Map<String, String> requestHeaders = new HashMap<String, String>();
    private int timeout = 20000;// 20s

    public void setBaseHandler(BaseParserHandler baseHandler) {
        this.baseHandler = baseHandler;
    }

    private BaseParserHandler baseHandler;

    /**
     * @param url
     * @param parameters
     * @param requestCallback
     */
    public AsyncHttpRequest(String url, List<RequestParameter> parameters,
                            RequestCallback requestCallback) {
        this.uriStr = url;
        this.parameters = parameters;
        this.requestCallback = requestCallback;

    }

    /**
     * @param requestAction   请求参数
     * @param requestCallback
     */
    public AsyncHttpRequest(RequestAction requestAction,
                            RequestCallback requestCallback) {
        this.requestAction = requestAction;
        this.requestCallback = requestCallback;
    }

    public void run() {
        try {
            if (requestType == RequestType.WEBSERVICE) {
                String result = buildAndExecuteWebServiceRequest();
                //TODO返回空直接返回 失败信息 不为空要对字符串进行处理 过滤到多余的头信息
                if (TextUtils.isEmpty(result)) {
                    System.out.println("--->>Result = NULL<<---");
                    AsyncHttpRequest.this.requestCallback.onNullSucess();
                    return;
                }

                // 没有解析器 不需要解析直接返回
                if(baseHandler==null){
                    responseProcess(result);
                    return ;
                }


                String regex="<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
                String data="<data>";
                String  enddata="</data>";
                result= result.replaceAll("<\\?xml version=\"1.0\" encoding=\"UTF-8\"\\?>", "");
                result=regex+data+result+enddata;
                System.out.println("Result = " + result);
                System.out.println(" ===========================================================================" );
                responseProcess(result);
            } else if(requestType == RequestType.TXT){
                String demo=getFileToStr("assets/demo.txt");
                System.out.println("-------->> " + demo);
                responseProcess(demo);

            }
        } catch (Exception e) {
            AsyncHttpRequest.this.requestCallback.onFail(e);
        }
    }

    @Override
    protected void setConnectTimeout(int connectTimeout) {
        super.setConnectTimeout(connectTimeout);
    }


    @Override
    protected String buildAndExecuteWebServiceRequest() throws IOException,
            XmlPullParserException {
        StringBuilder result = new StringBuilder();
        SoapObject request = new SoapObject(serviceNameSpace,
                requestAction.serviceName);

        System.out.println("serviceUrl = " + serviceUrl);
        System.out.println("serviceNameSpace = " + serviceNameSpace);
        System.out.println("requestAction.serviceName = " + requestAction.serviceName);
        // 2.设置调用方法参数
        if (requestAction.queryKeys.size() >= 1) {
            List<String> queryKeys = requestAction.queryKeys;
            Bundle queryBundle = requestAction.queryBundle;
            int size = queryKeys.size();
            for (int i = 0; i < size; i++) {
                request.addProperty(queryKeys.get(i),
                        queryBundle.get(queryKeys.get(i)));
                System.out.println("property=" + queryKeys.get(i) + ";  value=" + queryBundle.get(queryKeys.get(i)));
            }
        }

        if (null != requestAction.pageBean && requestAction.isPageBeanEnable) {
            List<String> queryKeys = requestAction.pageBean.getQueryKeys();
            Bundle queryBundle = requestAction.pageBean.getQueryBundle();
            int size = queryKeys.size();
            for (int i = 0; i < size; i++) {
                request.addProperty(queryKeys.get(i),
                        queryBundle.getInt(queryKeys.get(i)));
                System.out.println(".......分页参数....property=" + queryKeys.get(i) + ";value=" + queryBundle.get(queryKeys.get(i)));
            }
        }
        // 3.设置SOAP请求信息(参数部分为SOAP协议版本号,与你要调用的webService中版本号一致)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                SoapEnvelope.VER11);
        envelope.bodyOut = request;
        envelope.dotNet = true;
        //	Log.i("", ".........请求数据....envelope.bodyOut...>>" + envelope.bodyOut);
        // 4.注册Envelope
        (new MarshalBase64()).register(envelope);
        // 5.构建传输对象,并指明WSDL文档URL,Android传输对象
        HttpTransportSE transport = new HttpTransportSE(serviceUrl);//timeout

        transport.debug = true;
        //envelope.implicitTypes = true;
        envelope.setOutputSoapObject(request);
        String respStr = "";
        // 6.调用WebService(其中参数为1:命名空间+方法名称,2:Envelope对象) envelope.bodyIn
        transport.call( soapAction + requestAction.serviceName, envelope);
        if (envelope.getResponse() != null) {
            if (envelope.getResponse() instanceof SoapObject) {
                SoapObject response = (SoapObject) envelope.getResponse();
                int count = response.getPropertyCount();
                for (int i = 0; i < count; i++) {
                    result.append(response.getProperty(i).toString());
                }
                respStr = result.toString();

            } else if (envelope.getResponse() instanceof SoapPrimitive) {
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                respStr = response.toString();
            }
        }
       // System.out.println("respStr = " + respStr);
        return respStr;
    }

    public String getFileToStr(String fileName) {
        StringBuffer temp = new StringBuffer();
        try {
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream(fileName);
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String tempLine = rd.readLine();
            while (tempLine != null) {
                temp.append(tempLine);
                tempLine = rd.readLine();
            }
        } catch (Exception e) {
        }
        String result = temp.toString();
        return result;
    }

    private void writeLogInfo(String logName, String respStr) {
        try {
            FileWriter fw = new FileWriter("/sdcard/DCIM/Camera/" + logName
                    + "_log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(respStr);
            bw.close();
            fw.close();
        } catch (Exception e) {
        }

    }

    protected Map<String, String> buildWebServiceRequest() {
        Map<String, String> params = new HashMap<String, String>();
        if (null == parameters)
            return params;
        if (null != parameters && parameters.size() > 0) {
            int len = parameters.size();
            RequestParameter reqParam = null;
            for (int i = 0; i < len; i++) {
                reqParam = parameters.get(i);
                params.put(reqParam.getName(), reqParam.getValue());
            }
        }
        return params;
    }


    public String encodeParameters() {
        return null;
    }

    private static void log(String message) {
        System.out.println("[" + new java.util.Date() + "]" + message);
    }

    protected void responseProcess(String httpResponse) {
        Response response = new Response();
        response.setResponseAsString(httpResponse);
        requestCallback.onSuccess(response);
    }


    public void setRequestHeader(String name, String value) {
        requestHeaders.put(name, value);
    }

    public String getRequestHeader(String name) {
        return requestHeaders.get(name);
    }

    public void setRequestAction(RequestAction requestAction) {
        this.requestAction = requestAction;
    }

}
