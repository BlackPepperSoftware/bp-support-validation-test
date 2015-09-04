package uk.co.blackpepper.support.validation.test;

import javax.validation.ConstraintViolation;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class ConstraintViolationMatcher extends TypeSafeMatcher<ConstraintViolation<?>> {

	private final String expectedField;
	
	private final String expectedMessage;

	public ConstraintViolationMatcher(String expectedField, String expectedMessage) {
		this.expectedField = expectedField;
		this.expectedMessage = expectedMessage;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("constraint violation (field=")
			.appendValue(expectedField)
			.appendText(", message=")
			.appendValue(expectedMessage)
			.appendText(")");
	}

	@Override
	protected boolean matchesSafely(ConstraintViolation<?> actual) {
		return expectedField.equals(actual.getPropertyPath().toString())
			&& expectedMessage.equals(actual.getMessage());
	}
	
	public static Matcher<ConstraintViolation<?>> constraintViolation(String expectedField, String expectedMessage) {
		return new ConstraintViolationMatcher(expectedField, expectedMessage);
	}
}
