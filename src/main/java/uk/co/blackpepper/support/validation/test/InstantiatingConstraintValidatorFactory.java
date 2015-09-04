package uk.co.blackpepper.support.validation.test;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ValidationException;

class InstantiatingConstraintValidatorFactory implements ConstraintValidatorFactory {

	@Override
	public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
		try {
			return key.getConstructor().newInstance();
		}
		catch (InstantiationException
			| IllegalAccessException
			| IllegalArgumentException
			| InvocationTargetException
			| NoSuchMethodException
			| SecurityException exception) {
			throw new ValidationException("Cannot instantiate class: " + key.getName(), exception);
		}
	}

	@Override
	public void releaseInstance(ConstraintValidator<?, ?> instance) {
		// no-op
	}
}
