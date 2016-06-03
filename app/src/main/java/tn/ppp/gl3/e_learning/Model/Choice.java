package tn.ppp.gl3.e_learning.Model;

import java.io.Serializable;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class Choice implements Serializable {

    private int id_choice;
    private String label;
    private int valid;
    private int id_item;

    public int getId_choice() {
        return id_choice;
    }

    public void setId_choice(int id_choice) {
        this.id_choice = id_choice;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }
}
