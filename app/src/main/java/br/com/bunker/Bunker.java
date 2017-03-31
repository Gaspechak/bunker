package br.com.bunker;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.hawk.Hawk;

public class Bunker extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Hawk.init(this).build();
    }
}
