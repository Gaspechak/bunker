package br.com.bunker.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.bunker.R;
import br.com.bunker.model.User;
import br.com.bunker.presenter.SignupPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements SignupView {

    private SignupPresenter presenter;

    @BindView(R.id.input_name)
    TextInputEditText txtName;

    @BindView(R.id.input_email)
    TextInputEditText txtEmail;

    @BindView(R.id.input_password)
    TextInputEditText txtPassword;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        presenter = new SignupPresenter(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.status_connecting));
        progressDialog.setCancelable(false);
    }

    @Override
    public User getUser() {
        return new User(txtName.getText().toString(), txtEmail.getText().toString(), txtPassword.getText().toString());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void hideDialog() {
        progressDialog.hide();
    }

    @Override
    public void navigateToMain() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.password_menu, menu);
        menu.removeItem(R.id.action_delete);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_save) {
            presenter.signUp();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
