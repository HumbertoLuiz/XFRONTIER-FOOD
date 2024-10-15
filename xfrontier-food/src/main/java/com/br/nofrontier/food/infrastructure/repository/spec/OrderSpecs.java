package com.br.nofrontier.food.infrastructure.repository.spec;

import java.util.ArrayList;

import org.springframework.data.jpa.domain.Specification;

import com.br.nofrontier.food.domain.filter.OrderFilter;
import com.br.nofrontier.food.domain.model.Order;

import jakarta.persistence.criteria.Predicate;

public class OrderSpecs {

	public static Specification<Order> usingFilter(OrderFilter filter) {
		return (root, query, builder) -> {
			if (Order.class.equals(query.getResultType())) {
				root.fetch("restaurant").fetch("kitchen");
				root.fetch("customer");
			}

			var predicates = new ArrayList<Predicate>();

			if (filter.getCustomerId() != null) {
				predicates.add(builder.equal(root.get("customer").get("id"), filter.getCustomerId()));
			}

			if (filter.getRestaurantId() != null) {
				predicates.add(builder.equal(root.get("restaurant").get("id"), filter.getRestaurantId()));
			}

			if (filter.getInitialCreationDate() != null) {
				predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), filter.getInitialCreationDate()));
			}

			if (filter.getExitCreationDate() != null) {
				predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), filter.getExitCreationDate()));
			}

			return builder.and(predicates.toArray(new Predicate[0]));
		};
	}

}
