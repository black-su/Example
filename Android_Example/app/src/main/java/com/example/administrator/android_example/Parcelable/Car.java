package com.example.administrator.android_example.Parcelable;


import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Created by Administrator on 2018/6/16.
 */
public class Car extends ProductionBase implements Parcelable {

    public int price;
    public String color;
    public static String model;
    public transient String user;

    public Car(Parcel in){
        price = in.readInt();
        color = in.readString();
        model = in.readString();
        user = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>(){

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }

        @Override
        public Car createFromParcel(Parcel source) {
            Log.d("MainActivity", "createFromParcel :" + source.toString()+ Log.getStackTraceString(new Throwable()));
            return new Car(source);
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(price);
        dest.writeString(color);
        dest.writeString(model);
        dest.writeString(user);
        Log.d("MainActivity", "writeToParcel :" + dest.toString() + Log.getStackTraceString(new Throwable()));
    }

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
