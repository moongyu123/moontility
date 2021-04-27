package com.moongyu123.moontility.work.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="user_info")
public class UserInfo {

	@Id
	private Integer id;

	//기능1. 자리안비움
	private boolean antiafk_use;

	//기능2. 클립보드 전송
	private boolean clipboard_use;    //사용여부
	private boolean clipboard_receive_mode;  //수신모드여부
	private String clipboard_receive_method; //수신 method
	private String clipboard_receive_url;     //수신url
	private String clipboard_receive_data;    //수신 param
	private String clipboard_send_method;     //수신 method
	private String clipboard_send_url;        //송신url
	private String clipboard_send_data;       //송신 param
	private String clipboard_send_data_key;   //송신 param중 clipboard로 저장될 key

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isAntiafk_use() {
		return antiafk_use;
	}

	public void setAntiafk_use(boolean antiafk_use) {
		this.antiafk_use = antiafk_use;
	}

	public boolean isClipboard_use() {
		return clipboard_use;
	}

	public void setClipboard_use(boolean clipboard_use) {
		this.clipboard_use = clipboard_use;
	}

	public boolean isClipboard_receive_mode() {
		return clipboard_receive_mode;
	}

	public void setClipboard_receive_mode(boolean clipboard_receive_mode) {
		this.clipboard_receive_mode = clipboard_receive_mode;
	}

	public String getClipboard_receive_method() {
		return clipboard_receive_method;
	}

	public void setClipboard_receive_method(String clipboard_receive_method) {
		this.clipboard_receive_method = clipboard_receive_method;
	}

	public String getClipboard_receive_url() {
		return clipboard_receive_url;
	}

	public void setClipboard_receive_url(String clipboard_receive_url) {
		this.clipboard_receive_url = clipboard_receive_url;
	}

	public String getClipboard_receive_data() {
		return clipboard_receive_data;
	}

	public void setClipboard_receive_data(String clipboard_receive_data) {
		this.clipboard_receive_data = clipboard_receive_data;
	}

	public String getClipboard_send_method() {
		return clipboard_send_method;
	}

	public void setClipboard_send_method(String clipboard_send_method) {
		this.clipboard_send_method = clipboard_send_method;
	}

	public String getClipboard_send_url() {
		return clipboard_send_url;
	}

	public void setClipboard_send_url(String clipboard_send_url) {
		this.clipboard_send_url = clipboard_send_url;
	}

	public String getClipboard_send_data() {
		return clipboard_send_data;
	}

	public void setClipboard_send_data(String clipboard_send_data) {
		this.clipboard_send_data = clipboard_send_data;
	}

	public String getClipboard_send_data_key() {
		return clipboard_send_data_key;
	}

	public void setClipboard_send_data_key(String clipboard_send_data_key) {
		this.clipboard_send_data_key = clipboard_send_data_key;
	}

	@Override public String toString() {
		return "UserInfo{" +
				"id=" + id +
				", antiafk_use=" + antiafk_use +
				", clipboard_use=" + clipboard_use +
				", clipboard_receive_mode=" + clipboard_receive_mode +
				", clipboard_receive_method='" + clipboard_receive_method + '\'' +
				", clipboard_receive_url='" + clipboard_receive_url + '\'' +
				", clipboard_receive_data='" + clipboard_receive_data + '\'' +
				", clipboard_send_method='" + clipboard_send_method + '\'' +
				", clipboard_send_url='" + clipboard_send_url + '\'' +
				", clipboard_send_data='" + clipboard_send_data + '\'' +
				", clipboard_send_data_key='" + clipboard_send_data_key + '\'' +
				'}';
	}
}