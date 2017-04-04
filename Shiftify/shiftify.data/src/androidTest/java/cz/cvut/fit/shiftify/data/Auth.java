package cz.cvut.fit.shiftify.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import cz.cvut.fit.shiftify.firebase.FireBaseAuthenticator;
import cz.cvut.fit.shiftify.firebase.core.IAuthStateListener;

/**
 * Created by ondra on 4.4.17.
 */

@RunWith(AndroidJUnit4.class)
public class Auth {

    @Test
    public void init() throws Exception {
        FireBaseAuthenticator authenticator = new FireBaseAuthenticator(new IAuthStateListener() {
            @Override
            public void connected(FirebaseUser user) {
                //connected
            }

            @Override
            public void disconnected() {
                //disconnect
            }

            @Override
            public void connectionFailed(Exception e) {
                Assert.fail(e.toString());
            }
        });

        authenticator.login("test@shiftify.org", "test123");
        authenticator.close();
    }
}
