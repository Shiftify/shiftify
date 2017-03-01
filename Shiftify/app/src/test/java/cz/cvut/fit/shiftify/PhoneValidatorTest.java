package cz.cvut.fit.shiftify;

import org.junit.Test;

import cz.cvut.fit.shiftify.helpers.Validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ilia on 01-Mar-17.
 */

public class PhoneValidatorTest {

    @Test
    public void phoneValidatorTest_ValidPhones_ReturnsTrue() {
        String[] validPhones = {
                "+420123456789",
                "+71234567890",
        };

        for (String validPhone : validPhones) {
            assertTrue("\"" + validPhone + "\" is valid phone but Validator.phoneValid returns false!", Validator.phoneValid(validPhone));
        }
    }

    @Test
    public void phoneValidatorTest_InvalidPhones_ReturnsFalse() {
        String[] invalidPhones = {
                "",
                null,
                "123",
                "420123456789",
                "+420123"
        };

        for (String invalidPhone : invalidPhones) {
            assertFalse("\"" + invalidPhone + "\" is invalid phone but Validator.phoneValid returns true!", Validator.phoneValid(invalidPhone));
        }
    }
}
