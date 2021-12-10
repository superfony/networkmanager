package com.fony.networkmanager.webservice.request;
import org.apache.http.conn.ConnectTimeoutException;
import java.io.EOFException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.BindException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class HttpException extends BaseException {
	private static final long serialVersionUID = -497244674482523810L;

	/**
	 * {@link EOFException} 抛出此类异常,表示连接丢失,也就是说网络连接的另一端非正常关闭连接(可能是主机断电、网线出现故障等导致)
	 */
	public final static int SERVER_CLOSED_EXCEPTION = 0x01;

	/**
	 * {@link SocketException} 抛出此类异常,表示无法连接,也就是说当前主机不存在
	 */
	public final static int CONNECT_EXCEPTION = 0X02;

	/**
	 * {@link SocketException} 抛出此类异常,表示
	 * <ul>
	 * <li>1:连接正常关闭,也就是说另一端主动关闭连接</li>
	 * <li>2:表示一端关闭连接,而另一端此时在读数据</li>
	 * <li>3:表示一端关闭连接,而另一端此时在发送数据</li>
	 * <li>4:表示连接已关闭,但还继续使用(也就是读/写操作)此连接</li>
	 * </ul>
	 */
	public final static int SOCKET_EXCEPTION = 0x03;

	/**
	 * {@link BindException} 抛出此类异常,表示端口已经被占用
	 */
	public final static int BIND_EXCEPTION = 0x04;

	/**
	 * {@link ConnectTimeoutException} 连接超时
	 */
	public final static int CONNECT_TIMEOUT_EXCEPTION = 0x05;

	/**
	 * {@link UnsupportedEncodingException} 不支持的编码异常
	 */
	public final static int UNSUPPORTED_ENCODEING_EXCEPTION = 0x06;

	/**
	 * {@link SocketTimeoutException} socket 超时异常
	 */
	public final static int SOCKET_TIMEOUT_EXCEPTION = 0x06;

	/**
	 * {@link } 客户端协议异常
	 */
	public final static int CLIENT_PROTOL_EXCEPTION = 0x07;

	/**
	 * {@link IOException} 读取异常
	 */
	public final static int IO_EXCEPTION = 0x08;

	public final static int EXCEPTION = 0x09;



	public HttpException(int code, Throwable throwable) {
		super(code, throwable);
	}

	public HttpException(int code, String msg) {
		super(code, msg);
	}

	public HttpException(int code) {
		super(code);
	}



}
