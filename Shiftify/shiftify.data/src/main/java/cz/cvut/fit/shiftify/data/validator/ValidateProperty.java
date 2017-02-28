package cz.cvut.fit.shiftify.data.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ondra on 28.2.17.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateProperty {
    public PropertyType propertyType() default PropertyType.NONE;


}
