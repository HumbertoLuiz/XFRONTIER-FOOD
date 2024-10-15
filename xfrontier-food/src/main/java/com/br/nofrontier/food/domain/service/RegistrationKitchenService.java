package com.br.nofrontier.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.nofrontier.food.domain.exception.EntityInUseException;
import com.br.nofrontier.food.domain.exception.KitchenNotFoundException;
import com.br.nofrontier.food.domain.model.Kitchen;
import com.br.nofrontier.food.domain.repository.KitchenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegistrationKitchenService {

	private static final String MSG_KITCHEN_IN_USE = "Kitchen code %d cannot be removed as it is in use";

	private final KitchenRepository kitchenRepository;

	// ---------------------------------------------------------------------------------------------------------

	public List<Kitchen> findAll() {
		return kitchenRepository.findAll();
	}

	// ---------------------------------------------------------------------------------------------------------

	public Kitchen findById(Long kitchenId) {
		Optional<Kitchen> kitchenFound = Optional.ofNullable(kitchenRepository.findById(kitchenId)
				.orElseThrow(() -> new KitchenNotFoundException(kitchenId)));
		return kitchenFound.get();
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public Kitchen save(Kitchen kitchen) {
		return kitchenRepository.save(kitchen);
	}

	// ---------------------------------------------------------------------------------------------------------

//	@Transactional
//	public Kitchen update(Long kitchenId, Kitchen kitchen) {
//		Optional<Kitchen> currentKitchen = kitchenRepository.findById(kitchenId);
//		if (currentKitchen.isPresent()) {
//			BeanUtils.copyProperties(kitchen, currentKitchen.get(), "id");
//		}
//		Kitchen savedKitchen = kitchenRepository.save(currentKitchen.get());
//		return savedKitchen;
//	}

	@Transactional
	public Kitchen update(Long kitchenId, Kitchen kitchen) {
		return kitchenRepository.findById(kitchenId).map(currentKitchen -> {
			kitchen.setId(currentKitchen.getId());
			return kitchenRepository.save(kitchen);
		}).orElseThrow(() -> new KitchenNotFoundException(kitchenId));
	}

	// ---------------------------------------------------------------------------------------------------------

//	@Transactional
//	public void remove(Long kitchenId) {
//		try {
//			kitchenRepository.deleteById(kitchenId);
//			kitchenRepository.flush();
//
//		} catch (EmptyResultDataAccessException e) {
//			throw new KitchenNotFoundException(kitchenId);
//
//		} catch (DataIntegrityViolationException e) {
//			throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, kitchenId));
//		}
//	}

	@Transactional
	public void remove(Long kitchenId) {
		try {
			kitchenRepository.findById(kitchenId).map(kitchen -> {
				kitchenRepository.deleteById(kitchenId);
				kitchenRepository.flush();
				return Void.TYPE;
			}).orElseThrow(() -> new KitchenNotFoundException(kitchenId));

		} catch (EmptyResultDataAccessException e) {
			throw new KitchenNotFoundException(kitchenId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, kitchenId));
		}
	}

}
