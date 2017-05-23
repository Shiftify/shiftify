package cz.cvut.fit.shiftify.schedules;

import android.databinding.ObservableArrayList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.cvut.fit.shiftify.R;
import cz.cvut.fit.shiftify.data.managers.ScheduleTypeManager;
import cz.cvut.fit.shiftify.data.models.ScheduleShift;
import cz.cvut.fit.shiftify.data.models.ScheduleType;
import cz.cvut.fit.shiftify.main.ScheduleTypeListFragment;
import cz.cvut.fit.shiftify.model.ScheduleShiftViewModel;
import cz.cvut.fit.shiftify.model.ScheduleTypeViewModel;
import cz.cvut.fit.shiftify.utils.ToolbarUtils;

public class ScheduleTypeEditActivity extends AppCompatActivity {

    private static final String SHIFT_EDIT_FRAGMENT = "shift_edit_fragment";
    private static final String TYPE_EDIT_FRAGMENT = "type_edit_fragment";

    protected ObservableArrayList<ScheduleShiftViewModel> mShifts;
    protected ScheduleTypeViewModel mScheduleType;
    protected Long mTypeId;
    protected ScheduleTypeManager mManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_type_edit);
        ToolbarUtils.setToolbar(this);
        mTypeId = getIntent().getLongExtra(ScheduleTypeListFragment.SCHEDULE_TYPE_ID, -1);
        initScheduleType();
        initTypeEditFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void initScheduleType() {
        mShifts = new ObservableArrayList<>();
        mScheduleType = new ScheduleTypeViewModel("", "");
        mManager = new ScheduleTypeManager();
        //Edit schedule type requested
        if (mTypeId != -1) {
            try {
                ScheduleType typeToEdit = mManager.scheduleType(mTypeId);
                createModelView(typeToEdit);
            } catch (Exception e) {
                Toast toast = Toast.makeText(this, getString(R.string.error_while_fetching_schedule_types), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private void createModelView(ScheduleType type){
        mScheduleType.name.set(type.getName());
        mScheduleType.description.set(type.getDescription());

        List<ScheduleShift> shifts = type.getShifts();
        Collections.sort(shifts, new Comparator<ScheduleShift>() {
            @Override
            public int compare(ScheduleShift o1, ScheduleShift o2) {
                return o1.getDayOfScheduleCycle() - o2.getDayOfScheduleCycle();
            }
        });
        for (ScheduleShift shift : shifts) {
            ScheduleShiftViewModel shiftVM = new ScheduleShiftViewModel(
                    shift.getName(),
                    shift.getDescription(),
                    shift.getFrom(),
                    shift.getFrom().plus(shift.getDuration()),
                    shift.getDayOfScheduleCycle().toString()
            );
            mShifts.add(shiftVM);
        }
    }

    private void initTypeEditFragment() {
        ScheduleTypeEditFragment typeEditFragment = ScheduleTypeEditFragment.newInstance();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, typeEditFragment, TYPE_EDIT_FRAGMENT)
                .commit();
    }

    private void showEditFragment(int position) {
        ScheduleShiftEditFragment shiftEditFragment = ScheduleShiftEditFragment.newInstance(position);
        getFragmentManager().beginTransaction()
                .addToBackStack(SHIFT_EDIT_FRAGMENT)
                .replace(R.id.fragment_container, shiftEditFragment, SHIFT_EDIT_FRAGMENT)
                .commit();
    }

    protected void onEditShiftClick(int position) {
        showEditFragment(position);
    }

    protected void onAddShiftClick() {
        showEditFragment(-1);
    }

    protected void setToolbarText(String text) {
        getSupportActionBar().setTitle(text);
    }
}
