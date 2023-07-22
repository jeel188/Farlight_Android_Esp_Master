package android.cosmic.view;

import android.content.Context;
import android.view.WindowManager;

public class MainWindowManager {
    public static WindowManager getWindowManager(Context context){
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }
}
