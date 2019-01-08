package com.example.administrator.permission;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class PermissionUtil {

    private final static String TAG = "PermissionUtil";
    private final static HashMap<String,Integer> permissionMap = new HashMap<String,Integer>();
    public static boolean checkPermissions(Activity activity, String permission){

        int targetSdkVersion = 0;
        int checkResult = -1;
        PackageManager pm = activity.getPackageManager();
        try {
            final PackageInfo info = pm.getPackageInfo(activity.getPackageName(),0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        }catch (Exception e){
            e.printStackTrace();
        }

        //android 6.0以上的版本需要检查权限，6.0以下的版本不需要检查权限
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //如果targetSdkVersion小于23，需要使用PermissionChecker来判断权限,因为ActivityCompat.checkSelfPermission是API 23时加入的
            if (targetSdkVersion < Build.VERSION_CODES.M) {
                /*
                 * 此处得到的结果不准确，特别是在选定权限为"询问"时，不同品牌机型之间返回不同的值。
                 * 荣耀8返回PERMISSION_GRANTED，小米返回PERMISSION_DENIED_APP_OP
                 * 因此，android6.0以上的不同机型上，使用的targetSdkVersion小于23，在判断权限上会有出入。
                 * 建议在android6.0以上的机型中，targetSdkVersion尽量设置大于等于23
                 */
                checkResult = PermissionChecker.checkSelfPermission(activity, permission);
            } else {
                checkResult = ActivityCompat.checkSelfPermission(activity, permission);
            }
        }else{
            checkResult = pm.checkPermission(permission,activity.getPackageName());
        }
        return checkResult == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissions(Activity activity, String permission, int requestCode){
        /*
         * 仅在android6.0且targetSdkVersion大于API 23的情况下有效。
         * 勾选"不再询问"后返回false,第一次弹框，返回false
         * 用户选择"禁止"但是没有勾选"不再询问"时，返回true
         */
        boolean isShow = ActivityCompat.shouldShowRequestPermissionRationale(activity,permission);

        boolean checkResult = checkPermissions(activity,permission);

        android.util.Log.v(TAG,"isShow:"+isShow+"   checkResult:"+checkResult);
        //用来判断此权限是否是第一次被请求
        if(!isShow && !permissionMap.containsKey(permission)){
            permissionMap.put(permission,0);
        }else if(permissionMap.containsKey(permission)){
            int count = (Integer)permissionMap.get(permission);
            permissionMap.put(permission,++count);
        }

        int targetSdkVersion = 0;
        PackageManager pm = activity.getPackageManager();
        try {
            final PackageInfo info = pm.getPackageInfo(activity.getPackageName(),0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        }catch (Exception e){
            e.printStackTrace();
        }

        //应用没有获取权限
        if(!checkResult){
            //如果android系统大于6.0，且targetSdkVersion大于API 23
            boolean afterM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && targetSdkVersion >= Build.VERSION_CODES.M;
            //如果android系统大于6.0，但是targetSdkVersion小于API 23
            boolean sdkBeforeM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && targetSdkVersion < Build.VERSION_CODES.M;

            //此请求是否是第一次被请求，如果是第一次请求，弹出系统权限框
            boolean isFirstRequest = permissionMap.get(permission) == 0;

            //用户没有勾选"不再询问"
            if(afterM && (isShow || isFirstRequest)){
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
                //用户勾选了"不再询问"，另做处理
            }else if(afterM && !isShow){
                Toast.makeText(activity, "你需要手动打开权限", Toast.LENGTH_SHORT).show();
                showDialog(activity,requestCode);
                //android6.0机型中targetSdkVersion小于API 23，不同品牌机型之间行为不同，不能使用系统权限框，建议自己处理
            }else if(sdkBeforeM){
                Toast.makeText(activity, "你需要手动打开权限", Toast.LENGTH_SHORT).show();
                showDialog(activity,requestCode);

            }
        }
    }

    public static void showDialog(final Activity activity, final int requestCode){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("权限");
        builder.setMessage("去打开权限");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent localIntent = new Intent();
                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= 9) {
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    localIntent.setAction(Intent.ACTION_VIEW);
                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                }
                activity.startActivity(localIntent);
            }
        });
        if(requestCode == BaseActivity.PERMISSION_REQUEST_CODE_FORCE) {
            builder.setCancelable(false);
        }else{
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }
        builder.show();

    }
}
