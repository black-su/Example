package com.example.serializable;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/16.
 */
public class Car extends ProductionBase  implements Serializable{

    private int price;
    private String color ;
    static String model = "Lamborghini";
    transient String user;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Car{" +
                "price=" + price +
                ", color='" + color + '\'' +
                ", model='" + model + '\'' +
                ", user='" + user + '\'' +
                ", " + super.toString() + '\'' +
                '}';
    }
}
