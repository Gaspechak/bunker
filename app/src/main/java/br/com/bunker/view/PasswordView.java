package br.com.bunker.view;

import br.com.bunker.model.Vault;

public interface PasswordView {
    Vault getVault();

    void showMessege(String messege);

    String getString(int resId);
}
