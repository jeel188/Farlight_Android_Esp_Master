package android.cosmic.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.cosmic.ui.R;

public class ViewInflater {
    public static View getMainView(Context context){
        return LayoutInflater.from(context).inflate(R.layout.overlay_layout, null);
    }
}
