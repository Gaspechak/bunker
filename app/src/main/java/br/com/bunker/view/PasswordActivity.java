package br.com.bunker.view;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.bunker.R;
import br.com.bunker.model.Vault;
import br.com.bunker.presenter.PasswordPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PasswordActivity extends AppCompatActivity implements PasswordView {

    private PasswordPresenter presenter;

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

        presenter = new PasswordPresenter(this);
    }

    @Override
    public Vault getVault() {
        Vault v = new Vault();

        v.description = inputDescription.getText().toString();
        v.url = inputURL.getText().toString();
        v.username = inputUserName.getText().toString();
        v.password = inputPassword.getText().toString();
        v.note = inputNote.getText().toString();


        return v;
    }

    @Override
    public void showMessege(String messege) {
        Toast.makeText(this, messege, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.password_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            presenter.save();
        }

        return super.onOptionsItemSelected(item);
    }
}
