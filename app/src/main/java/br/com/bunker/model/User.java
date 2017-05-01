package br.com.bunker.model;


public class User {

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String name;
    public String email;
    public String password;

    public boolean isValid() {
        return name.length() > 0 && email.length() > 0 && password.length() >= 6;
    }
}
