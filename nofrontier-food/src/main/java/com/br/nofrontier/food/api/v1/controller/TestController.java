package com.br.nofrontier.food.api.v1.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.nofrontier.food.domain.model.Kitchen;
import com.br.nofrontier.food.domain.model.Restaurant;
import com.br.nofrontier.food.domain.repository.KitchenRepository;
import com.br.nofrontier.food.domain.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tests")
public class TestController {
	
	private final KitchenRepository kitchenRepository;
	
	private final RestaurantRepository retaurantRepository;

	@GetMapping("/kitchens/by-name")
	public List<Kitchen> kitchensByName(String name) {
		return kitchenRepository.findAllByName(name);
	}
	
	@GetMapping("/kitchens/unique-by-name")
	public Optional<Kitchen> kitchenByName(String name) {
		return kitchenRepository.findByName(name);
	}
	
	@GetMapping("/restaurants/by-shipping-rate")
	public List<Restaurant> restaurantByShippingRate(
			BigDecimal initialShippingRate, BigDecimal finalShippingRate) {
		return retaurantRepository.findByShippingRateBetween(initialShippingRate, finalShippingRate);
	}
	
	@GetMapping("/restaurants/by-name")
	public List<Restaurant> restaurantByShippingRate(String name,
			Long kitchenId) {
		return retaurantRepository.findByNameContainingAndKitchen(name, kitchenId);
	}	
	
	@GetMapping("/restaurants/first-by-name")
	public Optional<Restaurant> restaurantFirstByName(String name) {
		return retaurantRepository.findFirstRestaurantByNameContaining(name);
	}	

	@GetMapping("/restaurants/top2-by-name")
	public List<Restaurant> restaurantTop2ByName(String name) {
		return retaurantRepository.findTop2ByNameContaining(name);
	}	

	
	@GetMapping("/restaurants/count-by-kitchen")
	public int restaurantCountyByKitchen(Long kitchenId) {
		return retaurantRepository.countKitchenById(kitchenId);
	}
	
	@GetMapping("/restaurants/with-free-shipping")
	public List<Restaurant> restaurantWithFreeShipping(String name) {
		return retaurantRepository.findWithFreeShipping(name);
	}
	
	
}
