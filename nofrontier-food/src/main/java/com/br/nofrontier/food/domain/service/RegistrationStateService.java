package com.br.nofrontier.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.nofrontier.food.domain.exception.EntityInUseException;
import com.br.nofrontier.food.domain.exception.StateNotFoundException;
import com.br.nofrontier.food.domain.model.State;
import com.br.nofrontier.food.domain.repository.StateRepository;

@Service
public class RegistrationStateService {

	private static final String MSG_KITCHEN_IN_USE = "State code %d cannot be removed as it is in use";

	@Autowired
	private StateRepository stateRepository;

	// ---------------------------------------------------------------------------------------------------------

	public List<State> findAll() {
		return stateRepository.findAll();
	}

	// ---------------------------------------------------------------------------------------------------------

	public State findById(Long stateId) {
		var stateFound = stateRepository.findById(stateId);
		if (stateFound.isPresent()) {
			return stateFound.get();
		}
		throw new StateNotFoundException(stateId);
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public State save(State state) {
		return stateRepository.save(state);
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public Optional<State> update(Long stateId, State state) {
		Optional<State> currentState = stateRepository.findById(stateId);
		if (currentState != null) {
			BeanUtils.copyProperties(state, currentState.get(), "id");
		}
		currentState = stateRepository.save(currentState);
		return currentState;
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public void remove(Long stateId) {
		try {
			stateRepository.deleteById(stateId);
			stateRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new StateNotFoundException(stateId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, stateId));
		}
	}

	// ---------------------------------------------------------------------------------------------------------

	public State findOrFail(Long stateId) {
		return stateRepository.findById(stateId).orElseThrow(() -> new StateNotFoundException(stateId));
	}

}
