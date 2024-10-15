package com.br.nofrontier.food.api.v1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.nofrontier.food.domain.model.City;
import com.br.nofrontier.food.domain.service.RegistrationCityService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/cities")
public class CityController {

	@Autowired
	private RegistrationCityService registrationCityService;

	
	// ---------------------------------------------------------------------------------------------------------

	
	@GetMapping
	public List<City> findAll() {
		return registrationCityService.findAll();
	}
	
	
	// ---------------------------------------------------------------------------------------------------------

	
	@GetMapping("/{cityId}")
	public ResponseEntity<Optional<City>> findById(@PathVariable Long cityId) {
		Optional<City> city = Optional.ofNullable(registrationCityService.findById(cityId));
		if (city == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(city);
	}
	
	
	// ---------------------------------------------------------------------------------------------------------

	
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid City city) {
    	try {    		
    		city = registrationCityService.save(city);
    		return ResponseEntity.status(HttpStatus.CREATED)
    				.body(city);
    	} catch (EntityNotFoundException e) {
    		return ResponseEntity.badRequest()
    				.body(e.getMessage());
    	}
    }
    
    
	// ---------------------------------------------------------------------------------------------------------

    
	@PutMapping("/{cityId}")
	public ResponseEntity<Optional<City>> update(@PathVariable Long cityId, @RequestBody @Valid City city) {
		Optional<City> currentCity = registrationCityService.update(cityId, city);
		return ResponseEntity.ok(currentCity);
	}
    
    
	// ---------------------------------------------------------------------------------------------------------

    
	@DeleteMapping("/{cityId}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove(@PathVariable Long cityId) {
		City city = registrationCityService.findOrFail(cityId);
		if (city != null) {			
			registrationCityService.remove(cityId);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
    
}
