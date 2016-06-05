package tn.ppp.gl3.e_learning.Model;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class Course {
    private int id_course;
    private String img_path;
    private String pdf_path;
    private String label;
    private Category category;

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getId_cource() {
        return id_course;
    }

    public void setId_cource(int id_cource) {
        this.id_course = id_cource;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getPdf_path() {
        return pdf_path;
    }

    public void setPdf_path(String pdf_path) {
        this.pdf_path = pdf_path;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
