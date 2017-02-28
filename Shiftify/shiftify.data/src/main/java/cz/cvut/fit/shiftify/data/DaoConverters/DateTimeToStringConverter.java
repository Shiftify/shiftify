package cz.cvut.fit.shiftify.data.DaoConverters;

import org.greenrobot.greendao.converter.PropertyConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;

import cz.cvut.fit.shiftify.data.Utilities;

/**
 * Created by ondra on 26.2.17.
 */

public class DateTimeToStringConverter implements PropertyConverter<DateTime, String> {


    @Override
    public DateTime convertToEntityProperty(String databaseValue) {
        return Utilities.DATETIME_FORMATTER.parseDateTime(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(DateTime entityProperty) {
        return entityProperty.toString(Utilities.DATETIME_FORMATTER);
    }
}
