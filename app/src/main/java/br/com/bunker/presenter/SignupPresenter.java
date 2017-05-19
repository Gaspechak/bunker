package br.com.bunker.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.orhanobut.hawk.Hawk;

import br.com.bunker.helper.CredentialsCache;
import br.com.bunker.model.User;
import br.com.bunker.view.SignupView;

public class SignupPresenter {

    private SignupView signupView;

    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
    }

    public void signUp() {
        final User user = signupView.getUser();
        if (user.isValid()) {
            signupView.showDialog();
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        CredentialsCache.put(user.password + user.email + task.getResult().getUser().getUid());
                        changeUserName(user.name);
                    } else {
                        signupView.showMessage(task.getException().getMessage());
                        signupView.hideDialog();
                    }
                }
            });
        } else {
            signupView.showMessage("Todos os campos são obrigatórios e sua senha precisa ter no mínimo 6 caracteres.");
        }
    }

    private void changeUserName(String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();

        FirebaseAuth.getInstance().getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signupView.hideDialog();
                signupView.navigateToMain();
            }
        });
    }
}
