package com.br.nofrontier.food.domain.repository;

import java.util.Optional;

import com.br.nofrontier.food.domain.model.UserEntity;

public interface UserRepository extends CustomJpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);
	
}
