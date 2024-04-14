package com.br.nofrontier.food.domain.service;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.nofrontier.food.domain.exception.EntityInUseException;
import com.br.nofrontier.food.domain.exception.RestaurantNotFoundException;
import com.br.nofrontier.food.domain.model.Kitchen;
import com.br.nofrontier.food.domain.model.Restaurant;
import com.br.nofrontier.food.domain.repository.KitchenRepository;
import com.br.nofrontier.food.domain.repository.RestaurantRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationRestaurantService {

	private static final String MSG_RESTAURANT_IN_USE = "Restaurant code %d cannot be removed as it is in use";

	private final RestaurantRepository restaurantRepository;

	private final KitchenRepository kitchenRepository;

	// ---------------------------------------------------------------------------------------------------------

	public Iterable<Restaurant> findAll() {
		return restaurantRepository.findAll();
	}

	// ---------------------------------------------------------------------------------------------------------

	public Restaurant findById(Long restaurantId) {
		Optional<Restaurant> restaurantFound = Optional.ofNullable(restaurantRepository.findById(restaurantId)
				.orElseThrow(() -> new RestaurantNotFoundException(restaurantId)));
		return restaurantFound.get();
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long kitchenId = restaurant.getKitchen().getId();
		Kitchen kitchen = kitchenRepository.findById(kitchenId).orElseThrow(() -> new EntityNotFoundException(
				String.format("there is no register kitchen with %d code", kitchenId)));
		restaurant.setKitchen(kitchen);
		return restaurantRepository.save(restaurant);
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public Restaurant update(Long restaurantId, Restaurant restaurant) {
		return restaurantRepository.findById(restaurantId).map(currentRestaurant -> {
			restaurant.setId(currentRestaurant.getId());
			return restaurantRepository.save(restaurant);
		}).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public void remove(Long restaurantId) {
		try {
			restaurantRepository.findById(restaurantId).map(restaurant -> {
				restaurantRepository.deleteById(restaurantId);
				restaurantRepository.flush();
				return Void.TYPE;
			}).orElseThrow(() -> new RestaurantNotFoundException(restaurantId));

		} catch (EmptyResultDataAccessException e) {
			throw new RestaurantNotFoundException(restaurantId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_RESTAURANT_IN_USE, restaurantId));
		}
	}

}
