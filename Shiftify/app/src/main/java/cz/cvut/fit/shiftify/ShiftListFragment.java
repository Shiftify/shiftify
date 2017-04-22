package cz.cvut.fit.shiftify;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import cz.cvut.fit.shiftify.data.WorkDay;
import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.helpers.UserWorkdayWrapper;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

public class ShiftListFragment extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    ArrayList<UserWorkdayWrapper> userShiftsList;
    UserManager userManager;
    CustomShiftListAdapter adapter;
    String[] personsArray;

    TextView headerDate;
    ImageButton btnCal;
    ImageButton btnArrLeft;
    ImageButton btnArrRight;
    LocalDate cal;

    Integer[] imageId = {
            R.drawable.face,
            R.drawable.icon_bar_example,
            R.drawable.face_obama,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.face_obama,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.face_obama,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shifts, container, false);

        btnCal = (ImageButton) view.findViewById(R.id.btn_cal);
        btnArrLeft = (ImageButton) view.findViewById(R.id.week_arrow_left);
        btnArrRight = (ImageButton) view.findViewById(R.id.date_arrow_right);

        btnCal.setOnClickListener(this);
        btnArrLeft.setOnClickListener(this);
        btnArrRight.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadWorkersAndShifts();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        userManager = new UserManager();

        // why does this work??
        headerDate = (TextView) getActivity().findViewById(R.id.first_week_day);
        initHeaderDate();

        loadWorkersAndShifts();

        // TODO sortWorkersByShifts(); // ...tricky shit


        adapter = new CustomShiftListAdapter(getActivity(), userShiftsList, imageId);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


    private void loadWorkersAndShifts() {

        List<User> allWorkers = new ArrayList<>();
        userShiftsList = new ArrayList<>();
        WorkDay dayShifts;

        try {
            allWorkers = userManager.allUsers();

        } catch (Exception e) {
            System.err.println("Nepovedlo se nacist uzivatele z DB.");
        }


        for (User u : allWorkers
             ) {

            try {
                dayShifts = userManager.shiftsForDate( u.getId(), cal );
                userShiftsList.add(new UserWorkdayWrapper(u,dayShifts));

            } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getMessage().toString());
            }

        }
        adapter = new CustomShiftListAdapter(getActivity(), userShiftsList, imageId);
        setListAdapter(adapter);
    }

    private void initHeaderDate() {

        cal = LocalDate.now();
        String dateStr = cal.toString(CalendarUtils.JODA_DATE_FORMATTER);
        headerDate.setText(dateStr);
    }

    private void setHeaderDate() {

        String dateStr = cal.toString(CalendarUtils.JODA_DATE_FORMATTER);
        headerDate.setText(dateStr);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Intent intent = new Intent(getActivity(), PersonDetailActivity.class);

        intent.putExtra("userId", userShiftsList.get(position).getUser().getId());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_cal:
                //creating the new dialog fragment
                DialogFragment newFragment = new DatePickDialog();
                Bundle selectedDateBundle = new Bundle();
                //preparing the bundle for creating datePicker with selected date
                selectedDateBundle.putString(DatePickDialog.SELECTED_DATE, cal.toString(CalendarUtils.JODA_DATE_FORMATTER));

                //passing the bundle to the fragment
                newFragment.setArguments(selectedDateBundle);
                //showing the datePicker
                newFragment.show(getActivity().getFragmentManager(), DatePickDialog.DATE_PICKER_TAG);
                break;
            case R.id.week_arrow_left:
                // date --
                cal = cal.minusDays(1);
                setHeaderDate();
                loadWorkersAndShifts();
                break;
            case R.id.date_arrow_right:
                // date ++
                cal = cal.plusDays(1);
                setHeaderDate();
                loadWorkersAndShifts();
                break;
        }
    }

    public void setSelectedDate(LocalDate date) {
        String newDate = date.toString(CalendarUtils.JODA_DATE_FORMATTER);

        cal = new LocalDate(date);
        headerDate.setText(newDate);

        loadWorkersAndShifts();
    }
}
