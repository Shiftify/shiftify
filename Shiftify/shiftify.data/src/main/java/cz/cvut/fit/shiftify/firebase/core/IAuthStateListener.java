package cz.cvut.fit.shiftify.firebase.core;

import com.google.firebase.auth.FirebaseUser;

/**
 * This is useful for listening on changes when user is connected / disconnected from Firebase
 *
 * Note the these methods are called asynchronously
 */
public interface IAuthStateListener {

    /**
     * @param user - user info
     */
    void connected(FirebaseUser user);

    void disconnected();

    /**
     * @param e - reason why connection failed
     */
    void connectionFailed(Exception e);
}
