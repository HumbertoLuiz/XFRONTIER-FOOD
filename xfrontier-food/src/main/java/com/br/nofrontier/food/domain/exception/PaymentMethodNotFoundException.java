package com.br.nofrontier.food.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PaymentMethodNotFoundException(String message) {
		super(message);
	}
	
	public PaymentMethodNotFoundException(Long stateId) {
		this(String.format("There is no payment method registration with a code %d", stateId));
	}
	
}
