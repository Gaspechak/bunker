package br.com.bunker.presenter;

import com.orhanobut.hawk.Hawk;

import br.com.bunker.R;
import br.com.bunker.database.VaultDAO;
import br.com.bunker.helper.ConfirmCallback;
import br.com.bunker.helper.Encryptor;
import br.com.bunker.model.Vault;
import br.com.bunker.view.PasswordView;

public class PasswordPresenter {

    private PasswordView passwordView;
    private VaultDAO db;

    public PasswordPresenter(PasswordView passwordView) {
        this.passwordView = passwordView;
        db = new VaultDAO();
    }

    public void save() {
        Vault vault = passwordView.getVault();

        if (isValid(vault)) {
            try {

                vault.password = Encryptor.encrypt(vault.password, Hawk.get("pwd").toString());

                if (vault.key == null) {
                    db.insert(vault);
                } else {
                    db.update(vault);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            passwordView.showMessege(passwordView.getString(R.string.msg_success_saved), true);
        } else {
            passwordView.showMessege(passwordView.getString(R.string.msg_failed_save), false);
        }
    }

    private boolean isValid(Vault vault) {
        return vault != null && vault.description.length() > 0 && vault.url.length() > 0 && vault.password.length() > 0 && vault.username.length() > 0;
    }

    public void delete() {
        passwordView.showConfirmDialog(new ConfirmCallback() {
            @Override
            public void OnDialogClose(Boolean result) {
                if (result) db.remove(passwordView.getVault().key);
            }
        });
    }
}
