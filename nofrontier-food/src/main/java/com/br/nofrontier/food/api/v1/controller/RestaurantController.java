package com.br.nofrontier.food.api.v1.controller;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.nofrontier.food.domain.model.Restaurant;
import com.br.nofrontier.food.domain.service.RegistrationRestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

	private final RegistrationRestaurantService registrationRestaurantService;

	// ---------------------------------------------------------------------------------------------------------

	@GetMapping
	public Iterable<Restaurant> findAll() {
		return registrationRestaurantService.findAll();
	}

	// ---------------------------------------------------------------------------------------------------------

	@GetMapping("/{restaurantId}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Restaurant> findById(@PathVariable Long restaurantId) {
		Optional<Restaurant> restaurant = Optional.ofNullable(registrationRestaurantService.findById(restaurantId));
		if(restaurant.isPresent()) {
		return ResponseEntity.ok(restaurant.get());
		}
		return ResponseEntity.notFound().build();
	}

	// ---------------------------------------------------------------------------------------------------------

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurant save(@RequestBody @Valid Restaurant restaurant) {
		return registrationRestaurantService.save(restaurant);
	}

	// ---------------------------------------------------------------------------------------------------------

	@PutMapping("/{restaurantId}")
	public ResponseEntity<Restaurant> update(@PathVariable Long restaurantId,
			@RequestBody @Valid Restaurant restaurant) {
		Optional<Restaurant> currentRestaurant = Optional
				.ofNullable(registrationRestaurantService.update(restaurantId, restaurant));
		if(currentRestaurant.isPresent()) {
		return ResponseEntity.ok(currentRestaurant.get());
		}
		return ResponseEntity.noContent().build();
	}

	// ---------------------------------------------------------------------------------------------------------

	@DeleteMapping("/{restaurantId}")
	public ResponseEntity<Void> remove(@PathVariable Long restaurantId) {
		registrationRestaurantService.remove(restaurantId);
		return ResponseEntity.noContent().build();
	}

	// ---------------------------------------------------------------------------------------------------------

	@PatchMapping("/{restaurantId}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long restaurantId, @RequestBody Map<String, Object> fields) {
		Restaurant currentRestaurant = registrationRestaurantService.findById(restaurantId);
		if (currentRestaurant == null) {
			return ResponseEntity.notFound().build();
		}
		merge(fields, currentRestaurant);
		return update(restaurantId, currentRestaurant);
	}

	private void merge(Map<String, Object> originData, Restaurant targetRestaurant) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurant originRestaurant = objectMapper.convertValue(originData, Restaurant.class);
		originData.forEach((nameProperty, valueProperty) -> {
			Field field = ReflectionUtils.findField(Restaurant.class, nameProperty);
			field.setAccessible(true);
			Object newValue = ReflectionUtils.getField(field, originRestaurant);
			ReflectionUtils.setField(field, targetRestaurant, newValue);
		});
	}

}
