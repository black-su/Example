package com.example.administrator.android_example.Parcelable;

/**
 * Created by Administrator on 2018/6/16.
 */
public class ProductionBase {
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "ProductionBase{" +
                "address='" + address + '\'' +
                '}';
    }
}
