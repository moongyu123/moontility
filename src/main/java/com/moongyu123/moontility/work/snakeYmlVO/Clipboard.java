package com.moongyu123.moontility.work.snakeYmlVO;

public class Clipboard {
	private boolean use;    //사용여부
	private boolean isReceiveMode;  //수신모드여부
	private String receive_method; //수신 method
	private String receive_url;     //수신url
	private String receive_data;    //수신 param
	private String send_method;     //수신 method
	private String send_url;        //송신url
	private String send_data;       //송신 param
	private String send_data_key;   //송신 param중 clipboard로 저장될 key

	public String getReceive_data() {
		return receive_data;
	}

	public void setReceive_data(String receive_data) {
		this.receive_data = receive_data;
	}

	public String getReceive_method() {
		return receive_method;
	}

	public void setReceive_method(String receive_method) {
		this.receive_method = receive_method;
	}

	public String getSend_method() {
		return send_method;
	}

	public void setSend_method(String send_method) {
		this.send_method = send_method;
	}

	public String getSend_data() {
		return send_data;
	}

	public void setSend_data(String send_data) {
		this.send_data = send_data;
	}

	public String getSend_data_key() {
		return send_data_key;
	}

	public void setSend_data_key(String send_data_key) {
		this.send_data_key = send_data_key;
	}

	public boolean isUse() {
		return use;
	}

	public void setUse(boolean use) {
		this.use = use;
	}

	public boolean isReceiveMode() {
		return isReceiveMode;
	}

	public void setReceiveMode(boolean receiveMode) {
		isReceiveMode = receiveMode;
	}

	public String getReceive_url() {
		return receive_url;
	}

	public void setReceive_url(String receive_url) {
		this.receive_url = receive_url;
	}

	public String getSend_url() {
		return send_url;
	}

	public void setSend_url(String send_url) {
		this.send_url = send_url;
	}

	@Override public String toString() {
		String indent = "    ";
		String line = System.getProperty("line.separator");
		
		return "clipboard: " + line +
				indent + "use: " + use + line +
				indent + "isReceiveMode: " + isReceiveMode +line +
				indent + "receive_method: " + receive_method +line +
				indent + "receive_url: " + receive_url +line +
				indent + "receive_data: " + receive_data +line +
				indent + "send_method: " + send_method +line +
				indent + "send_url: " + send_url +line +
				indent + "send_data: " + send_data +line +
				indent + "send_data_key: " + send_data_key +line
				;
	}
}
