package android.cosmic.controller;

import android.content.Context;
import android.cosmic.service.MainService;

public class AppController {
    public static int STOP = 0;
    public static int START = 1;
    public static void InitService(Context context, int mode){
        switch (mode){
            case 0:
                MainService.Stop();
                break;
            case 1:
                MainService.Start(context);
                break;
        }
    }
}
