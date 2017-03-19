package br.com.bunker.view;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import br.com.bunker.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class VaultViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.vault_description)
    TextView vault_description;

    @BindView(R.id.vault_url)
    TextView vault_url;

    public VaultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setDescription(String description) {
        vault_description.setText(description);
    }

    public void setUrl(String url) {
        vault_url.setText(url);
    }
}
