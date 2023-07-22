package com.cosmic.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.cosmic.controller.AppController;
import android.cosmic.utils.FileCommons;
import android.cosmic.utils.CPreferences;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private CPreferences cPreferences;
    public static int REQUEST = 5565;
    public String daemonPath;
    public static String socket;
    public static boolean isRoot = false;
    public static boolean isNoroot = false;
    public static boolean rootMod = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CPreferences.InitShared(this);
        cPreferences = new CPreferences(this);
        requestOverlayPermission();
        FileCommons.copyFromAssets(this, getFilesDir().getPath(), "UtilityHelper");
        CardView cv_start_cheat = findViewById(R.id.cv_start_cheat);
        CardView cv_stop_cheat = findViewById(R.id.cv_stop_cheat);
        RadioButton rb_global = findViewById(R.id.rb_global);
        RadioButton rb_india = findViewById(R.id.rb_india);
        RadioButton rb_vietnam = findViewById(R.id.rb_vietnam);
        RadioButton rb_korea = findViewById(R.id.rb_korea);
        RadioButton rb_taiwan = findViewById(R.id.rb_taiwan);
        RadioButton isRooted = findViewById(R.id.isRooted);
        RadioButton isNoRooted = findViewById(R.id.isNoRooted);
        /** PUBGM Version **/
        /**
         *  Global 1
         *  India 2
         *  Vietnam 3
         *  Korea 4
         *  Taiwan 5
         **/
        rb_global.setOnClickListener(view -> {
            cPreferences.setInt("PUBGM_VERSION", 1);
        });
        rb_india.setOnClickListener(view -> {
            cPreferences.setInt("PUBGM_VERSION", 2);
        });
        rb_vietnam.setOnClickListener(view -> {
            cPreferences.setInt("PUBGM_VERSION", 3);
        });
        rb_korea.setOnClickListener(view -> {
            cPreferences.setInt("PUBGM_VERSION", 4);
        });
        rb_taiwan.setOnClickListener(view -> {
            cPreferences.setInt("PUBGM_VERSION", 5);
        });

        if (cPreferences.getInt("PUBGM_VERSION") == 1){
            rb_global.setChecked(true);
        }
        if (cPreferences.getInt("PUBGM_VERSION") == 2){
            rb_india.setChecked(true);
        }
        if (cPreferences.getInt("PUBGM_VERSION") == 3){
            rb_vietnam.setChecked(true);
        }
        if (cPreferences.getInt("PUBGM_VERSION") == 4){
            rb_korea.setChecked(true);
        }
        if (cPreferences.getInt("PUBGM_VERSION") == 5){
            rb_taiwan.setChecked(true);
        }

        isRooted.setOnClickListener(view -> {
            isRoot = true;
            ExecuteElf("su -c");
            isNoroot = false;
            rootMod = true;
            Log.d("COSMIC", "Root granted");
            cPreferences.setInt("EnvironmentMode", 1);
        });
        isNoRooted.setOnClickListener(view -> {
            isNoroot = true;
            isRoot = false;
            rootMod = true;
            Log.d("COSMIC", "Root not granted");
            cPreferences.setInt("EnvironmentMode", 2);
        });
        if (cPreferences.getInt("EnvironmentMode") == 1){
            isRoot = true;
            ExecuteElf("su -c");
            isNoroot = false;
            rootMod = true;
            Log.d("COSMIC", "Root granted");
            isRooted.setChecked(true);
        }
        if (cPreferences.getInt("EnvironmentMode") == 2){
            isNoroot = true;
            isRoot = false;
            rootMod = true;
            Log.d("COSMIC", "Root not granted");
            isNoRooted.setChecked(true);
        }

        cv_start_cheat.setOnClickListener(view -> {
            if(rootMod) {
                daemonPath = getFilesDir().getPath()+"/UtilityHelper";
                if(isNoroot)
                {
                    socket = daemonPath;
                }

                if(isRoot)
                {
                    socket = "su -c " + daemonPath;
                }
                try{
                    Runtime.getRuntime().exec("chmod 777 "+daemonPath);
                }
                catch (IOException e)
                {
                }
                AppController.InitService(this, AppController.START);
            }
            else {
                Toast.makeText(getApplicationContext(),"Please Select Environment First!",Toast.LENGTH_LONG).show();
            }
        });
        cv_stop_cheat.setOnClickListener(view -> {
            AppController.InitService(this, AppController.STOP);
        });
    }

    private void requestOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(this)){
                new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog)
                        .setMessage("Please enable overlay permission first")
                        .setPositiveButton("OK", (dialogInterface, i) -> {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
                            startActivityForResult(intent, REQUEST);
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> finish())
                        .setCancelable(false)
                        .show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestOverlayPermission();
            }
        }
    }

    private void ExecuteElf(String shell)
    {
        String s=shell;

        try
        {
            Runtime.getRuntime().exec(s,null,null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}