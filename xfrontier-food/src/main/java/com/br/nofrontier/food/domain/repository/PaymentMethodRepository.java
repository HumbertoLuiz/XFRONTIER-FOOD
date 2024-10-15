package com.br.nofrontier.food.domain.repository;

import java.time.OffsetDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.nofrontier.food.domain.model.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {

	@Query("select max(updateDate) from PaymentMethod")
	OffsetDateTime getLastUpdate();
	
	@Query("select updateDate from PaymentMethod where id = :paymentMethod")
	OffsetDateTime getUpdateDateById(Long paymentMethod);
	
}
