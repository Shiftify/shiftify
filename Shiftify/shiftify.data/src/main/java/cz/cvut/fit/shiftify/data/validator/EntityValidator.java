package cz.cvut.fit.shiftify.data.validator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ondra on 28.2.17
 *
 * This class methods validate given entity and will return results in such detail that is wanted
 *
 * ReflectiveOperationException is thrown only when there is some error in entity architecture usually. - Anyway it is programmer fault, not fault of a user
 */

public class EntityValidator {
    private static Map<PropertyType, IPropertyValidator> validators = new HashMap<>();

    static {
        validators.put(PropertyType.MAIL, EmailValidator.INSTANCE);
        validators.put(PropertyType.NONE, IPropertyValidator.OK_VALIDATOR);
        validators.put(PropertyType.PHONE, PhoneValidator.INSTANCE);
    }

    /**
     * @return all messages
     */
    public static ValidatorResult validateWithAllMessages(Object entity) throws ReflectiveOperationException {
        return validate(entity, entity.getClass(), ValidatorState.OK);
    }

    /**
     *
     * @return warning and error messages
     */
    public static ValidatorResult validateWithoutOkMessages(Object entity) throws ReflectiveOperationException {
        return validate(entity, entity.getClass(), ValidatorState.WARNING);
    }

    /**
     *
     * @return error messages
     */
    public static ValidatorResult validateWithErrorsOnly(Object entity) throws ReflectiveOperationException {
        return validate(entity, entity.getClass(), ValidatorState.ERROR);
    }

    private static ValidatorResult validate(Object entity, Class<?> klass, ValidatorState minimum) throws ReflectiveOperationException {
        ValidatorResult result = new ValidatorResult(minimum);

        Method[] methods = klass.getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(ValidateProperty.class)) {
                String propertyName = getPropertyName(method.getName(), method.getReturnType());
                Object value = method.invoke(entity);

                PropertyType propertyType = method.getAnnotation(ValidateProperty.class).propertyType();
                IPropertyValidator validator = validators.get(propertyType);

                ValidatorMessage msg = validator.validate(propertyName, value);

                result.addMessage(msg);
            }
        }

        return result;
    }

    private static String getPropertyName(String propertyName, Class<?> returnType) throws ReflectiveOperationException {
        boolean returnsBoolean = returnType.isAssignableFrom(boolean.class) || returnType.isAssignableFrom(Boolean.class);
        String prefix = (returnsBoolean) ? "is" : "get";

        if (!propertyName.startsWith(prefix)) {
            throw new ReflectiveOperationException("Internal exception: Method " + propertyName + " is not a getter!");
        }

        String name = propertyName.substring(prefix.length());
        if (!name.isEmpty() && Character.isUpperCase(name.charAt(0))) {
            char c = Character.toLowerCase(name.charAt(0));
            name = c + name.substring(1);
        }

        return name;
    }
}
