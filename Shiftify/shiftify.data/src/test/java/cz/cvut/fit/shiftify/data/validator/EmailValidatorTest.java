package cz.cvut.fit.shiftify.data.validator;

import junit.framework.Assert;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by ondra on 28.2.17.
 */

public class EmailValidatorTest {
    private static String NAME = "name";
    private static EmailValidator VALIDATOR = new EmailValidator();

    private static List<String> VALID_MAILS = Arrays.asList("user@50Cent.cnb", "u+ser@localhost.com", "My.User-_You@localhost.cz");
    private static List<String> INVALID_MAILS = Arrays.asList("domain.com", "@domain.com", "domain", "user@.com", "user@domain.", "user@domain..com", "user@domain.cz@domain.com", "user*me@domain.com");

    @Test
    public void testEmptyMails() {
        ValidatorMessage msg = VALIDATOR.validate(NAME, null);
        ValidatorMessage msg2 = VALIDATOR.validate(NAME, "");
        assertTrue(msg.getState() == ValidatorState.WARNING);
        assertTrue(msg2.getState() == ValidatorState.WARNING);
    }

    @Test
    public void testOkMails() {
        for (String mail : VALID_MAILS) {
            ValidatorMessage msg = VALIDATOR.validate(NAME, mail);
            assertTrue(MessageFormat.format("Mail {0} should be valid.", mail), msg.getState() == ValidatorState.OK);
        }
    }

    @Test
    public void testInvalidMails() {
        for (String mail : INVALID_MAILS) {
            ValidatorMessage msg = VALIDATOR.validate(NAME, mail);
            assertTrue(MessageFormat.format("Mail {0} should be invalid.", mail), msg.getState() == ValidatorState.ERROR);
        }
    }

}
