package cz.cvut.fit.shiftify;

import org.junit.Test;
import static org.junit.Assert.*;
import cz.cvut.fit.shiftify.helpers.Validator;

/**
 * Created by Ilia on 01-Mar-17.
 */

public class EmailValidatorTest {

    @Test
    public void emailValidatorTest_ValidEmails_ReturnsTrue(){
        String[] validEmails={
                "test_email@gmail.com"
        };

        for (String validEmail:  validEmails) {
            assertTrue("\"" + validEmail + "\" is valid email but Validator.emailValid returns false!", Validator.emailValid(validEmail));
        }
    }

    @Test
    public void emailValidatorTest_InvalidEmails_ReturnsFalse(){
        String[] invalidEmails={
                "",
                null,
                "@gmail.com",
                "test_email@gmail",
                "test_email",
        };

        for (String invalidEmail: invalidEmails){
            assertFalse("\"" + invalidEmail + "\" is invalid email but Validator.emailValid returns true!", Validator.emailValid(invalidEmail));
        }
    }
}
