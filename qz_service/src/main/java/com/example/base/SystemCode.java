package com.example.base;

public enum SystemCode {
	/**
	 * OK
	 */
	OK(1, "成功"),
	/**
	 * AccessTokenError
	 */
	AccessTokenError(403, "用户登录令牌失效"),
	/**
	 * UNAUTHORIZED
	 */
	UNAUTHORIZED(401, "用户未登录"),
	/**
	 * UNAUTHORIZED
	 */
	AuthError(402, "用户名或密码错误"),
	/**
	 * InnerError
	 */
	InnerError(500, "系统内部错误"),
	/**
	 * ParameterValidError
	 */
	ParameterValidError(501, "参数验证错误"),

	/**
	 * AccessDenied
	 */
	AccessDenied(502, "用户没有权限访问"),

	INEXISTENCE(402, "用户不存在"),

	INACTIVAED(402, "用户未激活");

	int code;
	String message;

	SystemCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
