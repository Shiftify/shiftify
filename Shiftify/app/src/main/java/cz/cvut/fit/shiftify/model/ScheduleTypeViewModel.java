package cz.cvut.fit.shiftify.model;

import android.content.Intent;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Objects;

/**
 * Created by Ilia on 24-Apr-17.
 */

public class ScheduleTypeViewModel {
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> description = new ObservableField<>();

    public ScheduleTypeViewModel(String name, String description) {
        this.name.set(name);
        this.description.set(description);
    }

    public TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(name.get(), s.toString())){
                name.set(s.toString());
            }
        }
    };

    public TextWatcher descriptionWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable s) {
            if (!Objects.equals(description.get(), s.toString())){
                description.set(s.toString());
            }
        }
    };
}
