package cz.cvut.fit.shiftify;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vojta on 17.11.2016.
 */

public class CustomShiftListAdapter extends ArrayAdapter<String> {

    private final Activity context;

    /*
    *
    *predelat na adapter sichet !
    *
    *
    * */


    public CustomShiftListAdapter(Activity context, String[] persons, Integer[] imageId) {
        super(context, R.layout.persons_list_single, persons);
        this.context = context;
        this.personsArray = persons;
        this.imageId = imageId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.persons_list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.person_name);
        txtTitle.setPadding(50,0,0,0);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.person_image);
        txtTitle.setText(personsArray[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
    }
}
