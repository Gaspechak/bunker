package br.com.bunker.database;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

abstract class BaseDAO<T> {

    DatabaseReference db;
    FirebaseUser currentUser;

    BaseDAO() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance().getReference("bunker").child("users").child(currentUser.getUid());
    }

    public abstract String insert(T entity);

    public abstract void update(T entity);

    public abstract void remove(String key);

    public abstract DatabaseReference getReference();
}
