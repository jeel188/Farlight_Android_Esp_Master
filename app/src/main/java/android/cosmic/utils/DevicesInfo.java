package android.cosmic.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.view.ViewConfiguration;
import android.view.WindowManager;

public class DevicesInfo {

    public static int getWidth(Context context){
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point= new Point();
        mWm.getDefaultDisplay().getRealSize(point);
        return point.x;
    }

    public static int getHeight(Context context){
        WindowManager mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point= new Point();
        mWm.getDefaultDisplay().getRealSize(point);
        return point.y;
    }
    public static int getLayoutType() {
        int LAYOUT_FLAG;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_TOAST;
        } else {
            LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        return LAYOUT_FLAG;
    }
    public static int getNavigationBarHeight(Context context) {
        boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0 && !hasMenuKey) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
