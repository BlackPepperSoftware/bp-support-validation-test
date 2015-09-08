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

import javax.validation.ConstraintViolation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConstraintViolationPropertyMatcher extends TypeSafeMatcher<ConstraintViolation<?>> {

	private final String expectedField;

	public ConstraintViolationPropertyMatcher(String expectedField) {
		this.expectedField = expectedField;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("constraint violation (field=")
			.appendValue(expectedField)
			.appendText(")");
	}

	@Override
	protected boolean matchesSafely(ConstraintViolation<?> actual) {
		return actual.getPropertyPath().toString().startsWith(expectedField);
	}

	/**
	 * @deprecated Use {@link ConstraintViolationMatcher} instead.
	 */
	@Deprecated
	public static Matcher<ConstraintViolation<?>> propertyViolation(String expectedField) {
		return new ConstraintViolationPropertyMatcher(expectedField);
	}
}
