package cz.cvut.fit.shiftify.data.DaoConverters;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.joda.time.LocalTime;

import cz.cvut.fit.shiftify.data.Utilities;

/**
 * Created by ondra on 26.2.17.
 */

public class LocalTimeToStringConverter implements PropertyConverter<LocalTime, String> {
    public static LocalTimeToStringConverter INSTANCE = new LocalTimeToStringConverter();

    @Override
    public LocalTime convertToEntityProperty(String databaseValue) {
        return Utilities.TIME_FORMATTER.parseLocalTime(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(LocalTime entityProperty) {
        return entityProperty.toString(Utilities.TIME_FORMATTER);
    }
}