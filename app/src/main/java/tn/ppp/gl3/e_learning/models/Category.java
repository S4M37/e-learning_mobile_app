package tn.ppp.gl3.e_learning.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Category implements Serializable {
    @SerializedName("id_category")
    private int idCategory;
    private String label;

    private Exam[] trainings;

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Exam[] getTrainings() {
        return trainings;
    }

    public void setTrainings(Exam[] trainings) {
        this.trainings = trainings;
    }
}
