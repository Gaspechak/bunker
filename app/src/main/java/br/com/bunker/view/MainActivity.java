package br.com.bunker.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.orhanobut.hawk.Hawk;

import br.com.bunker.R;
import br.com.bunker.helper.RecyclerAdapter;
import br.com.bunker.helper.VaultAdapter;
import br.com.bunker.model.Vault;
import br.com.bunker.presenter.MainPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView {

    private VaultAdapter adapter;

    private MainPresenter presenter;

    @BindView(R.id.rview_vaults)
    RecyclerView rviewVaults;

    private ChildEventListener listener;

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

            adapter = new VaultAdapter(this, new RecyclerAdapter.OnViewHolderClick() {
                @Override
                public void onClick(View view, int position) {
                    Intent i = new Intent(MainActivity.this, PasswordActivity.class);
                    i.putExtra("vault", adapter.getItem(position));
                    startActivity(i);
                }
            });

            rviewVaults.setAdapter(adapter);

            listener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    adapter.addToList(getVault(dataSnapshot));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    adapter.updateToList(dataSnapshot.getKey(), getVault(dataSnapshot));
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    adapter.removeToList(dataSnapshot.getKey());
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };

            presenter.getReference().addChildEventListener(listener);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rviewVaults.getContext(), manager.getOrientation());
            rviewVaults.addItemDecoration(dividerItemDecoration);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.getReference().removeEventListener(listener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Object obj = Hawk.get("isAuthValid");
        if (obj == null || obj.equals(false)) {
            Intent i = new Intent(this, LockActivity.class);
            i.putExtra("class", "br.com.bunker.view");
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onStop() {
        Hawk.put("isAuthValid", false);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.search(newText);
                rviewVaults.setAdapter(adapter);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @OnClick(R.id.fab_add)
    public void OnClickAdd() {
        startActivity(new Intent(this, PasswordActivity.class));
    }

    private Vault getVault(DataSnapshot dataSnapshot) {
        Vault v = dataSnapshot.getValue(Vault.class);
        v.key = dataSnapshot.getKey();
        return v;
    }

    @Override
    protected void onPause() {
        Hawk.put("isAuthValid", false);
        super.onPause();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


}
