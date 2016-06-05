package tn.ppp.gl3.e_learning.Model;

import java.io.Serializable;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class Category implements Serializable {
    private int id_category;
    private String label;

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
