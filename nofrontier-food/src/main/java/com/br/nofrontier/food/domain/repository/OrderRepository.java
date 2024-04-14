package com.br.nofrontier.food.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.br.nofrontier.food.domain.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>, CustomJpaRepository<Order, Long>,
		JpaSpecificationExecutor<Order> {

	Optional<Order> findByCode(String code);

	@Query("from Order p join fetch p.customer join fetch p.restaurants r join fetch r.kitchen")
	List<Order> findAll();
	
	boolean existsOrderByCode(String code);
	
}
