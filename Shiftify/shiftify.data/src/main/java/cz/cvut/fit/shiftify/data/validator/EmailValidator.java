package cz.cvut.fit.shiftify.data.validator;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ondra on 28.2.17.
 *
 * validates mail. Regexp pattern copied from https://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
 */

public class EmailValidator implements IPropertyValidator {
    public static IPropertyValidator INSTANCE = new EmailValidator();

    private Pattern pattern;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public ValidatorMessage validate(String name, Object value) {
        if (value == null || value.equals("")) {
            return new ValidatorMessage(name, PropertyType.MAIL, value, "Email není vyplněn.", ValidatorState.WARNING);
        }

        if (!(value instanceof String)) {
            return new ValidatorMessage(name, PropertyType.MAIL, value, "Email není řetězec.", ValidatorState.ERROR);
        }

        String mail = (String) value;
        Matcher m = pattern.matcher(mail);

        if (m.matches()) {
            return new ValidatorMessage(name, PropertyType.MAIL, value, MessageFormat.format("Email {0} je validní.", mail), ValidatorState.OK);
        } else {
            return new ValidatorMessage(name, PropertyType.MAIL, value, MessageFormat.format("Email {0} není validní.", mail), ValidatorState.ERROR);
        }
    }
}
