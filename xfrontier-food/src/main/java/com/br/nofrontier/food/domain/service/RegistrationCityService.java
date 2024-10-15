package com.br.nofrontier.food.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.br.nofrontier.food.domain.exception.CityNotFoundException;
import com.br.nofrontier.food.domain.exception.EntityInUseException;
import com.br.nofrontier.food.domain.model.City;
import com.br.nofrontier.food.domain.model.State;
import com.br.nofrontier.food.domain.repository.CityRepository;
import com.br.nofrontier.food.domain.repository.StateRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RegistrationCityService {

	private static final String MSG_KITCHEN_IN_USE = "City code %d cannot be removed as it is in use";

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StateRepository stateRepository;

	// ---------------------------------------------------------------------------------------------------------

	public List<City> findAll() {
		return cityRepository.findAll();
	}

	// ---------------------------------------------------------------------------------------------------------

	public City findById(Long cityId) {
		var cityFound = cityRepository.findById(cityId);
		if (cityFound.isPresent()) {
			return cityFound.get();
		}
		throw new CityNotFoundException(cityId);
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public City save(City city) {
		Long stateId = city.getStates().getId();
		State state = stateRepository.findById(stateId).orElseThrow(
				() -> new EntityNotFoundException(String.format("there is no register state with %d code", stateId)));
		city.setStates(state);
		return cityRepository.save(city);
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public Optional<City> update(Long cityId, City city) {
		Optional<City> currentCity = cityRepository.findById(cityId);
		if (currentCity != null) {
			BeanUtils.copyProperties(city, currentCity.get(), "id");
		}
		currentCity = cityRepository.save(currentCity);
		return currentCity;
	}

	// ---------------------------------------------------------------------------------------------------------

	@Transactional
	public void remove(Long cityId) {
		try {
			cityRepository.deleteById(cityId);
			cityRepository.flush();

		} catch (EmptyResultDataAccessException e) {
			throw new CityNotFoundException(cityId);

		} catch (DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format(MSG_KITCHEN_IN_USE, cityId));
		}
	}

	// ---------------------------------------------------------------------------------------------------------

	public City findOrFail(Long cityId) {
		return cityRepository.findById(cityId).orElseThrow(() -> new CityNotFoundException(cityId));
	}

}
