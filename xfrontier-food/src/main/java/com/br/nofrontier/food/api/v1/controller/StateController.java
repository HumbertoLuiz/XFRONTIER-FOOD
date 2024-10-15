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

import com.br.nofrontier.food.domain.model.State;
import com.br.nofrontier.food.domain.service.RegistrationStateService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/states")
public class StateController {

	@Autowired
	private RegistrationStateService registrationStateService;

	
	// ---------------------------------------------------------------------------------------------------------

	
	@GetMapping
	public List<State> findAll() {
		return registrationStateService.findAll();
	}
	
	
	// ---------------------------------------------------------------------------------------------------------

	
	@GetMapping("/{stateId}")
	public ResponseEntity<Optional<State>> findById(@PathVariable Long stateId) {
		Optional<State> state = Optional.ofNullable(registrationStateService.findById(stateId));
		if (state == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(state);
	}
	
	
	// ---------------------------------------------------------------------------------------------------------

	
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid State state) {
    	try {    		
    		state = registrationStateService.save(state);
    		return ResponseEntity.status(HttpStatus.CREATED)
    				.body(state);
    	} catch (EntityNotFoundException e) {
    		return ResponseEntity.badRequest()
    				.body(e.getMessage());
    	}
    }
    
    
	// ---------------------------------------------------------------------------------------------------------

    
	@PutMapping("/{stateId}")
	public ResponseEntity<Optional<State>> update(@PathVariable Long stateId, @RequestBody @Valid State state) {
		Optional<State> currentState = registrationStateService.update(stateId, state);
		return ResponseEntity.ok(currentState);
	}
    
    
	// ---------------------------------------------------------------------------------------------------------

    
	@DeleteMapping("/{stateId}")
	//@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove(@PathVariable Long stateId) {
		State state = registrationStateService.findOrFail(stateId);
		if (state != null) {			
			registrationStateService.remove(stateId);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
    
}
