package cz.cvut.fit.shiftify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;

public class ShiftListFragment extends ListFragment implements AdapterView.OnItemClickListener {


    Vector<User> allWorkers;
    Vector<User> freeWorkers;
    Vector<User> nonfreeWorkers;
    UserManager userManager;
    CustomShiftListAdapter adapter;
    String [] personsArray;

    TextView headerDate;

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

    ImageButton calBtn;

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
    return view;
}


    private void getFreeWorkersArray(){


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadWorkers();
        setHeaderDate();

        adapter = new CustomShiftListAdapter(getActivity(), personsArray, imageId);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private void loadWorkers(){

        freeWorkers = new Vector<User>();
        userManager = new UserManager();

        UserManager userManager = new UserManager();
        allWorkers = new Vector<User>();

        try {
            allWorkers = userManager.users();

        }catch(Exception e){
            System.err.println("Nepovedlo se nacist uzivatele z DB.");
        }

        personsArray = new String[allWorkers.size()];
        makeArray(allWorkers);

    }

    private void setHeaderDate(){

        headerDate = (TextView)getActivity().findViewById(R.id.shift_list_header_date);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        headerDate.setText(dateFormat.format(date).toString());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private void makeArray(Vector<User> vector){

        int index = 0;
        String firstname,surname,nickname;

        for (User u:
                vector) {

            firstname = u.getFirstName();
            surname = u.getSurname();
            nickname = (u.getNickname()== null ? "" : "\"" + u.getNickname() + "\"");


            personsArray[index] = firstname + " " + nickname + " " + surname;
            index++;
        }

    }
}
