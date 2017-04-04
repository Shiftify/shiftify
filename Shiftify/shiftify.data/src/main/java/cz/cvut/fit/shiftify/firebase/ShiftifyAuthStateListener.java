package cz.cvut.fit.shiftify.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cz.cvut.fit.shiftify.firebase.core.IAuthStateListener;

/**
 * Created by ondra on 3.4.17.
 */

public class ShiftifyAuthStateListener implements FirebaseAuth.AuthStateListener {
    private final IAuthStateListener authStateListener;

    public ShiftifyAuthStateListener(IAuthStateListener authStateListener) {
        this.authStateListener = authStateListener;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            authStateListener.connected(user);
        } else {
            authStateListener.disconnected();
        }
    }
}
