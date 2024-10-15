package com.br.nofrontier.food.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.nofrontier.food.domain.model.State;

public interface StateRepository extends JpaRepository<State, Long> {

	Optional<State> save(Optional<State> currentState);

}
