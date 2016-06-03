package tn.ppp.gl3.e_learning.Model;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class Course {
    private int id_cource;
    private String img_path;
    private String pdf_path;
    private String label;

    public int getId_cource() {
        return id_cource;
    }

    public void setId_cource(int id_cource) {
        this.id_cource = id_cource;
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
