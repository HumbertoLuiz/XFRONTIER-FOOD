package com.br.nofrontier.food.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.nofrontier.food.domain.model.Restaurant;

public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>, RestaurantRepositoryQueries,
		JpaSpecificationExecutor<Restaurant> {

	@Query("from Restaurant r join fetch r.kitchen join fetch r.paymentMethods")
	List<Restaurant> findAll();

	List<Restaurant> findByShippingRateBetween(BigDecimal initialShippingRate, BigDecimal finalShippingRate);

	@Query("from Restaurant where name like %:name% and kitchen.id = :id")
	List<Restaurant> findByName(String name, @Param("id") Long kitchen);

	List<Restaurant> findByNameContainingAndKitchen(String name, Long kitchenId);

	Optional<Restaurant> findFirstRestaurantByNameContaining(String name);

	List<Restaurant> findTop2ByNameContaining(String name);

	int countKitchenById(Long kitchenId);

	boolean existsResponsibleById(Long userId);

	Optional<Restaurant> save(Optional<Restaurant> currentRestaurant);

}
