package com.pest.control;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        //申请权限
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestReadExternalPermission();
//        }else{
//            start();
//        }

        start();
    }


    @SuppressLint("NewApi")
    private void requestReadExternalPermission() {
        String[] permissions = new String[]{Manifest.permission.CALL_PHONE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("lgx", "READ permission IS NOT granted...");

            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                Log.d("lgx", "11111111111111");

            } else {
                // 0 是自己定义的请求coude
                requestPermissions(permissions, 0);
                Log.d("lgx", "222222222222");
            }
        } else {
            Log.d("lgx", "READ permission is granted...");
            start();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("lgx", "requestCode=" + requestCode + "; --->" + permissions.toString()
                + "; grantResult=" + grantResults.toString());
        switch (requestCode) {
            case 0: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted
                    // request successfully, handle you transactions
                    start();
                } else {

                    // permission denied
                    // request failed
                    Toast.makeText(getApplicationContext(),"请先授权读取权限", Toast.LENGTH_LONG).show();
                }

                return;
            }
            default:
                break;

        }
    }

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
