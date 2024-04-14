package com.br.nofrontier.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.nofrontier.food.domain.model.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

}
