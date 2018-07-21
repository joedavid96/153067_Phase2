package com.cg.paymentapp.jdbc.exception;
@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {
	public InsufficientBalanceException(String msg) {
		super(msg);
	}
}
