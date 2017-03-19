package cz.cvut.fit.shiftify.data.validator;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by ondra on 19.3.17.
 */

public class PhoneValidatorTest {
    private static final IPropertyValidator VALIDATOR = PhoneValidator.INSTANCE;
    private static final String NAME = "phone";

    private static final List<String> VALID_PHONES = Arrays.asList("+420123456789", "+71234567890");
    private static final List<String> INVALID_PHONES = Arrays.asList("123", "420123456789", "+420123");

    @Test
    public void testEmptyPhones() {
        ValidatorMessage msg = VALIDATOR.validate(NAME, null);
        ValidatorMessage msg2 = VALIDATOR.validate(NAME, "");
        assertTrue(msg.getState() == ValidatorState.WARNING);
        assertTrue(msg2.getState() == ValidatorState.WARNING);
    }

    @Test
    public void testValidPhones() {
        for (String phone : VALID_PHONES) {
            ValidatorMessage message = VALIDATOR.validate(NAME, phone);
            assertTrue(MessageFormat.format("Phone number {0} should be valid!", phone), message.getState() == ValidatorState.OK);
        }
    }

    @Test
    public void testInvalidPhones() {
        for (String phone : INVALID_PHONES) {
            ValidatorMessage message = VALIDATOR.validate(NAME, phone);
            assertTrue(MessageFormat.format("Phone number {0} should be invalid!", phone), message.getState() == ValidatorState.ERROR);
        }
    }
}
