package cz.cvut.fit.shiftify.model;

import android.database.Observable;
import android.databinding.ObservableField;
import android.text.Editable;
import android.text.TextWatcher;

import org.joda.time.LocalTime;

import java.util.Objects;

/**
 * Created by Ilia on 24-Apr-17.
 */

public class ScheduleShiftViewModel {
    private Long id;
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();
    public final ObservableField<LocalTime> from = new ObservableField<>();
    public final ObservableField<LocalTime> to = new ObservableField<>();
    public final ObservableField<String> dayOfCycle = new ObservableField<>();

    public ScheduleShiftViewModel(){
        this.name.set("");
        this.description.set("");
        this.from.set(LocalTime.now());
        this.to.set(LocalTime.now().plusHours(1));
        this.dayOfCycle.set("1");
    }

    public ScheduleShiftViewModel(String name, String description, LocalTime from, LocalTime to, String dayOfCycle){
        this.name.set(name);
        this.description.set(description);
        this.from.set(from);
        this.to.set(to);
        this.dayOfCycle.set(dayOfCycle);
    }

    public TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(name.get(), s.toString())){
                name.set(s.toString());
            }
        }
    };

    public TextWatcher descriptionWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(description.get(), s.toString())){
                description.set(s.toString());
            }
        }
    };

    public TextWatcher dayOfCycleWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(dayOfCycle.get(), s.toString())){
                dayOfCycle.set(s.toString());
            }
        }
    };


}
