package tn.ppp.gl3.e_learning.Model;

import java.io.Serializable;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class Exam implements Serializable {
    private int id_exam;
    private String label;
    private int duration;
    private Item[] items;
    private Category[] categories;

    public Exam(Item[] items) {
        this.items = items;
        label = "training";
        duration = 0;
    }

    public Category[] getCategories() {
        return categories;
    }

    public void setCategories(Category[] categories) {
        this.categories = categories;
    }

    public int getId_exam() {
        return id_exam;
    }

    public void setId_exam(int id_exam) {
        this.id_exam = id_exam;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Item[] getItems() {
        return items;
    }

    public void setItems(Item[] items) {
        this.items = items;
    }
}
