package com.pokemum.domainLayer.domainModel;

/**
 * Created by qiaorui on 16/05/15.
 */
public class Usuario {
    private String username;
    private String password;
    private String name;

    public Usuario(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}
