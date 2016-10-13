package br.com.materialdesign.domain;

/**
 * Created by Dalton on 13/10/2016.
 */

public class Car {

    private String model;
    private String brand;
    private int photo;

    public Car(){

    }

    public Car(String m, String b, int p){
        this.model = m;
        this.brand = b;
        this.photo = p;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
