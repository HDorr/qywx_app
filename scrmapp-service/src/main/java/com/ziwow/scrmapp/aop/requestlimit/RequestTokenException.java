package com.ziwow.scrmapp.aop.requestlimit;
public class RequestTokenException extends Exception {
	private static final long serialVersionUID = 1364225358754654702L;
	public RequestTokenException() {
		super("token解密失败");
	}
	public RequestTokenException(String message) {
		super(message);
	}
}