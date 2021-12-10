package com.fony.networkmanager.webservice;
import android.text.TextUtils;
import com.fony.networkmanager.webservice.model.Response;
import com.fony.networkmanager.webservice.request.AsyncHttpPost;
import com.fony.networkmanager.webservice.request.BaseRequest;
import com.fony.networkmanager.webservice.request.BaseRequest.RequestType;
import com.fony.networkmanager.webservice.request.RequestAction;
import com.fony.networkmanager.webservice.request.RequestCallback;
import com.fony.networkmanager.webservice.xml.XmlParseModule;
import com.fony.networkmanager.webservice.xml.handler.BaseParserHandler;
import com.fony.networkmanager.webservice.xml.handler.DefaultSaxHandler;
import com.fony.networkmanager.webservice.xml.parser.BaseXmlParser;
import com.fony.networkmanager.webservice.xml.parser.sax.DefaultSaxXmlParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于继承Activity 类的webservice调用及xml解析方式
 * 2021年04月02日16:18:46
 * 目前还不支持异步
 * @author fony
 */
public class WebServiceBaseHelp {
//	private Context mContext;

    /**
     * 当前所持有的所有请求
     */
    private List<BaseRequest> requestList = null;
    private RequestCallback requestCallback;
    private BaseParserHandler parseHandler;
    private ModuleResponseProcessor responseProcess;



    private String serviceNameSpace;
    private String serviceUrl;
    private String soapAction;

    public void setSoapAction(String soapAction) {
        this.soapAction = soapAction;
    }


    private String TAG = WebServiceBaseHelp.class.getName();

    public static WebServiceBaseHelp webServiceBaseHelp=new WebServiceBaseHelp();


    public static WebServiceBaseHelp getInstance() {
        return webServiceBaseHelp;
    }

    public WebServiceBaseHelp() {

    }

    //异步方式调用
    public  WebServiceBaseHelp getInstancAsync(){
        return new WebServiceBaseHelp();
    }


    public WebServiceBaseHelp(String serviceNameSpace,
                              String serviceUrl,String soapAction) {
        this.serviceNameSpace = serviceNameSpace;
        this.serviceUrl = serviceUrl;
        this.soapAction=soapAction;
    }

    public void init() {
        requestList = new ArrayList<BaseRequest>();
    }

    public void onPause() {
        cancelRequest();
    }

    public void onDestroy() {
        cancelRequest();
    }

    /**
     * 在activity销毁的时候同时设置停止请求,停止线程请求回调.
     */
    private void cancelRequest() {
        if (null == requestList || requestList.size() <= 0)
            return;

    }

    /**
     * @param requestAction   请求参数设置
     * @param baseHandler     解析器
     * @param responseProcess 回调对象
     * @param requestType     请求类型
     */
    public void executeRequest(RequestAction requestAction,
                               BaseParserHandler baseHandler,
                               ModuleResponseProcessor responseProcess, RequestType requestType) {

        this.parseHandler = baseHandler;
        this.responseProcess = responseProcess;
        requestCallback = new DefaultCallback();
        AsyncHttpPost httpost = new AsyncHttpPost(requestAction,
                requestCallback);
        httpost.setBaseHandler(parseHandler);
        httpost.setRequestType(requestType);
        httpost.setServiceNameSpace(serviceNameSpace);
        httpost.setServiceUrl(serviceUrl);
        httpost.setSoapAction(soapAction);
        new Thread(httpost).start();
    }

    // RequestListener 用于回调结果
    public void executeRequest(RequestAction requestAction,
                               BaseParserHandler baseHandler,
                               ModuleResponseProcessor responseProcess,RequestListener requestListener, RequestType requestType) {

        this.parseHandler = baseHandler;
        this.responseProcess = responseProcess;
        this.requestListener=requestListener;
        requestCallback = new DefaultCallback();
        AsyncHttpPost httpost = new AsyncHttpPost(requestAction,
                requestCallback);
        httpost.setBaseHandler(parseHandler);
        httpost.setRequestType(requestType);
        httpost.setServiceNameSpace(serviceNameSpace);
        httpost.setServiceUrl(serviceUrl);
        httpost.setSoapAction(soapAction);
        new Thread(httpost).start();
    }



    public interface RequestListener {
        void onSuccess(Response response);
        void onFail(Exception e);
        void onNullSucess();
    }


    //TODO 设置RequestListener  是为了让数据直接返回 不进行下面的解析的操作
    private RequestListener requestListener;

    private class DefaultCallback implements RequestCallback {
        @Override
        public void onSuccess(Response response) {
            // 没有解析器直接返回
            if (null == parseHandler) {
                requestListener.onSuccess(response);
                return;
            }
            if (null == xmlParserProvider) {
                processResponse(WebServiceBaseHelp.this, response,
                        getBaseXmlParser(response, parseHandler),
                        responseProcess);
            } else {
                processResponse(WebServiceBaseHelp.this, response,
                        xmlParserProvider.createXmlParser(response),
                        responseProcess);
            }
        }

        @Override
        public void onFail(Exception e) {
            e.printStackTrace();
            if (null != requestListener)
                requestListener.onFail(e);
        }

        @Override
        public void onNullSucess() {
            requestListener.onNullSucess();
        }
    }

    /**
     * 生成自定义的xmlparser
     */
    public interface XmlParserProvider {
        BaseXmlParser createXmlParser(Response response);
    }

    private XmlParserProvider xmlParserProvider;

    /**
     * 开始解析调用
     *
     * @param httpModule    本身类的一个实列
     * @param response      成功请求的xml数据
     * @param baseXmlParser 解析器 是自己定义的解析器也可以是默认实现的解析器 不用的解析器返回的对象是不一样的
     * @param callBack      回调接口对象
     */
    public void processResponse(WebServiceBaseHelp httpModule, Response response,
                                BaseXmlParser baseXmlParser, ModuleResponseProcessor callBack) {

        XmlParseModule xmlParseModule = new XmlParseModule(response);
        xmlParseModule.setBaseXmlParser(baseXmlParser);
        // 开始解析
        xmlParseModule.parseXml();
        if (null != callBack) {
            callBack.processResponse(httpModule, xmlParseModule
                    .getBaseXmlParser().getContent());
        }
    }

    // XmlParseModule-》BaseXmlParser
    // DefaultSaxXmlParser-》SaxXmlParser-》DefaultXmlParser-》BaseXmlParser
    // DefaultSaxHandler-》 BaseParserHandler-》DefaultHandler
    /* 返回解析器管理类工具 */
    public BaseXmlParser getBaseXmlParser(Response response,
                                          BaseParserHandler baseHandler) {
        String ras = null;
        try {
            ras = response.asString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultSaxXmlParser saxXmlParser = new DefaultSaxXmlParser();

        /*
         * 先提取xml里面的message信息 如果为1 说明返回错误信息 或无结果返回 如果为0 说明有值返回 接下来进行解析
         */
        //String result = CommonRegex.getMiddleValue("result", ras);
//        if (null != result) {
//            if (null == baseHandler || result.equals("1")) {
//                saxXmlParser.setHandler(new DefaultSaxHandler());// 默认的解析器
//            } else {
//                saxXmlParser.setHandler(baseHandler);// 自己重写的解析器用来处理每次具体请求
//            }
//        } else {
//            saxXmlParser.setHandler(new DefaultSaxHandler());
//        }

        // 为空的时候进行处理
        if(TextUtils.isEmpty(ras)){
            saxXmlParser.setHandler(new DefaultSaxHandler());
        }else
        saxXmlParser.setHandler(baseHandler);
        return saxXmlParser;
    }

    public List<BaseRequest> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<BaseRequest> requestList) {
        this.requestList = requestList;
    }


    public void setRequestListener(RequestListener requestListener) {
        this.requestListener = requestListener;
    }

    public XmlParserProvider getXmlParserProvider() {
        return xmlParserProvider;
    }

    public void setXmlParserProvider(XmlParserProvider xmlParserProvider) {
        this.xmlParserProvider = xmlParserProvider;
    }

    public RequestCallback getRequestCallback() {
        return requestCallback;
    }

    public void setRequestCallback(RequestCallback requestCallback) {
        this.requestCallback = requestCallback;
    }

    public ModuleResponseProcessor getResponseProcess() {
        return responseProcess;
    }

    public BaseParserHandler getParseHandler() {
        return parseHandler;
    }

    public void setServiceNameSpace(String serviceNameSpace) {
        this.serviceNameSpace = serviceNameSpace;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void setParseHandler(BaseParserHandler parseHandler) {
        this.parseHandler = parseHandler;
    }
}
