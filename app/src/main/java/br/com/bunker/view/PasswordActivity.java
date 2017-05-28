package br.com.bunker.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import java.math.BigInteger;
import java.security.SecureRandom;

import br.com.bunker.R;
import br.com.bunker.helper.ConfirmCallback;
import br.com.bunker.helper.Encryptor;
import br.com.bunker.model.Vault;
import br.com.bunker.presenter.PasswordPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PasswordActivity extends AppCompatActivity implements PasswordView {

    private PasswordPresenter presenter;

    private Vault vault;
    //region fields

    @BindView(R.id.input_description)
    TextInputEditText inputDescription;

    @BindView(R.id.input_url)
    TextInputEditText inputURL;

    @BindView(R.id.input_username)
    TextInputEditText inputUserName;

    @BindView(R.id.input_password)
    TextInputEditText inputPassword;

    @BindView(R.id.input_note)
    TextInputEditText inputNote;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);

        vault = getIntent().getParcelableExtra("vault");

        if (vault != null) {
            setVault();
        }

        Vault cacheVault = vault != null ? vault : getVault();

        if (savedInstanceState == null) savedInstanceState = new Bundle();

        savedInstanceState.putParcelable("vault", cacheVault);

        presenter = new PasswordPresenter(this);
    }

    public void setVault() {
        inputDescription.setText(vault.description);
        inputURL.setText(vault.url);
        inputUserName.setText(vault.username);
        try {
            inputPassword.setText(Encryptor.decrypt(vault.password, Hawk.get("pwd").toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        inputNote.setText(vault.note);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        vault = savedInstanceState.getParcelable("vault");
        if (vault != null) {
            setVault();
        }
    }

    @Override
    public Vault getVault() {
        Vault v;

        if (vault != null) {
            v = vault;
        } else {
            v = new Vault();
        }

        v.description = inputDescription.getText().toString();
        v.url = inputURL.getText().toString();
        v.username = inputUserName.getText().toString();
        v.password = inputPassword.getText().toString();
        v.note = inputNote.getText().toString();

        return v;
    }

    @Override
    public void showMessege(String messege, Boolean finishActivity) {
        if (finishActivity) {
            finish();
        }
        Toast.makeText(this, messege, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showConfirmDialog(final ConfirmCallback callback) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        callback.OnDialogClose(true);
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        callback.OnDialogClose(false);
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Você tem certeza?").setPositiveButton("Sim", dialogClickListener).setNegativeButton("Não", dialogClickListener).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.password_menu, menu);
        if (vault == null) {
            menu.removeItem(R.id.action_delete);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Hawk.put("isAuthValid", true);

        if (id == R.id.action_save) {
            presenter.save();
        } else if (id == R.id.action_delete) {
            if (vault != null) presenter.delete();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Hawk.put("isAuthValid", true);
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        Object obj = Hawk.get("isAuthValid");
        if (obj == null || obj.equals(false)) {
            Intent i = new Intent(this, LockActivity.class);
            startActivity(i);
            moveTaskToBack(false);
        }
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Hawk.put("isAuthValid", false);
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @OnClick(R.id.random)
    public void OnClickRandom(){
        inputPassword.setText(new BigInteger(130, new SecureRandom()).toString(32));
    }
}
