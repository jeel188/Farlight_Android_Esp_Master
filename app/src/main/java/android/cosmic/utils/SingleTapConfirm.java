package android.cosmic.utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

public class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }
}
