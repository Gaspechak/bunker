package br.com.bunker.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.hawk.Hawk;

import br.com.bunker.helper.CredentialsCache;
import br.com.bunker.view.LoginView;

public class LoginPresenter {

    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        this.loginView = loginView;
    }

    public void login(final String email, final String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loginView.showMainActivity();
                        CredentialsCache.put(password + email + task.getResult().getUser().getUid());
                    } else {
                        loginView.showMessage(task.getException().getMessage());
                    }
                }
            });
        }
    }
}
