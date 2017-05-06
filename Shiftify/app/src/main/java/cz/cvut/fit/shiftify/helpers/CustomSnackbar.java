package cz.cvut.fit.shiftify.helpers;

import android.app.Activity;
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
    private Snackbar snack;
    private String text;

    public CustomSnackbar(View v,String t) {
        this.text = t;
        this.view = v;
    }

    public CustomSnackbar(Activity activity, int resourceId) {
        this.text = activity.getString(resourceId);
        this.view = activity.findViewById(android.R.id.content);
    }

    public CustomSnackbar(Activity activity, String text) {
        this.text = text;
        this.view = activity.findViewById(android.R.id.content);
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