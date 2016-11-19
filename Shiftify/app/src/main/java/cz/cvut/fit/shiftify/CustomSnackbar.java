package cz.cvut.fit.shiftify;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Vojta on 19.11.2016.
 */

public class CustomSnackbar{

    private View view;
    Snackbar snack;
    String text;

    public CustomSnackbar(View v,String t) {
        this.text = t;
        this.view = v;
    }

    public void show(){
        snack = Snackbar.make(view, text , Snackbar.LENGTH_SHORT);
        View snackBarView = snack.getView();
        snackBarView.setBackgroundColor(Color.parseColor("#DD3A83FF"));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setTypeface(null, Typeface.BOLD);
        snack.show();
    }
}

        /*

        */