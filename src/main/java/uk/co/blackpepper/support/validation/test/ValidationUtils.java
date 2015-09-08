/*
 * Copyright 2014 Black Pepper Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.co.blackpepper.support.validation.test;

import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

public final class ValidationUtils {
	
	/**
	 * @deprecated A bundle name needs to be provided for resolving validation messages; use
	 *             {@link #newValidator(String)} instead.
	 */
	@Deprecated
	private static final Validator VALIDATOR = Validation.byDefaultProvider()
		.configure()
		.messageInterpolator(new ResourceBundleMessageInterpolator())
		.buildValidatorFactory()
		.getValidator();
	
	private ValidationUtils() {
		throw new AssertionError();
	}

	/**
	 * @deprecated A bundle name needs to be provided for resolving validation messages; use
	 *             {@link #newValidator(String)} and invoke {@code Validator.validate} instead.
	 */
	@Deprecated
	public static <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
		return VALIDATOR.validate(object, groups);
	}

	/**
	 * @deprecated A bundle name needs to be provided for resolving validation messages; use
	 *             {@link #newValidator(String)} and invoke {@code Validator.validateValue} instead.
	 */
	@Deprecated
	public static <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
		Class<?>... groups) {

		return VALIDATOR.validateValue(beanType, propertyName, value, groups);
	}
	
	/**
	 * @deprecated A bundle name needs to be provided for resolving validation messages; use
	 *             {@link #newValidator(String, ConstraintValidator)} instead.
	 */
	@Deprecated
	public static Validator newValidatorWithConstraintValidator(ConstraintValidator<?, ?> constraintValidator) {
		return Validation.byDefaultProvider()
			.configure()
			.constraintValidatorFactory(newConstraintValidatorFactory(constraintValidator))
			.buildValidatorFactory()
			.getValidator();
	}

	public static Validator newValidator(String messageBundleName) {
		return newValidator(messageBundleName, null);
	}
	
	public static Validator newValidator(String messageBundleName, ConstraintValidator<?, ?> constraintValidator) {
		ConstraintValidatorFactory constraintValidatorFactory = (constraintValidator != null)
			? newConstraintValidatorFactory(constraintValidator) : null;
		
		MessageInterpolator messageInterpolator = new ResourceBundleMessageInterpolator(
			new PlatformResourceBundleLocator(messageBundleName));
			
		return Validation.byDefaultProvider()
			.configure()
			.constraintValidatorFactory(constraintValidatorFactory)
			.messageInterpolator(messageInterpolator)
			.buildValidatorFactory()
			.getValidator();
	}

	private static InstantiatingConstraintValidatorFactory newConstraintValidatorFactory(
		final ConstraintValidator<?, ?> constraintValidator) {
		return new InstantiatingConstraintValidatorFactory() {
			@Override
			public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
				if (constraintValidator.getClass().equals(key)) {
					return key.cast(constraintValidator);
				}
				return super.getInstance(key);
			}
		};
	}
}
