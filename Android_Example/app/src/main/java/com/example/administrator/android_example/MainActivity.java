package com.example.administrator.android_example;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.android_example.Parcelable.Car;

public class MainActivity extends AppCompatActivity {
    public static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parcel mParcel = Parcel.obtain();
        final Car mCar = new Car(mParcel);
        Car.model = "Lamborghini";
        mCar.setColor("while");
        mCar.setPrice(9999999);
        mCar.setUser("my wife");
        mCar.setAddress("China");
        Log.d(TAG, "new Car :" + mCar.toString());
        //序列化
        mCar.writeToParcel(mParcel, 0);

        Car.model = "Ferrari";
        mCar.setColor("black");
        mCar.setPrice(8888888);
        mCar.setUser("myself");
        mCar.setAddress("Japan");
        Log.d(TAG, "change Car :" + mCar.toString());
        //手动调用readParcelable获取Parcelable对象，结果返回null ？？？
        try {
            Car newCar =mParcel.readParcelable(null);
            Log.d(TAG, "readParcelable :" + newCar.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getApplicationContext(), Main2Activity.class);
                Bundle mBundle = new Bundle();
                mBundle.putParcelable("mydata", mCar);
                mIntent.putExtras(mBundle);

                Log.d(TAG, "OnClickListener :" + mIntent.getExtras().getParcelable("mydata").toString());
            }
        });

    }
}
