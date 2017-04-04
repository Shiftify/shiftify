package cz.cvut.fit.shiftify.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;

import cz.cvut.fit.shiftify.firebase.FireBaseAuthenticator;
import cz.cvut.fit.shiftify.firebase.core.IAuthStateListener;

/**
 * Created by ondra on 4.4.17.
 */

@RunWith(AndroidJUnit4.class)
public class Auth {
    private static final String APP_TAG = "ShiftifyTest";

    @Test
    public void initTest() throws Exception {
        final LoggedIn in = new LoggedIn();

        FireBaseAuthenticator authenticator = new FireBaseAuthenticator(new IAuthStateListener() {

            @Override
            public void connected(FirebaseUser user) {
                in.setLogged(true);

                Log.i(APP_TAG, "connected user "+ user.getEmail());
            }

            @Override
            public void disconnected() {
                Log.i(APP_TAG, "disconnected");
            }

            @Override
            public void connectionFailed(Exception e) {
                in.setException(e);

                Log.i(APP_TAG, "connection failed", e);
            }
        });

        authenticator.login("test@shiftify.org", "test123");

        Thread.sleep(1000 * 3);
        authenticator.close();

        Assert.assertTrue("Cannot log in: " + in.getException(), in.isLogged());

    }

    private static class LoggedIn {
        private boolean logged = false;
        private Exception exception = null;

        public boolean isLogged() {
            return logged;
        }

        public void setLogged(boolean logged) {
            this.logged = logged;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }
    }
}
