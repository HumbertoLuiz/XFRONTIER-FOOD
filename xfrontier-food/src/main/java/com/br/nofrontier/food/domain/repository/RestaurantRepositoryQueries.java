package com.br.nofrontier.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.br.nofrontier.food.domain.model.Restaurant;

public interface RestaurantRepositoryQueries {
	

	List<Restaurant> find(BigDecimal initialShippingRate, BigDecimal finalShippingRate);

	
	List<Restaurant> findWithFreeShipping(String name);

}