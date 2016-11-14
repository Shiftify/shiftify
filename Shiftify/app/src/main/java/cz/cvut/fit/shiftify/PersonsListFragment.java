package cz.cvut.fit.shiftify;


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

import cz.cvut.fit.shiftify.CustomPersonsListAdapter;

/**
 * Created by Vojta on 13.11.2016.
 */


public class PersonsListFragment extends ListFragment implements AdapterView.OnItemClickListener {


    /* Vojta:
    * Dummy pole pro seznam osob - pozdeji se budou tahat z DB.
    * Dale pomocne struktury pro ListLayout.
    * */
private String[] personsArray = { "Jan Novak", "Dan Horak", "Jiri Cernz", "Lukas Modry", "Standa Bily", "Ludek Hnedy", "Ludmila Nova",
        "Jonas Kalas", "Petr Salas",  "Laso Klas", "Oto Manas"};

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

        /*
        try {
            ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.persons, android.R.layout.simple_expandable_list_item_1);
            setListAdapter(adapter);
            getListView().setOnItemClickListener(this);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }*/

        adapter = new CustomPersonsListAdapter(getActivity(), personsArray, imageId);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);

        }



@Override
public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        Snackbar snack;
        snack = Snackbar.make(view, "Clicked item: " + getResources().getStringArray(R.array.persons)[position], Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#DD3A83FF"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snack.show();


        }
        }