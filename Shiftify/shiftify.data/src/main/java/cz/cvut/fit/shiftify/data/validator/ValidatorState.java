package cz.cvut.fit.shiftify.data.validator;

import java.util.Comparator;

/**
 * Created by ondra on 28.2.17.
 *
 */
public enum ValidatorState {
    OK(1), WARNING(2), ERROR(3);

    private int value;
    public static Comparator<ValidatorState> COMPARATOR = new ValidatorComparator();

    ValidatorState(int value) {
        this.value = value;
    }

    private static class ValidatorComparator implements Comparator<ValidatorState> {

        @Override
        public int compare(ValidatorState o1, ValidatorState o2) {
            if (o1.value == o2.value) {
                return 0;
            }

            return (o1.value < o2.value) ? -1 : 1;
        }
    }
}
