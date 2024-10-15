package com.br.nofrontier.food.domain.event;

import com.br.nofrontier.food.domain.model.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {

	private Order order;

}
