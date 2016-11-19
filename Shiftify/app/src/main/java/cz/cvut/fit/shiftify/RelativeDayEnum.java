package cz.cvut.fit.shiftify;

/**
 * Created by Vojta on 19.11.2016.
 */

public enum RelativeDayEnum {
    YESTERDAY(-1), TODAY(0), TOMORROW(1);

    private int value;
    RelativeDayEnum(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
