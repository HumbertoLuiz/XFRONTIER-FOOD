package com.br.nofrontier.food.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.nofrontier.food.domain.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
