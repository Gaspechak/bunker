package br.com.bunker.helper;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import br.com.bunker.R;
import br.com.bunker.model.Vault;
import br.com.bunker.view.MainActivity;
import br.com.bunker.view.PasswordActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VaultAdapter extends RecyclerAdapter<Vault> {

    @BindView(R.id.vault_description)
    TextView vault_description;

    @BindView(R.id.vault_url)
    TextView vault_url;

    @BindView(R.id.list_detail)
    LinearLayout listItem;

    @BindView(R.id.copy)
    ImageView copy;

    @BindView(R.id.logo_icon)
    ImageView logo;

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
    protected void bindView(final Vault item, ViewHolder viewHolder) {
        vault_description.setText(item.description);
        vault_url.setText(item.url);
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), PasswordActivity.class);
                i.putExtra("vault", item);
                getContext().startActivity(i);
            }
        });

        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(item.key, Encryptor.decrypt(item.password, Hawk.get("pwd").toString()));
                    clipboard.setPrimaryClip(clip);
                } catch (Exception e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "Copiado para área de transferencia", Toast.LENGTH_SHORT).show();
            }
        });


        GlideApp.with(getContext())
                .load("https://logo.clearbit.com/" + item.url)
                .placeholder(R.drawable.ic_key)
                .into(logo);
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
