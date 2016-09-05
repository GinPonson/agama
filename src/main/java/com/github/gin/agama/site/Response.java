package com.github.gin.agama.site;

public class Response {
	
	private int responseCode;
	
	private String responseMsg;
	
	private byte[] contentByte;
	
	private String contentType;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

	public byte[] getContentByte() {
		return contentByte;
	}

	public void setContentByte(byte[] contentByte) {
		this.contentByte = contentByte;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
