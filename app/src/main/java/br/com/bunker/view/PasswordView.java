package br.com.bunker.view;

import br.com.bunker.helper.ConfirmCallback;
import br.com.bunker.model.Vault;

public interface PasswordView {
    Vault getVault();

    void showMessege(String messege, Boolean finishActivity);

    void showConfirmDialog(ConfirmCallback callback);

    String getString(int resId);
}
