package cz.cvut.fit.shiftify.utils;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import cz.cvut.fit.shiftify.R;

/**
 * Created by petr on 12/6/16.
 */

public class ToolbarUtils {

    public static void setToolbar(AppCompatActivity activity) {
        setToolbar(activity, null);
    }

    public static void setToolbar(AppCompatActivity activity, Integer stringResId) {
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        if (stringResId != null) {
            toolbar.setTitle(activity.getString(stringResId));
        }
        setToolbar(toolbar, activity);
    }

    private static void setToolbar(Toolbar toolbar, AppCompatActivity activity) {
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
