package cz.cvut.fit.shiftify.data.validator;

/**
 * Created by ondra on 28.2.17.
 */

public interface IPropertyValidator {
    public static IPropertyValidator OK_VALIDATOR = new OkValidator();

    /**
     * validates parameter
     * @param name parameter name
     * @param value parameter value
     */
    public ValidatorMessage validate(String name, Object value);

    /**
     * Dummy implementation
     */
    public static class OkValidator implements IPropertyValidator {
        @Override
        public ValidatorMessage validate(String name, Object value) {
            return new ValidatorMessage(name, PropertyType.NONE, value, null, ValidatorState.OK);
        }
    }
}
