package com.br.nofrontier.food.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.br.nofrontier.food.domain.service.SendEmailService;
import com.br.nofrontier.food.domain.service.SendEmailService.Message;
import com.br.nofrontier.food.domain.event.OrderCancelledEvent;
import com.br.nofrontier.food.domain.model.Order;


@Component
public class NotificationCustomerOrderCanceledListener {

	@Autowired
	private SendEmailService sendEmail;
	
	@TransactionalEventListener
	public void toCancelOrder(OrderCancelledEvent event) {
		Order order = event.getOrder();
		
		var message = Message.builder()
				.subject(order.getRestaurants().getName() + " - Order cancelled")
				.body("emails/order-cancelled.html")
				.variable("order", order)
				.addressee(order.getCustomer().getEmail())
				.build();

		sendEmail.send(message);
	}
	
}
