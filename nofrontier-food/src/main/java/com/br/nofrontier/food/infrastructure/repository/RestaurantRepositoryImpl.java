package com.br.nofrontier.food.infrastructure.repository;


import static com.br.nofrontier.food.infrastructure.repository.spec.RestaurantSpecs.withFreeShipping;
import static com.br.nofrontier.food.infrastructure.repository.spec.RestaurantSpecs.withSimilarName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.br.nofrontier.food.domain.model.Restaurant;
import com.br.nofrontier.food.domain.repository.RestaurantRepository;
import com.br.nofrontier.food.domain.repository.RestaurantRepositoryQueries;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.Predicate;

@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired @Lazy
	private RestaurantRepository restaurantRepository;

	public List<Restaurant> find(String name, BigDecimal initialShippingRate, BigDecimal finalShippingRate) {
		var builder = manager.getCriteriaBuilder();

		var criteria = builder.createQuery(Restaurant.class);
		var root = criteria.from(Restaurant.class);

		var predicates = new ArrayList<Predicate>();

		if (StringUtils.hasText(name)) {
			predicates.add(builder.like(root.get("name"), "%" + name + "%"));
		}

		if (initialShippingRate != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("shippingRate"), initialShippingRate));
		}

		if (finalShippingRate != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("shippingRate"), finalShippingRate));
		}

		criteria.where(predicates.toArray(new Predicate[0]));

		var query = manager.createQuery(criteria);
		return query.getResultList();
	}

	@Override
	public List<Restaurant> findWithFreeShipping(String name) {
		return restaurantRepository.findAll(withFreeShipping().and(withSimilarName(name)));
	}

	@Override
	public List<Restaurant> find(BigDecimal initialShippingRate, BigDecimal finalShippingRate) {
		// TODO Auto-generated method stub
		return null;
	}


}
