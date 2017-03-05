package cz.cvut.fit.shiftify.exceptions;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.LocalTime;
import org.joda.time.Period;

import cz.cvut.fit.shiftify.data.models.ExceptionInSchedule;
import cz.cvut.fit.shiftify.data.models.ExceptionShift;


/**
 * Created by Ilia on 05-Mar-17.
 */

public class ExceptionShiftParcelable implements Parcelable {

    private Long id;
    private LocalTime from;
    private Period duration;
    private Long exceptionInScheduleId;
    private Boolean isWorking;
    private String description;
    private ExceptionInSchedule exceptionInSchedule;

    public ExceptionShiftParcelable(ExceptionShift exceptionShift){
        id = exceptionShift.getId();
        from = exceptionShift.getFrom();
        duration = exceptionShift.getDuration();
        exceptionInScheduleId = exceptionShift.getExceptionInScheduleId();
        isWorking = exceptionShift.getIsWorking();
        description = exceptionShift.getDescription();
        exceptionInSchedule = exceptionShift.getExceptionInSchedule();
    }

    public static final Parcelable.Creator<ExceptionShiftParcelable> CREATOR
            = new Parcelable.Creator<ExceptionShiftParcelable>(){

        @Override
        public ExceptionShiftParcelable createFromParcel(Parcel source) {
            return new ExceptionShiftParcelable(source);
        }

        @Override
        public ExceptionShiftParcelable[] newArray(int size) {
            return new ExceptionShiftParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //note that you should put and pull the values from Parcel in the same order
        dest.writeValue(id);
        dest.writeValue(from);
        dest.writeValue(duration);
        dest.writeValue(exceptionInScheduleId);
        dest.writeValue(isWorking);
        dest.writeValue(description);
        dest.writeValue(exceptionInSchedule);
    }

    private ExceptionShiftParcelable(Parcel source){
        //note that you should put and pull the values from Parcel in the same order
        id=(Long)source.readValue(null);
        from=(LocalTime)source.readValue(null);
        duration=(Period)source.readValue(null);
        exceptionInScheduleId=(Long)source.readValue(null);
        isWorking=(Boolean)source.readValue(null);
        description=(String)source.readValue(null);
        exceptionInSchedule=(ExceptionInSchedule)source.readValue(null);
    }
}
