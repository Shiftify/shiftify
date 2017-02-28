package cz.cvut.fit.shiftify.data.validator;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by ondra on 28.2.17.
 */

public class EntityValidatorTest {
    private static String STRING_VALUE = "aaa";
    private static boolean BOOLEAN_VALUE = true;
    private static String LONG_METHOD_NAME = "correctWithReaaaaaaaaaaaaaaaalyLongMethodNameOfCourse";

    @Test(expected = ReflectiveOperationException.class)
    public void testWrongEntity() throws Exception {
        EntityValidator.validateWithAllMessages(new WrongEntity());
        fail("The entity has annotation placed on wrong place.");
    }

    @Test
    public void testWithoutValidation() throws Exception {
        ValidatorResult result = EntityValidator.validateWithAllMessages(new Object());

        assertTrue("There should be no results.", result.getMessages().isEmpty());
        assertTrue("Final state should be OK.", result.getFinalState() == ValidatorState.OK);
    }

    @Test
    public void testSimpleEntityInAllMode() throws Exception {
        ValidatorResult result = EntityValidator.validateWithAllMessages(new SimpleEntity());

        assertTrue("Final state should be OK", result.getFinalState() == ValidatorState.OK);

        Map<String, ValidatorMessage> resultMap = result.getMessages();

        assertTrue("There should be one result.", resultMap.size() == 1);
        assertTrue("Result map should contain key 'value'", resultMap.containsKey("value"));

        ValidatorMessage message = resultMap.get("value");
        assertTrue(message.getName().equals("value"));
        assertTrue(message.getPropertyType() == PropertyType.NONE);
        assertTrue(message.getState() == ValidatorState.OK);
        assertTrue(STRING_VALUE.equals(message.getValue()));
    }

    @Test
    public void testSimpleEntityInNotOkMode() throws Exception {
        ValidatorResult result = EntityValidator.validateWithoutOkMessages(new SimpleEntity());

        assertTrue("There should be no results.", result.getMessages().isEmpty());
        assertTrue("Final state should be OK.", result.getFinalState() == ValidatorState.OK);
    }

    @Test
    public void testSimpleEntityInErrorMode() throws Exception {
        ValidatorResult result = EntityValidator.validateWithErrorsOnly(new SimpleEntity());

        assertTrue("There should be no results.", result.getMessages().isEmpty());
        assertTrue("Final state should be OK.", result.getFinalState() == ValidatorState.OK);
    }

    @Test(expected = ReflectiveOperationException.class)
    public void testWrongBooleanEntity() throws Exception {
        EntityValidator.validateWithErrorsOnly(new WrongBooleanEntity());

        fail("Should fail - incorrectly named getter.");
    }

    @Test
    public void testSimpleBooleanEntityAndPropertyNames() throws Exception {
        ValidatorResult result = EntityValidator.validateWithAllMessages(new SimpleBooleanEntity());

        assertTrue("Final state should be OK", result.getFinalState() == ValidatorState.OK);

        Map<String, ValidatorMessage> resultMap = result.getMessages();

        assertTrue("There should be one result.", resultMap.size() == 1);
        assertTrue("Result map should contain key '" + LONG_METHOD_NAME + "'", resultMap.containsKey(LONG_METHOD_NAME));

        ValidatorMessage message = resultMap.get(LONG_METHOD_NAME);
        assertTrue(message.getName().equals(LONG_METHOD_NAME));
        assertTrue(message.getPropertyType() == PropertyType.NONE);
        assertTrue(message.getState() == ValidatorState.OK);
        assertTrue((Boolean) message.getValue() == BOOLEAN_VALUE);
    }

    @Test
    public void testComplicatedEntity() throws Exception {
        ValidatorResult result = EntityValidator.validateWithAllMessages(new ComplicatedEntity());

        assertTrue("Final state should be OK", result.getFinalState() == ValidatorState.OK);

        Map<String, ValidatorMessage> resultMap = result.getMessages();

        assertTrue("There should be two results.", resultMap.size() == 2);
        assertTrue("Result map should contain key 'value'", resultMap.containsKey("value"));

        ValidatorMessage message = resultMap.get("value");
        assertTrue(message.getName().equals("value"));
        assertTrue(message.getPropertyType() == PropertyType.NONE);
        assertTrue(message.getState() == ValidatorState.OK);
        assertTrue(STRING_VALUE.equals(message.getValue()));

        assertTrue("Result map should contain key 'correct'", resultMap.containsKey("correct"));

        ValidatorMessage correctMsg = resultMap.get("correct");
        assertTrue(correctMsg.getName().equals("correct"));
        assertTrue(correctMsg.getPropertyType() == PropertyType.NONE);
        assertTrue(correctMsg.getState() == ValidatorState.OK);
        assertTrue((Boolean) correctMsg.getValue() == BOOLEAN_VALUE);
    }

    public static class ComplicatedEntity {
        @ValidateProperty(propertyType = PropertyType.NONE)
        public String getValue() {
            return STRING_VALUE;
        }

        @ValidateProperty(propertyType = PropertyType.NONE)
        public boolean isCorrect() {
            return BOOLEAN_VALUE;
        }

        public void publicMethod() {
            privateMethod();
        }

        private void privateMethod() {
            //Do nothing
        }
    }

    public static class WrongBooleanEntity {
        @ValidateProperty(propertyType = PropertyType.NONE)
        public boolean getValue() {
            return BOOLEAN_VALUE;
        }
    }

    public static class SimpleBooleanEntity {
        @ValidateProperty(propertyType = PropertyType.NONE)
        public boolean isCorrectWithReaaaaaaaaaaaaaaaalyLongMethodNameOfCourse() {
            return BOOLEAN_VALUE;
        }
    }

    public static class SimpleEntity {
        @ValidateProperty(propertyType = PropertyType.NONE)
        public String getValue() {
            return STRING_VALUE;
        }

        public void setValue() {
            //Do nothing - this is method without annotation
        }
    }

    public static class WrongEntity {
        @ValidateProperty(propertyType = PropertyType.NONE)
        public String returnValue() {
            return STRING_VALUE;
        }
    }
}
