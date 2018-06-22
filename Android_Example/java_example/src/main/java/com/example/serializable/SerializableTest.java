package com.example.serializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Created by Administrator on 2018/6/16.
 */
public class SerializableTest {

    private static String path = "F:\\AndroidStudioProjects\\Android_Example\\java_example\\src\\main\\java\\com\\example\\serializable\\SerializableCar.txt";
    public static void main(String args[]){

        Car myCar = new Car();
        myCar.setColor("while");
        myCar.setPrice(9999999);
        myCar.setUser("my wife");
        myCar.setAddress("China");
        try {
            System.out.println("myCar is :" + myCar.toString());
            SerializableCar(myCar);//序列化
            Car.model = "Ferrari";
            myCar.setColor("black");
            myCar.setPrice(8888888);
            myCar.setUser("myself");
            myCar.setAddress("Japan");

            Object object = DeserializationCar(path);//反序列化
            Car newCar = (Car)object;

            System.out.println("newCar is :" + newCar.toString());
            //获取默认的SerialVersionUID值
            ObjectStreamClass osc2 = ObjectStreamClass.lookup(Car.class);
            System.out.println("SerialVersionUID ：" + osc2.getSerialVersionUID());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //序列化
    public static void SerializableCar(Object object) throws IOException {
        File mFile = new File(path);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mFile));
        oos.writeObject(object);
        oos.close();
        System.out.println("SerializableCar is success, Serializable file is in "+ path);
    }
    //反序列化
    public static Object DeserializationCar(String mPath) throws  IOException,ClassNotFoundException{
        File mFile = new File(mPath);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mFile));
        Object object = ois.readObject();
        System.out.println("DeserializationCar is success, return the Object");
        return object;
    }
}
