package br.com.bunker.presenter;

import com.google.firebase.auth.FirebaseAuth;
import com.orhanobut.hawk.Hawk;

import br.com.bunker.R;
import br.com.bunker.database.VaultDAO;
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
    }
}
