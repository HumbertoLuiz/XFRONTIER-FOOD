package com.br.nofrontier.food.api.v1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.nofrontier.food.domain.model.Kitchen;
import com.br.nofrontier.food.domain.service.RegistrationKitchenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/kitchens")
@RequiredArgsConstructor
public class KitchenController {

	private final RegistrationKitchenService registrationKitchenService;

	// KitchenModelAssembler kitchenModelAssembler;

	// KitchenInputDisassembler kitchenInputDisassembler;

	// PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

	// @CheckSecurity.Kitchens.CanConsult

	// ---------------------------------------------------------------------------------------------------------

	@GetMapping
	public List<Kitchen> findAll() {
		return registrationKitchenService.findAll();
	}

	// ---------------------------------------------------------------------------------------------------------

//    @CheckSecurity.Kitchens.PodeConsultar
//    @Override
	@GetMapping("/{kitchenId}")
	public ResponseEntity<Kitchen> findById(@PathVariable Long kitchenId) {
		Optional<Kitchen> kitchen = Optional.ofNullable(registrationKitchenService.findById(kitchenId));
		if(kitchen.isPresent()) {
		return ResponseEntity.ok(kitchen.get());
		}
		return ResponseEntity.notFound().build();
	}

	// ---------------------------------------------------------------------------------------------------------

//    @CheckSecurity.Kitchens.PodeEditar
//    @Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Kitchen save(@RequestBody @Valid Kitchen kitchen) {
		return registrationKitchenService.save(kitchen);
	}

	// ---------------------------------------------------------------------------------------------------------

//    @CheckSecurity.Kitchens.PodeEditar
//    @Override
	@PutMapping("/{kitchenId}")
	public ResponseEntity<Kitchen> update(@PathVariable Long kitchenId, @RequestBody @Valid Kitchen kitchen) {
		Optional<Kitchen> currentKitchen = Optional.ofNullable(registrationKitchenService.update(kitchenId, kitchen));
		if(currentKitchen.isPresent()) {
		return ResponseEntity.ok(currentKitchen.get());
		}
		return ResponseEntity.notFound().build();
	}

	// ---------------------------------------------------------------------------------------------------------

	// @CheckSecurity.Kitchens.PodeEditar
	@DeleteMapping("/{kitchenId}")
	// @ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> remove(@PathVariable Long kitchenId) {
		registrationKitchenService.remove(kitchenId);
		return ResponseEntity.noContent().build();
	}

}
