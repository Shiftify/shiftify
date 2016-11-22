package cz.cvut.fit.shiftify.data;

import android.app.Application;
import android.content.Context;

/**
 * Created by petr on 11/22/16.
 */

public class MyAppContext extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MyAppContext.sContext = getApplicationContext();
    }

    public static Context getsContext() {
        return sContext;
    }
}
