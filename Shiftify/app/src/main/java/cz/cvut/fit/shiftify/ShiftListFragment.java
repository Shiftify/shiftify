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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.cvut.fit.shiftify.data.managers.UserManager;
import cz.cvut.fit.shiftify.data.models.User;
import cz.cvut.fit.shiftify.utils.CalendarUtils;

public class ShiftListFragment extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    List<User> allWorkers;
    List<User> freeWorkers;
    UserManager userManager;
    CustomShiftListAdapter adapter;
    String[] personsArray;

    TextView headerDate;
    ImageButton btnCal;
    ImageButton btnArrLeft;
    ImageButton btnArrRight;
    Calendar cal;
    SimpleDateFormat dateFormat;

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
            R.drawable.face,
            R.drawable.icon_bar_example,
            R.drawable.icon_bar_example

    };

    /*
    *
    *    co predavat adapteru??
    *    vektor Useru a Shifts ? - ale jak je spojit dohromady
    *    nebo vektor dvojic User, String (string je ta nazev sichty) ?
    *
    */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shifts, container, false);

        btnCal = (ImageButton) view.findViewById(R.id.btn_cal);
        btnArrLeft = (ImageButton) view.findViewById(R.id.date_arrow_left);
        btnArrRight = (ImageButton) view.findViewById(R.id.date_arrow_right);

        btnCal.setOnClickListener(this);
        btnArrLeft.setOnClickListener(this);
        btnArrRight.setOnClickListener(this);

        return view;
    }


    private void getFreeWorkersArray() {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        loadWorkers();

        // why does this work??
        headerDate = (TextView) getActivity().findViewById(R.id.shift_list_header_date);
        initHeaderDate();

        adapter = new CustomShiftListAdapter(getActivity(), personsArray, imageId);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void loadWorkers() {

        freeWorkers = new ArrayList<>();
        userManager = new UserManager();

        UserManager userManager = new UserManager();
        allWorkers = new ArrayList<>();

        try {
            allWorkers = userManager.allUsers();

        } catch (Exception e) {
            System.err.println("Nepovedlo se nacist uzivatele z DB.");
        }

        personsArray = new String[allWorkers.size()];
        makeArray(allWorkers);

    }

    private void initHeaderDate() {

        cal = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String dateStr = dateFormat.format(cal.getTime());
        headerDate.setText(dateStr);
    }

    private void setHeaderDate(RelativeDayEnum dayOffset) {

        Date d;
        try {
            d = dateFormat.parse(headerDate.getText().toString());
            cal.setTime(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, dayOffset.getValue());
        String dateStr = dateFormat.format(cal.getTime());
        headerDate.setText(dateStr);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Intent intent = new Intent(getActivity(), PersonShiftsActivity.class);  //PersonScheduleActivity

        intent.putExtra("userId", allWorkers.get(position).getId());
        startActivity(intent);
    }

    private void makeArray(List<User> list) {

        int index = 0;
        String firstname, surname, nickname;

        for (User u :
                list) {

            firstname = u.getFirstName();
            surname = u.getSurname();
            nickname = (u.getNickname() == null ? " " : " \"" + u.getNickname() + "\" ");


            personsArray[index] = firstname + nickname + surname;
            index++;
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_cal:

                DialogFragment newFragment = new ShiftPlanDateDialog();
                newFragment.show(getActivity().getFragmentManager(), "datePicker");
                break;
            case R.id.date_arrow_left:
                // date --
                setHeaderDate(RelativeDayEnum.YESTERDAY);
                break;
            case R.id.date_arrow_right:
                // date ++
                setHeaderDate(RelativeDayEnum.TOMORROW);

                break;
        }
    }

    public void setSelectedDate(Calendar calendar) {
        headerDate.setText(CalendarUtils.calendarToDateString(calendar));
//        TODO dalsi veci
    }
}
