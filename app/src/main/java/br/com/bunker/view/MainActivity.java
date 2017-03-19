package br.com.bunker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.bunker.R;
import br.com.bunker.database.VaultDAO;
import br.com.bunker.model.Vault;
import br.com.bunker.presenter.MainPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private FirebaseRecyclerAdapter<Vault, VaultViewHolder> adapter;

    private MainPresenter presenter;

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

            presenter = new MainPresenter(this);

            rviewVaults.setHasFixedSize(true);

            LinearLayoutManager manager = new LinearLayoutManager(this);

            manager.setOrientation(LinearLayoutManager.VERTICAL);

            rviewVaults.setLayoutManager(manager);

            adapter = new FirebaseRecyclerAdapter<Vault, VaultViewHolder>(Vault.class, R.layout.view_holder_vault, VaultViewHolder.class, presenter.getReference()) {
                @Override
                protected void populateViewHolder(VaultViewHolder viewHolder, Vault model, int position) {
                    model.key = adapter.getRef(position).getKey();

                    final Vault modelRef = model;

                    viewHolder.setDescription(model.description);
                    viewHolder.setUrl(model.url);

                    viewHolder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, PasswordActivity.class);
                            i.putExtra("vault", modelRef);
                            startActivity(i);
                        }
                    });
                }
            };

            rviewVaults.setAdapter(adapter);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rviewVaults.getContext(), manager.getOrientation());
            rviewVaults.addItemDecoration(dividerItemDecoration);
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
