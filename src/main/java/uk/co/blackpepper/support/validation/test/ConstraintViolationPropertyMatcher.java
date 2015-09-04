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
