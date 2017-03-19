package br.com.bunker.presenter;

import com.google.firebase.database.DatabaseReference;

import br.com.bunker.database.VaultDAO;
import br.com.bunker.view.MainView;

public class MainPresenter {

    private MainView mainView;
    private VaultDAO dao;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
        dao = new VaultDAO();
    }

    public DatabaseReference getReference() {
        return dao.getReference();
    }
}
