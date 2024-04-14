package com.br.nofrontier.food.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Embeddable
public class Address {

	private String zipCode;

	private String street;

	private String number;

	private String complement;

	private String neighborhood;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City cities;

}
