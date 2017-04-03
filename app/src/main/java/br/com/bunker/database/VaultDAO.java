package br.com.bunker.database;

import com.google.firebase.database.DatabaseReference;

import br.com.bunker.model.Vault;

public class VaultDAO extends BaseDAO<Vault> {

    private static String databasePath = "vaults";

    @Override
    public String insert(Vault entity) {
        DatabaseReference ref = db.child(databasePath).push();
        ref.setValue(entity);
        return ref.getKey();
    }

    @Override
    public void update(Vault entity) {
        db.child(databasePath).child(entity.key).setValue(entity);
    }

    @Override
    public void remove(String key) {
        db.child(databasePath).child(key).removeValue();
    }

    @Override
    public DatabaseReference getReference() {
        return db.child(databasePath);
    }
}
