package com.br.nofrontier.food.domain.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.br.nofrontier.food.domain.event.OrderConfirmedEvent;
import com.br.nofrontier.food.domain.model.Order;
import com.br.nofrontier.food.domain.service.SendEmailService.Message;
import com.br.nofrontier.food.domain.service.SendEmailService;

@Component
public class NotificationCustomerOrderConfirmedListener {

	@Autowired
	private SendEmailService sendEmail;
	
	@TransactionalEventListener
	public void toConfirmOrder(OrderConfirmedEvent event) {
		Order order = event.getOrder();
		
		var message = Message.builder()
				.subject(order.getRestaurants().getName() + " - Order confirmed")
				.body("emails/order-confirmed.html")
				.variable("order", order)
				.addressee(order.getCustomer().getEmail())
				.build();

		sendEmail.send(message);
	}
	
}
