package cz.cvut.fit.shiftify.data.DaoConverters;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import cz.cvut.fit.shiftify.data.Utilities;

/**
 * Created by ondra on 26.2.17.
 */

public class LocalDateToStringConverter implements PropertyConverter<LocalDate, String> {
    public static LocalDateToStringConverter INSTANCE = new LocalDateToStringConverter();

    @Override
    public LocalDate convertToEntityProperty(String databaseValue) {
        return Utilities.DATE_FORMATTER.parseLocalDate(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(LocalDate entityProperty) {
        return entityProperty.toString(Utilities.DATE_FORMATTER);
    }
}
