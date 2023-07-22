package android.cosmic.view;

import android.content.Context;
import android.cosmic.utils.ExtraUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.cosmic.utils.ESPEngine;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.cosmic.ui.R;

import static android.cosmic.view.OverlayView.getShowBoolean;
import static android.cosmic.view.OverlayView.getValueColor;

public class CanvasDrawingView extends View {
    Paint p;
    int FPS = 60;
    Paint mTextPaint, mStrokePaint, mFilledPaint, mRectPaint;
    Rect eRect;
    GradientDrawable eGradientDrawable;
    int eColor[] = {Color.TRANSPARENT,Color.GREEN,Color.TRANSPARENT};

    Bitmap[] WEP = new Bitmap[120];
    Bitmap[] VEH = new Bitmap[20];
    static Bitmap[] FLAG = new Bitmap[250];
    static Bitmap[] FLAG_SV = new Bitmap[250];

    private static int[] VEH_NAME = {
            //R.drawable.ve0,
            R.drawable.ve1,
            R.drawable.ve2,
            R.drawable.ve3,
            R.drawable.ve4,
            R.drawable.ve5,
            R.drawable.ve6,
            R.drawable.ve7,
            R.drawable.ve8,
            R.drawable.ve9,
            R.drawable.ve10,
            R.drawable.ve11,
            R.drawable.ve12,
            R.drawable.ve13,
            R.drawable.ve14,
            R.drawable.ve15,
            R.drawable.ve16,
            R.drawable.ve17,
    };

    private static final int[] ITEM_NAME = {
            R.drawable.i1,
            R.drawable.i2,
            R.drawable.i3,
            R.drawable.i4,
            R.drawable.i5,
            R.drawable.i6,
            R.drawable.i7,
            R.drawable.i8,
            R.drawable.i9,
            R.drawable.i10,
            R.drawable.i11,
            R.drawable.i12,
            R.drawable.i13,
            R.drawable.i14,
            R.drawable.i15,
            R.drawable.i16,
            R.drawable.i17,
            R.drawable.i18,
            R.drawable.i19,
            R.drawable.i20,
            R.drawable.i21,
            R.drawable.i22,
            R.drawable.i23,
            R.drawable.i24,
            R.drawable.i25,
            R.drawable.i26,
            R.drawable.i27,
            R.drawable.i28,
            R.drawable.i29,
            R.drawable.i30,
            R.drawable.i31,
            R.drawable.i32,
            R.drawable.i33,
            R.drawable.i34,
            R.drawable.i35,
            R.drawable.i36,
            R.drawable.i37,
            R.drawable.i38,
            R.drawable.i39,
            R.drawable.i40,
            R.drawable.i41,
            R.drawable.i42,
            R.drawable.i43,
            R.drawable.i44,
            R.drawable.i45,
            R.drawable.i46,
            R.drawable.i47,
            R.drawable.i48,
            R.drawable.i49,
            R.drawable.i50,
            R.drawable.i51,
            R.drawable.i52,
            R.drawable.i53,
            R.drawable.i54,
            R.drawable.i55,
            R.drawable.i56,
            R.drawable.i57,
            R.drawable.i58,
    };

    public CanvasDrawingView(Context context) {
        super(context, null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(Color.TRANSPARENT);
    }
    private void InitializePaints() {
        eRect = new Rect(-100,60,100,100);
        eGradientDrawable = new GradientDrawable();
        eGradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        eGradientDrawable.setColors(eColor);
        eGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        eGradientDrawable.setBounds(eRect);

        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setColor(Color.rgb(0, 0, 0));

        mTextPaint = new Paint();
        mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStrokeWidth(1.1f);

        mFilledPaint = new Paint();
        mFilledPaint.setStyle(Paint.Style.FILL);
        mFilledPaint.setAntiAlias(true);

        mRectPaint = new Paint();
        mRectPaint.setStyle(Paint.Style.STROKE);
        mRectPaint.setAntiAlias(true);

        p=new Paint();

        final int bitmap_count_veh = VEH_NAME.length;
        for(int i = 0 ; i < bitmap_count_veh ; i++)
        {
            VEH[i] = BitmapFactory.decodeResource(getResources(), VEH_NAME[i]);
            VEH[i] = scale(VEH[i],40,40);
        }

        final int bitmap_count_wep = ITEM_NAME.length;
        for(int i = 0 ; i < bitmap_count_wep ; i++)
        {
            WEP[i] = BitmapFactory.decodeResource(getResources(), ITEM_NAME[i]);
            WEP[i] = scale(WEP[i],70,70);
        }

        final int bitmap_count_flags = ExtraUtils.Flags.length;
        for(int i = 0 ; i < bitmap_count_flags ; i++)
        {
            FLAG[i] = BitmapFactory.decodeResource(getResources(), ExtraUtils.Flags_Id[i]);
            FLAG_SV[i] = FLAG[i];
            FLAG[i] = scale(FLAG[i],24,24);
            Log.e("Flag",Integer.toString(i) + " " + ExtraUtils.Flags[i]);
        }
    }

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                if (true)
                {
                    invalidate();
                }
            } catch (Exception e){
            }
            handler.postDelayed(this, FPS);
        }
    };
    public void runEsp() {
        runnable.run();
    }
    public void setFPS(int fps){
        FPS = 60 - fps;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas != null) {
            ClearCanvas(canvas);
            OverlayView.GetInitCanvasDrawing(this, canvas);
        }
    }
    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
    }
    public void DrawEnemyCount(Canvas cvs, int colors, float dx, float dy) {
        if (colors == 1){ // Enemy = 0
            eColor[1] = Color.GREEN;
        }
        if (colors == 2){ // Enemy > 5
            eColor[1] = Color.YELLOW;
        }
        if (colors == 3){ // Enemy > 10
            eColor[1] = Color.parseColor("#FF6D00");
        }
        if (colors == 4){ // Enemy > 15
            eColor[1] = Color.RED;
        }
        cvs.save();
        cvs.translate(dx,dy);
        eGradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        eGradientDrawable.draw(cvs);
        cvs.restore();
    }
    public void DrawText(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.setShadowLayer(3, 0,0, Color.BLACK);
        mTextPaint.setColor(Color.rgb(r, g, b));
        mTextPaint.setAlpha(a);
        if (getRight() > 1920 || getBottom() > 1920)
            mTextPaint.setTextSize(4 + size);
        else if (getRight() == 1920 || getBottom() == 1920)
            mTextPaint.setTextSize(2 + size);
        else
            mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }
    public void DrawText2(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        mTextPaint.clearShadowLayer();
        mTextPaint.setColor(Color.rgb(r, g, b));
        mTextPaint.setAlpha(a);
        if (getRight() > 1920 || getBottom() > 1920)
            mTextPaint.setTextSize(4 + size);
        else if (getRight() == 1920 || getBottom() == 1920)
            mTextPaint.setTextSize(2 + size);
        else
            mTextPaint.setTextSize(size);
        cvs.drawText(txt, posX, posY, mTextPaint);
    }
    public void DrawLine(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        mStrokePaint.setColor(Color.rgb(r, g, b));
        mStrokePaint.setAlpha(a);
        mStrokePaint.setStrokeWidth(lineWidth);
        cvs.drawLine(fromX, fromY, toX, toY, mStrokePaint);
    }
    public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float cx, float cy, float radius){
        mFilledPaint.setColor(Color.rgb(r, g, b));
        mFilledPaint.setAlpha(a);
        cvs.drawCircle(cx, cy, radius, mFilledPaint);
    }
    public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius, float stroke) {
        mStrokePaint.setARGB(a,r,g,b);
        mStrokePaint.setStrokeWidth(stroke);
        cvs.drawCircle(posX, posY, radius, mStrokePaint);
    }
    public void DrawRect(Canvas canvas, int a, int r, int g, int b, float strokeSize, float startX, float startY, float endX, float endY){
        mRectPaint.setColor(Color.rgb(r, g, b));
        mTextPaint.setAlpha(a);
        mRectPaint.setStrokeWidth(strokeSize);
        canvas.drawRect(startX, startY, endX, endY, mRectPaint);
    }
    public void DrawEnemyWeapon(Canvas cvs, int a, int r, int g, int b, int id, int ammo, float posX, float posY, float size) {
        mTextPaint.setARGB(a,r, g, b);
        mTextPaint.clearShadowLayer();
        String wName = ESPEngine.getEnemyWeapon(id);
        if(wName != null){
            mTextPaint.setTextSize(size);
            mTextPaint.setColor(Color.WHITE);
            cvs.drawText(wName + " / " + ammo, posX, posY, mTextPaint);
        }
    }

    public void DrawFLAG(Canvas cvs, String nametxt, float X, float Y) {
        String flag = nametxt;
        String countryName = ESPEngine.getFlagName(nametxt);
        if (getShowBoolean("Nation Text")) {
            if(!countryName.equals("")) {
                cvs.drawText(countryName, X, Y, mTextPaint);
            }
        }
        else if (getShowBoolean("Nation Image")) {
            cvs.drawBitmap(FLAG[ESPEngine.getFlag(flag)], X, Y - 20, p);
        }
    }

    public void DrawVehicles(Canvas cvs, String itemName, int distance, float posX, float posY, float size) {
        int vehicleNum = ESPEngine.getVehicleNum(itemName);
        String vehicleName = ESPEngine.getVehicleName(itemName);
        mTextPaint.setShadowLayer(5, 0,0, Color.BLACK);
        mTextPaint.setColor(Color.WHITE);
        float textSize = 15;
        if (getRight() > 1920 || getBottom() > 1920)
            mTextPaint.setTextSize(4 + textSize);
        else if (getRight() == 1920 || getBottom() == 1920)
            mTextPaint.setTextSize(2 + textSize);
        else
            mTextPaint.setTextSize(textSize);
        if(vehicleNum!=-1){
            if (getValueColor(vehicleName) != 0)
                mTextPaint.setColor(getValueColor(itemName));
            if (getShowBoolean("Images + Distance")) {
                cvs.drawBitmap(VEH[vehicleNum],posX - 20,posY - 40 - 30,p);
                cvs.drawText(vehicleName + ": "+ distance + "m", posX, posY  - 10, mTextPaint);
            }

            else if (getShowBoolean("Images Only")) {
                cvs.drawBitmap(VEH[vehicleNum],posX - 20,posY - 40 - 30,p);
            }
            else if (getShowBoolean("No Images (Default)")) {
                cvs.drawText(vehicleName + ": "+ distance + "m", posX, posY, mTextPaint);
            }
        }
    }
    public void DrawItem(Canvas cvs, String itemName, int distance, float posX, float posY, float size) {
        int itemsNum = ESPEngine.GetItemsNum(itemName);
        String itemsName = ESPEngine.GetItemsType(itemName);
        mTextPaint.setShadowLayer(5, 0,0, Color.BLACK);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(size);
        float textSize = 15;
        if (getRight() > 1920 || getBottom() > 1920)
            mTextPaint.setTextSize(4 + textSize);
        else if (getRight() == 1920 || getBottom() == 1920)
            mTextPaint.setTextSize(2 + textSize);
        else
            mTextPaint.setTextSize(textSize);
        boolean ItemsNotFound = itemsName != null;
        if(!isEmpty(itemsName) || ItemsNotFound){
            if (getValueColor(itemsName) != 0)
                mTextPaint.setColor(getValueColor(itemName));
            mTextPaint.setColor(ESPEngine.getColor());
            mTextPaint.setAlpha(220);
            if(itemsNum!=-1) {
                if (getShowBoolean("Images + Distance")) {
                    cvs.drawBitmap(WEP[itemsNum], posX - 30, posY - 60 - 30, p);
                } else if (getShowBoolean("Images Only")) {
                    cvs.drawBitmap(WEP[itemsNum], posX - 30, posY - 60 - 30, p);
                }
            }
            if (getShowBoolean("No Images (Default)")) {
                cvs.drawText(itemsName + "("+ distance + "m)", posX, posY, mTextPaint);
            } else if (getShowBoolean("Images + Distance")) {
                cvs.drawText(itemsName + "("+ distance + "m)", posX, posY, mTextPaint);
            }
        }
    }

    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    public static Bitmap scale(Bitmap bitmap, int maxWidth, int maxHeight) {
        // Determine the constrained dimension, which determines both dimensions.
        int width;
        int height;
        float widthRatio = (float)bitmap.getWidth() / maxWidth;
        float heightRatio = (float)bitmap.getHeight() / maxHeight;
        // Width constrained.
        if (widthRatio >= heightRatio) {
            width = maxWidth;
            height = (int)(((float)width / bitmap.getWidth()) * bitmap.getHeight());
        }
        // Height constrained.
        else {
            height = maxHeight;
            width = (int)(((float)height / bitmap.getHeight()) * bitmap.getWidth());
        }
        Bitmap scaledBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float ratioX = (float)width / bitmap.getWidth();
        float ratioY = (float)height / bitmap.getHeight();
        float middleX = width / 2.0f;
        float middleY = height / 2.0f;
        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bitmap, middleX - bitmap.getWidth() / 2, middleY - bitmap.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));
        return scaledBitmap;
    }
}
