package cz.cvut.fit.shiftify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.Vector;

import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;

public class ShiftListFragment extends ListFragment implements AdapterView.OnItemClickListener{


    Vector<User> freeWorkers;
    Vector<User> nonfreeWorkers;
    UserManager userManager;
    CustomShiftListAdapter adapter;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        freeWorkers = new Vector<User>();
        userManager = new UserManager();

        //userManager.

        // nacist volny Users do ArrayList
        // nacist pracujici Users do Arraylist a seradit je

        adapter = new CustomShiftListAdapter(getActivity(), shiftsArray, userArray);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
