package cz.cvut.fit.shiftify.data.validator;

import java.util.regex.Pattern;

/**
 * Created by ondra on 19.3.17.
 */

public class PhoneValidator implements IPropertyValidator {
    public static final IPropertyValidator INSTANCE = new PhoneValidator();

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+(?:[0-9]●?){6,14}[0-9]$");

    @Override
    public ValidatorMessage validate(String name, Object value) {
        if (value == null || value.equals("")) {
            return new ValidatorMessage(name, PropertyType.PHONE, value, "Telefon není vyplněn.", ValidatorState.WARNING);
        }

        if (!(value instanceof String)) {
            return new ValidatorMessage(name, PropertyType.PHONE, value, "Nekompatibilní typ: očekává se řetězec", ValidatorState.ERROR);
        }

        String phone = (String) value;

        boolean matches = PHONE_PATTERN.matcher(phone).matches();

        if (matches) {
            return new ValidatorMessage(name, PropertyType.PHONE, value, "Telefon zadán správně.", ValidatorState.OK);
        } else {
            return new ValidatorMessage(name, PropertyType.PHONE, value, "Telefon není zadán ve správném formátu.", ValidatorState.ERROR);
        }
    }
}
