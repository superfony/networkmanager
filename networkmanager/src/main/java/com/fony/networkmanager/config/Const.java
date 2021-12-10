package com.fony.networkmanager.config;
import com.fony.networkmanager.webservice.WebServiceBaseHelp;
/*
 * webservice 接口定义
 * @author fony
 */
public class Const {
    // 记录日志
    public static final String LOG = "logs";
    //*****************************base config ************************************************
    public static boolean IS_DEBUG = true;
    public static String HTTP = "http://";
    public static String APPERARANCEIP = "192.168.30.126:8080";
    public static String SoapAction = "http://" + APPERARANCEIP + "/ajlw2015/";
    public static String ServerUrl = "http://" + APPERARANCEIP + "/ajlw2015/services/DataService";

    // http 新服务接口
    public static String APPERARANCEIP2013 = "192.168.30.28:8089";
    public static String SERVERURL2013 = "http://" + APPERARANCEIP2013 + "/cars2013";

    // 命名空间
    public static final String serviceNameSpace = "http://ws";
    // 图片上传地址
    public static String UPLOADIMAGE = "http://" + APPERARANCEIP + "/ajlw2015/servlet/UploadFilesServlet";
    //retrofit 服务地址
    public static String retrofitServerUrl = "http://192.168.30.65:8080/";

    public static void setApperaranceIp(String ip) {
        APPERARANCEIP = ip;
        retrofitServerUrl = "http://" + APPERARANCEIP + "/";
       // APIRetrofit.resetUrl();

        SoapAction = "http://" + APPERARANCEIP + "/ajlw2015/";
        ServerUrl = "http://" + APPERARANCEIP + "/ajlw2015/services/DataService";
        UPLOADIMAGE = "http://" + APPERARANCEIP + "/ajlw2015/servlet/UploadFilesServlet";
        WebServiceBaseHelp.getInstance().setServiceNameSpace(Const.serviceNameSpace);
        WebServiceBaseHelp.getInstance().setServiceUrl(Const.ServerUrl);
        WebServiceBaseHelp.getInstance().setSoapAction(Const.SoapAction);
        // APIRetrofit.serverUrl="http://"+APPERARANCEIP+"/";
    }

    // 安检大部分webservice 接口用到
    public static final String getLst = "getLst";

}
