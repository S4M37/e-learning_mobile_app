package tn.ppp.gl3.e_learning.models;

/**
 * Created by S4M37 on 03/06/2016.
 */
public class ItemResult {
    private int id_item_result;
    private int id_item;
    private int id_result;
    private int response;

    public int getId_item_result() {
        return id_item_result;
    }

    public void setId_item_result(int id_item_result) {
        this.id_item_result = id_item_result;
    }

    public int getId_item() {
        return id_item;
    }

    public void setId_item(int id_item) {
        this.id_item = id_item;
    }

    public int getId_result() {
        return id_result;
    }

    public void setId_result(int id_result) {
        this.id_result = id_result;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
