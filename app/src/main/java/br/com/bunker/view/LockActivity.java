package br.com.bunker.view;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.orhanobut.hawk.Hawk;

import br.com.bunker.R;
import br.com.bunker.helper.CredentialsCache;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LockActivity extends AppCompatActivity {

    @BindView(R.id.input_password)
    TextInputEditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_unlock)
    public void onClickUnlock() {
        boolean isValid = CredentialsCache.validate(inputPassword.getText().toString());
        if (isValid) {
            Bundle extras = getIntent().getExtras();
            Hawk.put("isAuthValid", true);
            if(extras == null){
                finish();
            } else{
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }

        } else {
            Hawk.put("isAuthValid", false);
            Toast.makeText(this, "A autênticação falhou, tente novamente", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
