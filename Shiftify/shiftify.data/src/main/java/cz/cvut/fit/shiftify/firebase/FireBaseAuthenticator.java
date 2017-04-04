package cz.cvut.fit.shiftify.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import cz.cvut.fit.shiftify.firebase.core.IAuthStateListener;

/**
 * Created by ondra on 3.4.17.
 */

public class FireBaseAuthenticator implements AutoCloseable {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener stateListener;
    private IAuthStateListener authStateListener;

    public FireBaseAuthenticator(IAuthStateListener authStateListener) {
        auth = FirebaseAuth.getInstance();
        stateListener = new ShiftifyAuthStateListener(authStateListener);
        auth.addAuthStateListener(stateListener);

        this.authStateListener = authStateListener;
    }

    public void login(String mail, String password) {
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            IllegalStateException e = new IllegalStateException("Momentálně je přihlášen jiný uživatel.");
            authStateListener.connectionFailed(e);
            return;
        }

        auth.signInWithEmailAndPassword(mail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                authStateListener.connected(authResult.getUser());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                authStateListener.connectionFailed(e);
            }
        });

    }

    public void disconnect() {
        auth.signOut();
    }


    @Override
    public void close() throws Exception {
        disconnect();
        auth.removeAuthStateListener(stateListener);
    }
}
