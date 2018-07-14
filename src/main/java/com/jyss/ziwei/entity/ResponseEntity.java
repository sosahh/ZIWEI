package com.jyss.ziwei.entity;

import java.io.Serializable;

public class ResponseEntity implements Serializable{
	private String status;
	private String  msg;      //错误信息
	private Object data;


	public static ResponseEntity se(Object data){
		return new ResponseEntity(data);
	}

	public static ResponseEntity op(String status, String msg){
		return new ResponseEntity(status,msg);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public ResponseEntity(Object data) {
		this.status = "1";
		this.msg = "";
		this.data = data;
	}

	public ResponseEntity(String status, String msg) {
		this.status = status;
		this.msg = msg;
		this.data = "";
	}
}
