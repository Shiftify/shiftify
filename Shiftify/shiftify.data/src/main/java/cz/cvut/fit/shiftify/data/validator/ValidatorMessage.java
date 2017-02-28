package cz.cvut.fit.shiftify.data.validator;

/**
 * Created by ondra on 28.2.17.
 *
 * Keeps single result
 */
public class ValidatorMessage {

    private final String name;
    private final PropertyType propertyType;
    private final Object value;
    private final String explanation;
    private final ValidatorState state;

    public ValidatorMessage(String name, PropertyType propertyType, Object value, String explanation, ValidatorState state) {
        this.name = name;
        this.propertyType = propertyType;
        this.value = value;
        this.explanation = explanation;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public Object getValue() {
        return value;
    }

    public String getExplanation() {
        return explanation;
    }

    public ValidatorState getState() {
        return state;
    }
}
