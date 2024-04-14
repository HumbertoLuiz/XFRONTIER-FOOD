package com.br.nofrontier.food.domain.repository;

import java.util.List;
import java.util.Optional;

import com.br.nofrontier.food.domain.model.Kitchen;

public interface KitchenRepository extends CustomJpaRepository<Kitchen, Long> {

	List<Kitchen> findAllByNameContaining(String name);
	
	List<Kitchen> findAllByName(String name);
	
	Optional<Kitchen> findByName(String name);
	
	boolean existsByName(String name);

	Optional<Kitchen> save(Optional<Kitchen> currentKitchen);
	
	
	
	
}
