package com.br.nofrontier.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.nofrontier.food.domain.model.City;

public interface CityRepository extends JpaRepository<City, Long> {

	Optional<City> save(Optional<City> currentCity);

}
