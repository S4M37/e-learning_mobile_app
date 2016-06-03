package tn.ppp.gl3.e_learning.Model;

import java.io.Serializable;

/**
 * Created by S4M37 on 17/04/2016.
 */
public class User implements Serializable {
    private int id_user;
    private String name;
    private String email;
    private int valide;

    public int getValide() {
        return valide;
    }

    public void setValide(int valide) {
        this.valide = valide;
    }

    public User(int id_user, String name, String email) {
        this.id_user = id_user;
        this.name = name;
        this.email = email;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "id : " + id_user + " name : " + name + " email: " + email;
    }
}
