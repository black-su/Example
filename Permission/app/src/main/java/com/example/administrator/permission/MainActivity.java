package com.example.administrator.permission;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtil.requestPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION,BaseActivity.PERMISSION_REQUEST_CODE);
        setPermissionListener(new PermissionListener() {
            @Override
            public void granted(String permission) {
                //权限申请后的逻辑处理
                Toast.makeText(MainActivity.this,permission+"权限已经打开--MainActivity",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void denied(String permission) {
                //权限申请后的逻辑处理
                Toast.makeText(MainActivity.this,permission+"权限被禁止--MainActivity",Toast.LENGTH_SHORT).show();

            }
        },Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
