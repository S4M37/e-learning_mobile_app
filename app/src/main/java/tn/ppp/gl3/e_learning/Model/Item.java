package tn.ppp.gl3.e_learning.Model;

import java.io.Serializable;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class Item implements Serializable {
    private int id_item;
    private int id_category;
    private String label;
    private Choice[] choices;

    public Choice[] getChoices() {
        return choices;
    }

    public void setChoices(Choice[] choices) {
        this.choices = choices;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

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
