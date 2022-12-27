package com.final_project2.entity;

import java.io.Serializable;
import java.util.Objects;

public class Car implements Serializable {
    private int id;
    private String imageUrl;
    private String brand;
    private String model;
    private CarTransmission carTransmission;
    private CarClass carClass;
    private double price;
    private String infoAboutCar;
    private boolean isBroken;
    private boolean isFree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public CarTransmission getCarTransmission() {
        return carTransmission;
    }

    public void setCarTransmission(CarTransmission carTransmission) {
        this.carTransmission = carTransmission;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getInfoAboutCar() {
        return infoAboutCar;
    }

    public void setInfoAboutCar(String infoAboutCar) {
        this.infoAboutCar = infoAboutCar;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id && Double.compare(car.price, price) == 0 && isBroken == car.isBroken && isFree == car.isFree && Objects.equals(imageUrl, car.imageUrl) && Objects.equals(brand, car.brand) && Objects.equals(model, car.model) && carTransmission == car.carTransmission && carClass == car.carClass && Objects.equals(infoAboutCar, car.infoAboutCar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageUrl, brand, model, carTransmission, carClass, price, infoAboutCar, isBroken, isFree);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", carTransmission=" + carTransmission +
                ", carClass=" + carClass +
                ", price=" + price +
                ", infoAboutCar='" + infoAboutCar + '\'' +
                ", isBroken=" + isBroken +
                ", isFree=" + isFree +
                '}';
    }

    public enum CarClass{
        ECONOMY,COMFORT,BUSINESS
    }
    public enum CarTransmission{
        MANUAL, AUTOMATIC
    }



}
