package com.example.base;

public class RestResponse<T> {
	private int code;
	private String message;
	private T response;
	private int count;

	public RestResponse(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public RestResponse(int code, String message, T response) {
		this.code = code;
		this.message = message;
		this.response = response;
	}

	public RestResponse(int code, String message, T response, int count) {
		this.code = code;
		this.message = message;
		this.response = response;
		this.count = count;
	}

	public static RestResponse fail(Integer code, String msg) {
		return new RestResponse<>(code, msg);
	}

	public static RestResponse ok() {
		SystemCode systemCode = SystemCode.OK;
		return new RestResponse<>(systemCode.getCode(), systemCode.getMessage());
	}

	public static <F> RestResponse<F> ok(F response) {
		SystemCode systemCode = SystemCode.OK;
		return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), response);
	}

	public static <F> RestResponse<F> ok(F response, int count) {
		SystemCode systemCode = SystemCode.OK;
		return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), response, count);
	}

	public static <F> RestResponse<F> ok(String msg, F response) {
		SystemCode systemCode = SystemCode.OK;
		return new RestResponse<>(systemCode.getCode(), msg, response);
	}

	public static RestResponse ok(Integer code, String msg) {
		return new RestResponse<>(code, msg);
	}

	public static RestResponse system(SystemCode code) {
		return new RestResponse<>(code.getCode(), code.getMessage());
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
