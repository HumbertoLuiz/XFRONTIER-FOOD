package com.br.nofrontier.food.core.validation;

import java.math.BigDecimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;

import org.springframework.beans.BeanUtils;

public class ZeroValueIncludesValidatorDescription implements ConstraintValidator<ZeroValueIncludesDescription, Object> {

	private String valueField;
	private String descriptionField;
	private String mandatoryDescription;
	
	@Override
	public void initialize(ZeroValueIncludesDescription constraint) {
		this.valueField = constraint.valueField();
		this.descriptionField = constraint.descriptionField();
		this.mandatoryDescription = constraint.mandatoryDescription();
	}
	
	@Override
	public boolean isValid(Object objectValidation, ConstraintValidatorContext context) {
		boolean valid = true;
		
		try {
			BigDecimal value = (BigDecimal) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), valueField)
					.getReadMethod().invoke(objectValidation);
			
			String description = (String) BeanUtils.getPropertyDescriptor(objectValidation.getClass(), descriptionField)
					.getReadMethod().invoke(objectValidation);
			
			if (value != null && BigDecimal.ZERO.compareTo(value) == 0 && description != null) {
				valid = description.toLowerCase().contains(this.mandatoryDescription.toLowerCase());
			}
			
			return valid;
		} catch (Exception e) {
			throw new ValidationException(e);
		}
	}

}
