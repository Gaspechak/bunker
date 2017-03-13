package br.com.bunker.model;

import com.google.firebase.database.Exclude;

public class Vault {

    @Exclude
    public String key;
    public String description;
    public String url;
    public String username;
    public String password;
    public String note;
}
