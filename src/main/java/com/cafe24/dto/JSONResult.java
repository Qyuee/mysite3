package com.cafe24.dto;

/**
 * private String result; private String message; private Object data;
 * 
 * @author bit
 *
 */
public class JSONResult {
	private String result; // "success" or "fail"
	private String message; // result가 fail이면 "원인 메세지" or null
	private Object data; // result가 success이면 전달할 데이터

	// 외부에서 객체를 만들수 없도록 정의
	// 객체는 내부에서 생성하고 반환한다. 팩토리 형태로 사용할 것임.
	private JSONResult(String result, String message, Object data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public static JSONResult success(Object data) {
		// 성공하면 데이터를 넘기고
		return new JSONResult("success", null, data);
	}

	public static JSONResult fail(String message) {
		// 실패하면 message를 넘기고
		return new JSONResult("fail", message, null);
	}

	public String getResult() {
		return result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JSONResult [result=" + result + ", message=" + message + ", data=" + data + "]";
	}

}
