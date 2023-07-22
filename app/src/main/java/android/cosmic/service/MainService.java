package android.cosmic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.cosmic.utils.DevicesInfo;
import android.cosmic.utils.CPreferences;
import android.cosmic.view.OverlayView;

import java.io.DataOutputStream;
import java.io.IOException;

import static com.cosmic.ui.MainActivity.socket;

public class MainService extends Service {

    static {
        System.loadLibrary("CosmicJNIUtility");
    }

    public native int InitBase(int versionCode, int width, int heigth);
    public static native void CloseSocket();

    static MainService instance;
    Process process;

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    private CPreferences cPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        cPreferences = new CPreferences(this);
        new Thread(() -> {
            if (MainService.this.InitBase(
                    cPreferences.getInt("PUBGM_VERSION"),
                    DevicesInfo.getWidth(MainService.this.getBaseContext()),
                    DevicesInfo.getHeight(MainService.this.getBaseContext())) != 1) {
                new Handler(Looper.getMainLooper()).post(() -> Stop());
            } else {
                new Handler(Looper.getMainLooper()).post(() -> OverlayView.init(instance).show());
            }
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Shell(socket);
        }).start();
    }

    public static void Start(Context context){
        if (instance == null){
            context.startService(new Intent(context, MainService.class));
        }
    }
    public static void Stop(){
        if (instance != null){
            instance.stopService(new Intent(instance, MainService.class));
            instance = null;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        OverlayView.destroy();
        CloseSocket();
    }

    public void Shell(String str) {

        DataOutputStream dataOutputStream = null;
        try {
            process = Runtime.getRuntime().exec(str);
        } catch (IOException e) {
            e.printStackTrace();
            process = null;
        }
        if (process != null) {
            dataOutputStream = new DataOutputStream(process.getOutputStream());
        }
        try {
            dataOutputStream.flush();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        try {
            process.waitFor();
        } catch (InterruptedException e3) {
            e3.printStackTrace();
        }
    }
}