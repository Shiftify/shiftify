package cz.cvut.fit.shiftify;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Vector;

import cz.cvut.fit.shiftify.CustomPersonsListAdapter;
import cz.cvut.fit.shiftify.data.User;
import cz.cvut.fit.shiftify.data.UserManager;

/**
 * Created by Vojta on 13.11.2016.
 */


public class PersonsListFragment extends ListFragment implements AdapterView.OnItemClickListener {


    /* Vojta:
    * Dummy pole pro seznam osob - pozdeji se budou tahat z DB.
    * Dale pomocne struktury pro ListLayout.
    * */
private String[] personsArray;
private Vector<User> userVector;

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

private ListView personsList;
private ArrayAdapter arrayAdapter;

private CustomPersonsListAdapter adapter;
    /*-----------*/

@Nullable
@Override
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_persons, container, false);
        return view;
        }


@Override
public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

    UserManager userManager = new UserManager();
    userVector = new Vector<User>();

    try {
        userVector = userManager.users();

    }catch(Exception e){
        System.err.println("Nepovedlo se nacist uzivatele z DB.");
    }

    personsArray = new String[userVector.size()];
    makeArray(userVector);

    adapter = new CustomPersonsListAdapter(getActivity(), personsArray, imageId);
    setListAdapter(adapter);
    getListView().setOnItemClickListener(this);

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

@Override
public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        /*
        Snackbar snack;
        snack = Snackbar.make(view, "Clicked item: " + getResources().getStringArray(R.array.persons)[position], Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#DD3A83FF"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snack.show();*/

        Intent i = new Intent(PersonsListFragment.this.getActivity(),PersonDetailActivity.class);
        i.putExtra("userId",userVector.elementAt(position).getId());

        startActivity(i);

}
}