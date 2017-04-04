package cz.cvut.fit.shiftify.firebase.core;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by ondra on 3.4.17.
 */

public interface IAuthStateListener {

    void connected(FirebaseUser user);

    void disconnected();

    void connectionFailed(Exception e);
}
