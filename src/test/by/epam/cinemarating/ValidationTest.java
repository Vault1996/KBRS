package test.by.epam.cinemarating;

import by.epam.cinemarating.validation.AuthenticationValidator;
import org.junit.Assert;
import org.junit.Test;

public class ValidationTest {
	@Test
	public void authenticationValidationTest() {
		String login = "Hello";
		String password = "1234567";
		AuthenticationValidator validator = new AuthenticationValidator();
		Assert.assertTrue(validator.validate(login, password));
	}
}
