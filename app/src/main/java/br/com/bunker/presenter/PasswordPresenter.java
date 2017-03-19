package br.com.bunker.presenter;

import br.com.bunker.R;
import br.com.bunker.database.VaultDAO;
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

        if (vault.key == null) {
            db.insert(passwordView.getVault());
        } else {
            db.update(vault);
        }

        passwordView.showMessege(passwordView.getString(R.string.msg_success_saved), true);
    }
}
