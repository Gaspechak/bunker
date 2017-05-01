package br.com.bunker.view;

import br.com.bunker.model.User;

public interface SignupView {
    User getUser();
    void showMessage(String message);
    void showDialog();
    void hideDialog();
    void navigateToMain();
}
