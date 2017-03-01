package cz.cvut.fit.shiftify.data.DaoConverters;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.joda.time.Period;

import cz.cvut.fit.shiftify.data.Utilities;

/**
 * Created by lukas on 01.03.2017.
 */

public class PeriodToStringConverter implements PropertyConverter<Period, String> {
    public static LocalTimeToStringConverter INSTANCE = new LocalTimeToStringConverter();

    @Override
    public Period convertToEntityProperty(String databaseValue) {
        return Utilities.PERIOD_FORMATTER.parsePeriod(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(Period entityProperty) {
        return entityProperty.toString(Utilities.PERIOD_FORMATTER);
    }
}
