package cz.cvut.fit.shiftify.data.validator;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ondra on 28.2.17.
 *
 * Keeps all results from check
 */

public class ValidatorResult {
    /**
     * final state of the result
     */
    private ValidatorState finalState = ValidatorState.OK;

    /**
     * you may not want to log some results (e.g. successful validation)
     */
    private final ValidatorState minimumState;

    /**
     * stored messages
     */
    private Map<String, ValidatorMessage> messages = new LinkedHashMap<>();

    public ValidatorResult(ValidatorState minimumState) {
        this.minimumState = minimumState;
    }

    public void addMessage(ValidatorMessage message) {
        Comparator<ValidatorState> comp = ValidatorState.COMPARATOR;
        ValidatorState validatorState = message.getState();

        if (comp.compare(finalState, validatorState) < 0) {
            finalState = validatorState;
        }

        if (comp.compare(minimumState, validatorState) <= 0) {
            messages.put(message.getName(), message);
        }

    }

    public ValidatorState getFinalState() {
        return finalState;
    }

    public Map<String, ValidatorMessage> getMessages() {
        return messages;
    }
}
