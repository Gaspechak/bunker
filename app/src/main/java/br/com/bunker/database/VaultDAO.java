package br.com.bunker.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.bunker.model.Vault;

public class VaultDAO {

    private DatabaseReference db;
    private FirebaseUser currentUser;

    public VaultDAO() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid()).child("data");
    }

    public String insert(Vault vault) {
        DatabaseReference ref = db.push();
        ref.setValue(vault);
        return ref.getKey();
    }

    public void update(Vault vault) {
        db.child(vault.key).setValue(vault);
    }

    public void delete(String key) {
        db.child(key).removeValue();
    }
}
