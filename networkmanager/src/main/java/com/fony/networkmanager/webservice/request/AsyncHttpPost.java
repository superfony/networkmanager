package com.fony.networkmanager.webservice.request;
import com.fony.networkmanager.webservice.request.model.RequestParameter;
import java.util.List;

/**
 * @Title 异步Http Post请求
 * @Descriptoin 线程的终止工作交给线程池,当activity停止的时候,设置回调函数为false,就不会执行回调方法
 * @author fony
 */
public class AsyncHttpPost extends AsyncHttpRequest {
	private static final long serialVersionUID = 5139886571863034394L;
	public AsyncHttpPost(RequestAction requestAction,
			RequestCallback requestCallback) {
		super(requestAction, requestCallback);
	}
}
