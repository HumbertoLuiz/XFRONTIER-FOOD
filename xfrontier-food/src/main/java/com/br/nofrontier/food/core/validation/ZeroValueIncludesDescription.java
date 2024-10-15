package com.br.nofrontier.food.core.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ZeroValueIncludesValidatorDescription.class })
public @interface ZeroValueIncludesDescription {

	String message() default "invalid mandatory description";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
	
	String valueField();
	
	String descriptionField();
	
	String mandatoryDescription();
	
}
