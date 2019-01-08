package com.example.administrator.permission;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity {


    public final static int PERMISSION_REQUEST_CODE = 1001;
    public final static int PERMISSION_REQUEST_CODE_FORCE = 1002;

    private PermissionListener permissionListener;
    private String permission;

    protected interface PermissionListener{
        public void granted(String permission);
        public void denied(String permission);
    }
    public PermissionListener getPermissionListener() {
        return permissionListener;
    }

    public void setPermissionListener(PermissionListener permissionListener,String permission) {
        this.permissionListener = permissionListener;
        this.permission = permission;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //由于不同品牌机型之间，权限页面不同，为了不适配机型。用户跳到应用详情页，操作结束后回到本Actiity，再次做权限的判断。
        if(permissionListener != null){
            boolean checkResult = PermissionUtil.checkPermissions(this,permission);
            if(checkResult){
                permissionListener.granted(permission);
            }else{
                permissionListener.denied(permission);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_CODE){
            for (int i = 0; i < permissions.length; i++){
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,permission+"权限没有打开",Toast.LENGTH_SHORT).show();
                    if(permissionListener != null){
                        permissionListener.denied(permission);
                    }
                }else if(permissionListener != null){
                    permissionListener.granted(permission);
                }
            }
            //强制用户打开权限
        }else if(requestCode == PERMISSION_REQUEST_CODE_FORCE){
            for (int i = 0; i < permissions.length; i++){
                String permission = permissions[i];
                int grantResult = grantResults[i];
                if(grantResult != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,permission+"权限没有打开",Toast.LENGTH_SHORT).show();
                    if(permissionListener != null){
                        permissionListener.denied(permission);
                    }
                    PermissionUtil.requestPermissions(this,permission,PERMISSION_REQUEST_CODE_FORCE);
                }else if(permissionListener != null){
                    permissionListener.granted(permission);
                }
            }
        }
    }
}
