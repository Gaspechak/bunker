package br.com.bunker.helper;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.bunker.R;
import br.com.bunker.model.Vault;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VaultAdapter extends RecyclerAdapter<Vault> {

    @BindView(R.id.vault_description)
    TextView vault_description;

    @BindView(R.id.vault_url)
    TextView vault_url;

    private List<Vault> items;

    public VaultAdapter(Context context) {
        super(context);
        items = adapterList;
    }

    public VaultAdapter(Context context, OnViewHolderClick listener) {
        super(context, listener);
        items = adapterList;
    }

    @Override
    protected View createView(Context context, ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_holder_vault, viewGroup, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void bindView(Vault item, ViewHolder viewHolder) {
        vault_description.setText(item.description);
        vault_url.setText(item.url);
    }

    public void search(String query){
        if (query != null && query.length() > 0) {
            String filter = query.toLowerCase().trim();

            List<Vault> newVaults = new ArrayList<>();

            for (Vault item : adapterList) {
                String description = item.description.toLowerCase().trim();
                String url = item.url.toLowerCase().trim();
                String note = item.note.toLowerCase().trim();

                if (description.contains(filter) || url.contains(filter) || note.contains(filter)) {
                    newVaults.add(item);
                }
            }

            adapterList = new ArrayList<>(newVaults);
        } else {
            adapterList = items;
        }
    }

    public void addToList(Vault item) {
        adapterList.add(item);
        notifyItemInserted(getItemCount());
    }

    public void updateToList(String key, Vault item) {
        for (int i = 0; i < adapterList.size(); i++) {
            if (adapterList.get(i).key.equals(key)) {
                adapterList.set(i, item);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void removeToList(String key) {
        for (int i = 0; i < adapterList.size(); i++) {
            if (adapterList.get(i).key.equals(key)) {
                adapterList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }
}
