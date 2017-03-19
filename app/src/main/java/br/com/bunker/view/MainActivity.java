package br.com.bunker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.bunker.R;
import br.com.bunker.database.VaultDAO;
import br.com.bunker.model.Vault;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private FirebaseRecyclerAdapter<Vault, VaultViewHolder> adapter;

    private VaultDAO db;

    @BindView(R.id.rview_vaults)
    RecyclerView rviewVaults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);

            db = new VaultDAO();

            rviewVaults.setHasFixedSize(true);
            rviewVaults.setLayoutManager(new LinearLayoutManager(this));

            adapter = new FirebaseRecyclerAdapter<Vault, VaultViewHolder>(Vault.class, R.layout.view_holder_vault, VaultViewHolder.class, db.getReference()) {
                @Override
                protected void populateViewHolder(VaultViewHolder viewHolder, Vault model, int position) {
                    viewHolder.setDescription(model.description);
                }
            };

            rviewVaults.setAdapter(adapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter.cleanup();
    }

    @OnClick(R.id.fab_add)
    public void OnClickAdd() {
        startActivity(new Intent(this, PasswordActivity.class));
    }
}
