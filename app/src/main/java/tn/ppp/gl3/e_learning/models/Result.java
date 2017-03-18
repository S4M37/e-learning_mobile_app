package tn.ppp.gl3.e_learning.models;

/**
 * Created by S4M37 on 03/06/2016.
 */
public class Result {
    private int id_result;
    private String label;
    private float score;
    private String observation;
    private String test_date;
    private ItemResult[] items_result;
    private Exam exam;

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getId_result() {
        return id_result;
    }

    public void setId_result(int id_result) {
        this.id_result = id_result;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getTest_date() {
        return test_date;
    }

    public void setTest_date(String test_date) {
        this.test_date = test_date;
    }

    public ItemResult[] getItems_result() {
        return items_result;
    }

    public void setItems_result(ItemResult[] items_result) {
        this.items_result = items_result;
    }
}
