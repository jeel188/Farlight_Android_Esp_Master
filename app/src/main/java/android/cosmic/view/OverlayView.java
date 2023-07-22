package android.cosmic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.cosmic.utils.CPreferences;
import android.cosmic.utils.SingleTapConfirm;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.cosmic.ui.MainActivity;
import com.cosmic.ui.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class OverlayView {
    static {
        System.loadLibrary("CosmicJNIUtility");
    }
    public static native void setValueBoolean(int feature, boolean about);
    public static native void GetInitCanvasDrawing(CanvasDrawingView canvasDrawingView, Canvas canvas);
    public static native void Range(int range);
    public static native void Location(int location);
    public static native void Target(int target);
    public static native void Trigger(int trigger);

    private static CPreferences cPreferences;
    private static Context context;
    private static View menuView;
    private static CanvasDrawingView canvasDrawingView;
    private static WindowManager windowManager;
    private static WindowManager.LayoutParams params, paramsCanvas;

    public OverlayView(Context ctx){
        context = ctx;
    }
    public static OverlayView init(Context context){
        cPreferences = new CPreferences(context);
        windowManager = MainWindowManager.getWindowManager(context);
        menuView = ViewInflater.getMainView(context);
        canvasDrawingView = new CanvasDrawingView(context);
        canvasDrawingView.setFPS(getValueInt("FRAME_RATE"));
        canvasDrawingView.runEsp();
        params = OverlayMenuWLParams.getParams();
        paramsCanvas = OverlayCanvasWLParams.getParams();
        return new OverlayView(context);
    }
    public void show(){
        windowManager.addView(menuView, params);
        windowManager.addView(canvasDrawingView, paramsCanvas);
        InitView();
    }
    private static LinearLayout controlView;
    private static RelativeLayout mainView;
    private static void InitView() {
        controlView = menuView.findViewById(R.id.layout_control);
        mainView = menuView.findViewById(R.id.layout_menu);
        menuView.findViewById(R.id.ic_close).setOnClickListener(v -> {
            controlView.setVisibility(View.VISIBLE);
            mainView.setVisibility(View.GONE);
        });
        final GestureDetector gestureDetector = new GestureDetector(context, new SingleTapConfirm());
        menuView.findViewById(R.id.base_layout).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    mainView.setVisibility(View.VISIBLE);
                    controlView.setVisibility(View.GONE);
                    return true;
                } else {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(menuView, params);
                            return true;
                    }
                    return false;
                }
            }
        });
        InitMenuView();
    }
    private static SeekBar seekbar_frame_rate;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private static void InitMenuView() {
        final CheckBox nationtext = mainView.findViewById(R.id.nationtext);
        final CheckBox nationimage = mainView.findViewById(R.id.nationimage);

        nationtext.setChecked(getShowBoolean((String) nationtext.getText()));
        nationtext.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setShowBoolean(String.valueOf(nationtext.getText()), nationtext.isChecked());
                if (isChecked) {
                    nationimage.setChecked(false);
                }
            }
        });

        nationimage.setChecked(getShowBoolean((String) nationimage.getText()));
        nationimage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setShowBoolean(String.valueOf(nationimage.getText()), nationimage.isChecked());
                if (isChecked) {
                    nationtext.setChecked(false);
                }
            }
        });

        final CheckBox Default = mainView.findViewById(R.id.defaultimg);
        final CheckBox Material = mainView.findViewById(R.id.meterial);
        final CheckBox MaterialDistance = mainView.findViewById(R.id.meterialdistance);

        Material.setChecked(getShowBoolean((String) Material.getText()));
        Material.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setShowBoolean(String.valueOf(Material.getText()), Material.isChecked());
                if (isChecked) {
                    Default.setChecked(false);
                    MaterialDistance.setChecked(false);
                }
            }
        });

        MaterialDistance.setChecked(getShowBoolean((String) MaterialDistance.getText()));
        MaterialDistance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setShowBoolean(String.valueOf(MaterialDistance.getText()), MaterialDistance.isChecked());
                if (isChecked) {
                    Default.setChecked(false);
                    Material.setChecked(false);
                }
            }
        });

        Default.setChecked(getShowBoolean((String) Default.getText()));
        Default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setShowBoolean(String.valueOf(Default.getText()), Default.isChecked());
                if (isChecked) {
                    Material.setChecked(false);
                    MaterialDistance.setChecked(false);
                }
            }
        });

        Button ItemsAll=mainView.findViewById(R.id.ItemsAll);

        ItemsAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox helmet_lv_1 = mainView.findViewById(R.id.helmet_lv_1);
                CheckBox helmet_lv_2 = mainView.findViewById(R.id.helmet_lv_2);
                CheckBox helmet_lv_3 = mainView.findViewById(R.id.helmet_lv_3);
                CheckBox vest_lv_1 = mainView.findViewById(R.id.vest_lv_1);
                CheckBox vest_lv_2 = mainView.findViewById(R.id.vest_lv_2);
                CheckBox vest_lv_3 = mainView.findViewById(R.id.vest_lv_3);
                CheckBox bagpack_lv_1 = mainView.findViewById(R.id.bagpack_lv_1);
                CheckBox bagpack_lv_2 = mainView.findViewById(R.id.bagpack_lv_2);
                CheckBox bagpack_lv_3 = mainView.findViewById(R.id.bagpack_lv_3);
                CheckBox adrenalline = mainView.findViewById(R.id.adrenalline);
                CheckBox paintkiller = mainView.findViewById(R.id.paintkiller);
                CheckBox energydrink = mainView.findViewById(R.id.energydrink);
                CheckBox bandage = mainView.findViewById(R.id.bandage);
                CheckBox firstaidkit = mainView.findViewById(R.id.firstaidkit);
                CheckBox medkit = mainView.findViewById(R.id.medkit);
                CheckBox _2x_scope = mainView.findViewById(R.id._2x_scope);
                CheckBox _3x_scope = mainView.findViewById(R.id._3x_scope);
                CheckBox _4x_scope = mainView.findViewById(R.id._4x_scope);
                CheckBox _6x_scope = mainView.findViewById(R.id._6x_scope);
                CheckBox _8x_scope = mainView.findViewById(R.id._8x_scope);
                CheckBox holo = mainView.findViewById(R.id.holo);
                CheckBox lazer = mainView.findViewById(R.id.lazer);
                CheckBox _5_56mm = mainView.findViewById(R.id._5_56mm);
                CheckBox _7_62mm = mainView.findViewById(R.id._7_62mm);
                CheckBox _300_magnum = mainView.findViewById(R.id._300_magnum);
                CheckBox _99mm = mainView.findViewById(R.id._99mm);
                CheckBox _45_acp = mainView.findViewById(R.id._45_acp);
                CheckBox _12_gauge = mainView.findViewById(R.id._12_gauge);
                CheckBox extended = mainView.findViewById(R.id.extended);
                CheckBox extended_quickdraw = mainView.findViewById(R.id.extended_quickdraw);
                CheckBox quickdraw = mainView.findViewById(R.id.quickdraw);
                CheckBox compensator = mainView.findViewById(R.id.compensator);
                CheckBox flashhider = mainView.findViewById(R.id.flashhider);
                CheckBox suppressor = mainView.findViewById(R.id.suppressor);
                CheckBox smoke = mainView.findViewById(R.id.smoke);
                CheckBox grenade = mainView.findViewById(R.id.grenade);
                CheckBox molotov = mainView.findViewById(R.id.molotov);
                CheckBox flashbang = mainView.findViewById(R.id.flashbang);
                CheckBox angled_foregrip = mainView.findViewById(R.id.angled_foregrip);
                CheckBox thumb_grip = mainView.findViewById(R.id.thumb_grip);
                CheckBox laser_sight = mainView.findViewById(R.id.laser_sight);
                CheckBox light_grip = mainView.findViewById(R.id.light_grip);
                CheckBox half_grip = mainView.findViewById(R.id.half_grip);
                CheckBox vertical_foregrip = mainView.findViewById(R.id.vertical_foregrip);
                CheckBox duckbill = mainView.findViewById(R.id.duckbill);
                CheckBox choke = mainView.findViewById(R.id.choke);
                CheckBox stock = mainView.findViewById(R.id.stock);
                CheckBox tactical_stock = mainView.findViewById(R.id.tactical_stock);
                CheckBox lootbox = mainView.findViewById(R.id.lootbox);
                CheckBox air_drop = mainView.findViewById(R.id.air_drop);
                CheckBox ghilliesuit = mainView.findViewById(R.id.ghilliesuit);

                helmet_lv_1.setChecked(true);
                helmet_lv_2.setChecked(true);
                helmet_lv_3.setChecked(true);
                vest_lv_1.setChecked(true);
                vest_lv_2.setChecked(true);
                vest_lv_3.setChecked(true);
                bagpack_lv_1.setChecked(true);
                bagpack_lv_2.setChecked(true);
                bagpack_lv_3.setChecked(true);
                adrenalline.setChecked(true);
                paintkiller.setChecked(true);
                energydrink.setChecked(true);
                bandage.setChecked(true);
                firstaidkit.setChecked(true);
                medkit.setChecked(true);
                _2x_scope.setChecked(true);
                _3x_scope.setChecked(true);
                _4x_scope.setChecked(true);
                _6x_scope.setChecked(true);
                _8x_scope.setChecked(true);
                holo.setChecked(true);
                lazer.setChecked(true);
                _5_56mm.setChecked(true);
                _7_62mm.setChecked(true);
                _300_magnum.setChecked(true);
                _99mm.setChecked(true);
                _45_acp.setChecked(true);
                _12_gauge.setChecked(true);
                extended.setChecked(true);
                extended_quickdraw.setChecked(true);
                quickdraw.setChecked(true);
                compensator.setChecked(true);
                flashhider.setChecked(true);
                suppressor.setChecked(true);
                smoke.setChecked(true);
                grenade.setChecked(true);
                molotov.setChecked(true);
                flashbang.setChecked(true);
                angled_foregrip.setChecked(true);
                thumb_grip.setChecked(true);
                laser_sight.setChecked(true);
                light_grip.setChecked(true);
                half_grip.setChecked(true);
                vertical_foregrip.setChecked(true);
                duckbill.setChecked(true);
                choke.setChecked(true);
                stock.setChecked(true);
                tactical_stock.setChecked(true);
                lootbox.setChecked(true);
                air_drop.setChecked(true);
                ghilliesuit.setChecked(true);
            }
        });

        Button ItemsNone=mainView.findViewById(R.id.ItemsNone);

        ItemsNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox helmet_lv_1 = mainView.findViewById(R.id.helmet_lv_1);
                CheckBox helmet_lv_2 = mainView.findViewById(R.id.helmet_lv_2);
                CheckBox helmet_lv_3 = mainView.findViewById(R.id.helmet_lv_3);
                CheckBox vest_lv_1 = mainView.findViewById(R.id.vest_lv_1);
                CheckBox vest_lv_2 = mainView.findViewById(R.id.vest_lv_2);
                CheckBox vest_lv_3 = mainView.findViewById(R.id.vest_lv_3);
                CheckBox bagpack_lv_1 = mainView.findViewById(R.id.bagpack_lv_1);
                CheckBox bagpack_lv_2 = mainView.findViewById(R.id.bagpack_lv_2);
                CheckBox bagpack_lv_3 = mainView.findViewById(R.id.bagpack_lv_3);
                CheckBox adrenalline = mainView.findViewById(R.id.adrenalline);
                CheckBox paintkiller = mainView.findViewById(R.id.paintkiller);
                CheckBox energydrink = mainView.findViewById(R.id.energydrink);
                CheckBox bandage = mainView.findViewById(R.id.bandage);
                CheckBox firstaidkit = mainView.findViewById(R.id.firstaidkit);
                CheckBox medkit = mainView.findViewById(R.id.medkit);
                CheckBox _2x_scope = mainView.findViewById(R.id._2x_scope);
                CheckBox _3x_scope = mainView.findViewById(R.id._3x_scope);
                CheckBox _4x_scope = mainView.findViewById(R.id._4x_scope);
                CheckBox _6x_scope = mainView.findViewById(R.id._6x_scope);
                CheckBox _8x_scope = mainView.findViewById(R.id._8x_scope);
                CheckBox holo = mainView.findViewById(R.id.holo);
                CheckBox lazer = mainView.findViewById(R.id.lazer);
                CheckBox _5_56mm = mainView.findViewById(R.id._5_56mm);
                CheckBox _7_62mm = mainView.findViewById(R.id._7_62mm);
                CheckBox _300_magnum = mainView.findViewById(R.id._300_magnum);
                CheckBox _99mm = mainView.findViewById(R.id._99mm);
                CheckBox _45_acp = mainView.findViewById(R.id._45_acp);
                CheckBox _12_gauge = mainView.findViewById(R.id._12_gauge);
                CheckBox extended = mainView.findViewById(R.id.extended);
                CheckBox extended_quickdraw = mainView.findViewById(R.id.extended_quickdraw);
                CheckBox quickdraw = mainView.findViewById(R.id.quickdraw);
                CheckBox compensator = mainView.findViewById(R.id.compensator);
                CheckBox flashhider = mainView.findViewById(R.id.flashhider);
                CheckBox suppressor = mainView.findViewById(R.id.suppressor);
                CheckBox smoke = mainView.findViewById(R.id.smoke);
                CheckBox grenade = mainView.findViewById(R.id.grenade);
                CheckBox molotov = mainView.findViewById(R.id.molotov);
                CheckBox flashbang = mainView.findViewById(R.id.flashbang);
                CheckBox angled_foregrip = mainView.findViewById(R.id.angled_foregrip);
                CheckBox thumb_grip = mainView.findViewById(R.id.thumb_grip);
                CheckBox laser_sight = mainView.findViewById(R.id.laser_sight);
                CheckBox light_grip = mainView.findViewById(R.id.light_grip);
                CheckBox half_grip = mainView.findViewById(R.id.half_grip);
                CheckBox vertical_foregrip = mainView.findViewById(R.id.vertical_foregrip);
                CheckBox duckbill = mainView.findViewById(R.id.duckbill);
                CheckBox choke = mainView.findViewById(R.id.choke);
                CheckBox stock = mainView.findViewById(R.id.stock);
                CheckBox tactical_stock = mainView.findViewById(R.id.tactical_stock);
                CheckBox lootbox = mainView.findViewById(R.id.lootbox);
                CheckBox air_drop = mainView.findViewById(R.id.air_drop);
                CheckBox ghilliesuit = mainView.findViewById(R.id.ghilliesuit);

                helmet_lv_1.setChecked(false);
                helmet_lv_2.setChecked(false);
                helmet_lv_3.setChecked(false);
                vest_lv_1.setChecked(false);
                vest_lv_2.setChecked(false);
                vest_lv_3.setChecked(false);
                bagpack_lv_1.setChecked(false);
                bagpack_lv_2.setChecked(false);
                bagpack_lv_3.setChecked(false);
                adrenalline.setChecked(false);
                paintkiller.setChecked(false);
                energydrink.setChecked(false);
                bandage.setChecked(false);
                firstaidkit.setChecked(false);
                medkit.setChecked(false);
                _2x_scope.setChecked(false);
                _3x_scope.setChecked(false);
                _4x_scope.setChecked(false);
                _6x_scope.setChecked(false);
                _8x_scope.setChecked(false);
                holo.setChecked(false);
                lazer.setChecked(false);
                _5_56mm.setChecked(false);
                _7_62mm.setChecked(false);
                _300_magnum.setChecked(false);
                _99mm.setChecked(false);
                _45_acp.setChecked(false);
                _12_gauge.setChecked(false);
                extended.setChecked(false);
                extended_quickdraw.setChecked(false);
                quickdraw.setChecked(false);
                compensator.setChecked(false);
                flashhider.setChecked(false);
                suppressor.setChecked(false);
                smoke.setChecked(false);
                grenade.setChecked(false);
                molotov.setChecked(false);
                flashbang.setChecked(false);
                angled_foregrip.setChecked(false);
                thumb_grip.setChecked(false);
                laser_sight.setChecked(false);
                light_grip.setChecked(false);
                half_grip.setChecked(false);
                vertical_foregrip.setChecked(false);
                duckbill.setChecked(false);
                choke.setChecked(false);
                stock.setChecked(false);
                tactical_stock.setChecked(false);
                lootbox.setChecked(false);
                air_drop.setChecked(false);
                ghilliesuit.setChecked(false);
            }
        });

        Button VehiclesAll=mainView.findViewById(R.id.VehiclesAll);

        VehiclesAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox buggy = mainView.findViewById(R.id.buggy);
                CheckBox uaz = mainView.findViewById(R.id.uaz);
                CheckBox motorcycleCart = mainView.findViewById(R.id.motorcycleCart);
                CheckBox motorcycle = mainView.findViewById(R.id.motorcycle);
                CheckBox dacia = mainView.findViewById(R.id.dacia);
                CheckBox aquaRail = mainView.findViewById(R.id.aquaRail);
                CheckBox pg117 = mainView.findViewById(R.id.pg117);
                CheckBox miniBus = mainView.findViewById(R.id.miniBus);
                CheckBox mirado = mainView.findViewById(R.id.mirado);
                CheckBox scooter = mainView.findViewById(R.id.scooter);
                CheckBox rony = mainView.findViewById(R.id.rony);
                CheckBox snowbike = mainView.findViewById(R.id.snowbike);
                CheckBox snowmobile = mainView.findViewById(R.id.snowmobile);
                CheckBox tuk = mainView.findViewById(R.id.tuk);
                CheckBox brdm = mainView.findViewById(R.id.brdm);
                CheckBox ladaNiva = mainView.findViewById(R.id.ladaNiva);
                CheckBox airDropPlane = mainView.findViewById(R.id.airDropPlane);

                buggy.setChecked(true);
                uaz.setChecked(true);
                motorcycleCart.setChecked(true);
                motorcycle.setChecked(true);
                dacia.setChecked(true);
                aquaRail.setChecked(true);
                pg117.setChecked(true);
                miniBus.setChecked(true);
                mirado.setChecked(true);
                scooter.setChecked(true);
                rony.setChecked(true);
                snowbike.setChecked(true);
                snowmobile.setChecked(true);
                tuk.setChecked(true);
                brdm.setChecked(true);
                ladaNiva.setChecked(true);
                airDropPlane.setChecked(true);

            }
        });

        Button VehiclesNone=mainView.findViewById(R.id.VehiclesNone);

        VehiclesNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox buggy = mainView.findViewById(R.id.buggy);
                CheckBox uaz = mainView.findViewById(R.id.uaz);
                CheckBox motorcycleCart = mainView.findViewById(R.id.motorcycleCart);
                CheckBox motorcycle = mainView.findViewById(R.id.motorcycle);
                CheckBox dacia = mainView.findViewById(R.id.dacia);
                CheckBox aquaRail = mainView.findViewById(R.id.aquaRail);
                CheckBox pg117 = mainView.findViewById(R.id.pg117);
                CheckBox miniBus = mainView.findViewById(R.id.miniBus);
                CheckBox mirado = mainView.findViewById(R.id.mirado);
                CheckBox scooter = mainView.findViewById(R.id.scooter);
                CheckBox rony = mainView.findViewById(R.id.rony);
                CheckBox snowbike = mainView.findViewById(R.id.snowbike);
                CheckBox snowmobile = mainView.findViewById(R.id.snowmobile);
                CheckBox tuk = mainView.findViewById(R.id.tuk);
                CheckBox brdm = mainView.findViewById(R.id.brdm);
                CheckBox ladaNiva = mainView.findViewById(R.id.ladaNiva);
                CheckBox airDropPlane = mainView.findViewById(R.id.airDropPlane);

                buggy.setChecked(false);
                uaz.setChecked(false);
                motorcycleCart.setChecked(false);
                motorcycle.setChecked(false);
                dacia.setChecked(false);
                aquaRail.setChecked(false);
                pg117.setChecked(false);
                miniBus.setChecked(false);
                mirado.setChecked(false);
                scooter.setChecked(false);
                rony.setChecked(false);
                snowbike.setChecked(false);
                snowmobile.setChecked(false);
                tuk.setChecked(false);
                brdm.setChecked(false);
                ladaNiva.setChecked(false);
                airDropPlane.setChecked(false);

            }
        });

        TextView playerBtn = mainView.findViewById(R.id.playerBtn);
        TextView itemBtn = mainView.findViewById(R.id.itemBtn);
        TextView weaponBtn = mainView.findViewById(R.id.weaponBtn);
        TextView vehicleBtn = mainView.findViewById(R.id.vehicleBtn);
        final LinearLayout player =mainView.findViewById(R.id.players);
        final LinearLayout items =mainView.findViewById(R.id.items);
        final LinearLayout weapons =mainView.findViewById(R.id.weapons);
        final LinearLayout vehicle =mainView.findViewById(R.id.vehicles);

        playerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                weaponBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                playerBtn.setBackgroundColor(Color.parseColor("#030B17"));
                vehicleBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                playerBtn.setTextColor(Color.parseColor("#0091EA"));
                itemBtn.setTextColor(Color.BLACK);
                weaponBtn.setTextColor(Color.BLACK);
                vehicleBtn.setTextColor(Color.BLACK);
                items.setVisibility(View.GONE);
                weapons.setVisibility(View.GONE);
                player.setVisibility(View.VISIBLE);
                vehicle.setVisibility(View.GONE);
            }
        });

        itemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBtn.setBackgroundColor(Color.parseColor("#030B17"));
                playerBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                weaponBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                vehicleBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                playerBtn.setTextColor(Color.BLACK);
                weaponBtn.setTextColor(Color.BLACK);
                itemBtn.setTextColor(Color.parseColor("#0091EA"));
                vehicleBtn.setTextColor(Color.BLACK);
                items.setVisibility(View.VISIBLE);
                player.setVisibility(View.GONE);
                weapons.setVisibility(View.GONE);
                vehicle.setVisibility(View.GONE);
            }
        });

        weaponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weaponBtn.setBackgroundColor(Color.parseColor("#030B17"));
                playerBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                itemBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                vehicleBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                playerBtn.setTextColor(Color.BLACK);
                itemBtn.setTextColor(Color.BLACK);
                weaponBtn.setTextColor(Color.parseColor("#0091EA"));
                vehicleBtn.setTextColor(Color.BLACK);
                items.setVisibility(View.GONE);
                player.setVisibility(View.GONE);
                weapons.setVisibility(View.VISIBLE);
                vehicle.setVisibility(View.GONE);
            }
        });

        vehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                playerBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                weaponBtn.setBackgroundColor(Color.parseColor("#D5D5D5"));
                vehicleBtn.setBackgroundColor(Color.parseColor("#030B17"));
                playerBtn.setTextColor(Color.BLACK);
                itemBtn.setTextColor(Color.BLACK);
                weaponBtn.setTextColor(Color.BLACK);
                vehicleBtn.setTextColor(Color.parseColor("#0091EA"));
                items.setVisibility(View.GONE);
                player.setVisibility(View.GONE);
                weapons.setVisibility(View.GONE);
                vehicle.setVisibility(View.VISIBLE);
            }
        });

        ScrollView layout_ar = mainView.findViewById(R.id.layout_ar);
        ScrollView layout_basr = mainView.findViewById(R.id.layout_basr);
        ScrollView layout_asr = mainView.findViewById(R.id.layout_asr);
        ScrollView layout_smg = mainView.findViewById(R.id.layout_smg);
        ScrollView layout_sg = mainView.findViewById(R.id.layout_sg);
        ScrollView layout_lmg = mainView.findViewById(R.id.layout_lmg);
        ScrollView layout_pistol = mainView.findViewById(R.id.layout_pistol);
        ScrollView layout_melee = mainView.findViewById(R.id.layout_melee);
        ScrollView layout_other = mainView.findViewById(R.id.layout_other);
        TextView textview_ar = mainView.findViewById(R.id.textview_ar);
        TextView textview_basr = mainView.findViewById(R.id.textview_basr);
        TextView textview_asr = mainView.findViewById(R.id.textview_asr);
        TextView textview_smg = mainView.findViewById(R.id.textview_smg);
        TextView textview_sg = mainView.findViewById(R.id.textview_sg);
        TextView textview_lmg = mainView.findViewById(R.id.textview_lmg);
        TextView textview_pistol = mainView.findViewById(R.id.textview_pistol);
        TextView textview_melee = mainView.findViewById(R.id.textview_melee);
        TextView textview_other = mainView.findViewById(R.id.textview_other);
        View.OnClickListener onClickWeapon = view -> {
            switch (view.getId()){
                case R.id.textview_ar:
                    layout_ar.setVisibility(VISIBLE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_basr:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(VISIBLE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_asr:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(VISIBLE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_smg:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(VISIBLE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_sg:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(VISIBLE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_lmg:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(VISIBLE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_pistol:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(VISIBLE);
                    layout_melee.setVisibility(GONE);
                    break;
                case R.id.textview_melee:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(VISIBLE);
                    layout_other.setVisibility(GONE);
                    break;
                case R.id.textview_other:
                    layout_ar.setVisibility(GONE);
                    layout_basr.setVisibility(GONE);
                    layout_asr.setVisibility(GONE);
                    layout_smg.setVisibility(GONE);
                    layout_sg.setVisibility(GONE);
                    layout_lmg.setVisibility(GONE);
                    layout_pistol.setVisibility(GONE);
                    layout_melee.setVisibility(GONE);
                    layout_other.setVisibility(VISIBLE);
                    break;
            }
        };
        textview_ar.setOnClickListener(onClickWeapon);
        textview_basr.setOnClickListener(onClickWeapon);
        textview_asr.setOnClickListener(onClickWeapon);
        textview_smg.setOnClickListener(onClickWeapon);
        textview_sg.setOnClickListener(onClickWeapon);
        textview_lmg.setOnClickListener(onClickWeapon);
        textview_pistol.setOnClickListener(onClickWeapon);
        textview_melee.setOnClickListener(onClickWeapon);
        textview_other.setOnClickListener(onClickWeapon);

        CheckBox akm = mainView.findViewById(R.id.akm);
        CheckBox m16a4 = mainView.findViewById(R.id.m16a4);
        CheckBox scarl = mainView.findViewById(R.id.scarl);
        CheckBox m416 = mainView.findViewById(R.id.m416);
        CheckBox groza = mainView.findViewById(R.id.groza);
        CheckBox aug = mainView.findViewById(R.id.aug);
        CheckBox qbz = mainView.findViewById(R.id.qbz);
        CheckBox m762 = mainView.findViewById(R.id.m762);
        CheckBox mk47 = mainView.findViewById(R.id.mk47);
        CheckBox _636c = mainView.findViewById(R.id._636c);
        CompoundButton.OnCheckedChangeListener onCheckedAR = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.akm:
                    setShowBoolean(akm.getText().toString(), isChecked);
                    break;
                case R.id.m16a4:
                    setShowBoolean(m16a4.getText().toString(), isChecked);
                    break;
                case R.id.scarl:
                    setShowBoolean(scarl.getText().toString(), isChecked);
                    break;
                case R.id.m416:
                    setShowBoolean(m416.getText().toString(), isChecked);
                    break;
                case R.id.groza:
                    setShowBoolean(groza.getText().toString(), isChecked);
                    break;
                case R.id.aug:
                    setShowBoolean(aug.getText().toString(), isChecked);
                    break;
                case R.id.qbz:
                    setShowBoolean(qbz.getText().toString(), isChecked);
                    break;
                case R.id.m762:
                    setShowBoolean(m762.getText().toString(), isChecked);
                    break;
                case R.id.mk47:
                    setShowBoolean(mk47.getText().toString(), isChecked);
                    break;
                case R.id._636c:
                    setShowBoolean(_636c.getText().toString(), isChecked);
                    break;
            }
        };
        akm.setOnCheckedChangeListener(onCheckedAR);
        m16a4.setOnCheckedChangeListener(onCheckedAR);
        scarl.setOnCheckedChangeListener(onCheckedAR);
        m416.setOnCheckedChangeListener(onCheckedAR);
        groza.setOnCheckedChangeListener(onCheckedAR);
        aug.setOnCheckedChangeListener(onCheckedAR);
        qbz.setOnCheckedChangeListener(onCheckedAR);
        m762.setOnCheckedChangeListener(onCheckedAR);
        mk47.setOnCheckedChangeListener(onCheckedAR);
        _636c.setOnCheckedChangeListener(onCheckedAR);
        akm.setChecked(getShowBoolean(akm.getText().toString()));
        m16a4.setChecked(getShowBoolean(m16a4.getText().toString()));
        scarl.setChecked(getShowBoolean(scarl.getText().toString()));
        m416.setChecked(getShowBoolean(m416.getText().toString()));
        groza.setChecked(getShowBoolean(groza.getText().toString()));
        aug.setChecked(getShowBoolean(aug.getText().toString()));
        qbz.setChecked(getShowBoolean(qbz.getText().toString()));
        m762.setChecked(getShowBoolean(m762.getText().toString()));
        mk47.setChecked(getShowBoolean(mk47.getText().toString()));
        _636c.setChecked(getShowBoolean(_636c.getText().toString()));


        CheckBox kar98k = mainView.findViewById(R.id.kar98k);
        CheckBox m24 = mainView.findViewById(R.id.m24);
        CheckBox awm = mainView.findViewById(R.id.awm);
        CheckBox win94 = mainView.findViewById(R.id.win94);
        CompoundButton.OnCheckedChangeListener onCheckedBASR = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.kar98k:
                    setShowBoolean(kar98k.getText().toString(), isChecked);
                    break;
                case R.id.m24:
                    setShowBoolean(m24.getText().toString(), isChecked);
                    break;
                case R.id.awm:
                    setShowBoolean(awm.getText().toString(), isChecked);
                    break;
                case R.id.win94:
                    setShowBoolean(win94.getText().toString(), isChecked);
                    break;
            }
        };
        kar98k.setOnCheckedChangeListener(onCheckedBASR);
        m24.setOnCheckedChangeListener(onCheckedBASR);
        awm.setOnCheckedChangeListener(onCheckedBASR);
        win94.setOnCheckedChangeListener(onCheckedBASR);
        kar98k.setChecked(getShowBoolean(kar98k.getText().toString()));
        m24.setChecked(getShowBoolean(m24.getText().toString()));
        awm.setChecked(getShowBoolean(awm.getText().toString()));
        win94.setChecked(getShowBoolean(win94.getText().toString()));

        CheckBox sks = mainView.findViewById(R.id.sks);
        CheckBox vss = mainView.findViewById(R.id.vss);
        CheckBox mini14 = mainView.findViewById(R.id.mini14);
        CheckBox mk14 = mainView.findViewById(R.id.mk14);
        CheckBox slr = mainView.findViewById(R.id.slr);
        CheckBox qbu = mainView.findViewById(R.id.qbu);
        CompoundButton.OnCheckedChangeListener onCheckedASR = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.sks:
                    setShowBoolean(sks.getText().toString(), isChecked);
                    break;
                case R.id.vss:
                    setShowBoolean(vss.getText().toString(), isChecked);
                    break;
                case R.id.mini14:
                    setShowBoolean(mini14.getText().toString(), isChecked);
                    break;
                case R.id.mk14:
                    setShowBoolean(mk14.getText().toString(), isChecked);
                    break;
                case R.id.slr:
                    setShowBoolean(slr.getText().toString(), isChecked);
                    break;
                case R.id.qbu:
                    setShowBoolean(qbu.getText().toString(), isChecked);
                    break;
            }
        };
        sks.setOnCheckedChangeListener(onCheckedASR);
        vss.setOnCheckedChangeListener(onCheckedASR);
        mini14.setOnCheckedChangeListener(onCheckedASR);
        mk14.setOnCheckedChangeListener(onCheckedASR);
        slr.setOnCheckedChangeListener(onCheckedASR);
        qbu.setOnCheckedChangeListener(onCheckedASR);
        sks.setChecked(getShowBoolean(sks.getText().toString()));
        vss.setChecked(getShowBoolean(vss.getText().toString()));
        mini14.setChecked(getShowBoolean(mini14.getText().toString()));
        mk14.setChecked(getShowBoolean(mk14.getText().toString()));
        slr.setChecked(getShowBoolean(slr.getText().toString()));
        qbu.setChecked(getShowBoolean(qbu.getText().toString()));

        CheckBox uzi = mainView.findViewById(R.id.uzi);
        CheckBox ump45 = mainView.findViewById(R.id.ump45);
        CheckBox vector = mainView.findViewById(R.id.vector);
        CheckBox thompson = mainView.findViewById(R.id.thompson);
        CheckBox mp5k = mainView.findViewById(R.id.mp5k);
        CompoundButton.OnCheckedChangeListener onCheckedSMG = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.uzi:
                    setShowBoolean(uzi.getText().toString(), isChecked);
                    break;
                case R.id.ump45:
                    setShowBoolean(ump45.getText().toString(), isChecked);
                    break;
                case R.id.vector:
                    setShowBoolean(vector.getText().toString(), isChecked);
                    break;
                case R.id.thompson:
                    setShowBoolean(thompson.getText().toString(), isChecked);
                    break;
                case R.id.mp5k:
                    setShowBoolean(mp5k.getText().toString(), isChecked);
                    break;
            }
        };
        uzi.setOnCheckedChangeListener(onCheckedSMG);
        ump45.setOnCheckedChangeListener(onCheckedSMG);
        vector.setOnCheckedChangeListener(onCheckedSMG);
        thompson.setOnCheckedChangeListener(onCheckedSMG);
        mp5k.setOnCheckedChangeListener(onCheckedSMG);
        uzi.setChecked(getShowBoolean(uzi.getText().toString()));
        ump45.setChecked(getShowBoolean(ump45.getText().toString()));
        vector.setChecked(getShowBoolean(vector.getText().toString()));
        thompson.setChecked(getShowBoolean(thompson.getText().toString()));
        mp5k.setChecked(getShowBoolean(mp5k.getText().toString()));

        CheckBox s686 = mainView.findViewById(R.id.s686);
        CheckBox s1897 = mainView.findViewById(R.id.s1897);
        CheckBox s12k = mainView.findViewById(R.id.s12k);
        CheckBox dbs = mainView.findViewById(R.id.dbs);
        CompoundButton.OnCheckedChangeListener onCheckedSG = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.s686:
                    setShowBoolean(s686.getText().toString(), isChecked);
                    break;
                case R.id.s1897:
                    setShowBoolean(s1897.getText().toString(), isChecked);
                    break;
                case R.id.s12k:
                    setShowBoolean(s12k.getText().toString(), isChecked);
                    break;
                case R.id.dbs:
                    setShowBoolean(dbs.getText().toString(), isChecked);
                    break;
            }
        };
        s686.setOnCheckedChangeListener(onCheckedSG);
        s1897.setOnCheckedChangeListener(onCheckedSG);
        s12k.setOnCheckedChangeListener(onCheckedSG);
        dbs.setOnCheckedChangeListener(onCheckedSG);

        s686.setChecked(getShowBoolean(s686.getText().toString()));
        s1897.setChecked(getShowBoolean(s1897.getText().toString()));
        s12k.setChecked(getShowBoolean(s12k.getText().toString()));
        dbs.setChecked(getShowBoolean(dbs.getText().toString()));


        CheckBox m249 = mainView.findViewById(R.id.m249);
        CheckBox dp28 = mainView.findViewById(R.id.dp28);
        CompoundButton.OnCheckedChangeListener onCheckedLMG = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.m249:
                    setShowBoolean(m249.getText().toString(), isChecked);
                    break;
                case R.id.dp28:
                    setShowBoolean(dp28.getText().toString(), isChecked);
                    break;
            }
        };
        m249.setOnCheckedChangeListener(onCheckedLMG);
        dp28.setOnCheckedChangeListener(onCheckedLMG);

        m249.setChecked(getShowBoolean(m249.getText().toString()));
        dp28.setChecked(getShowBoolean(dp28.getText().toString()));


        CheckBox p92 = mainView.findViewById(R.id.p92);
        CheckBox p1911 = mainView.findViewById(R.id.p1911);
        CheckBox r1895 = mainView.findViewById(R.id.r1895);
        CheckBox p18c = mainView.findViewById(R.id.p18c);
        CheckBox r45 = mainView.findViewById(R.id.r45);
        CheckBox sawedoff = mainView.findViewById(R.id.sawedoff);
        CheckBox flaregun = mainView.findViewById(R.id.flaregun);
        CheckBox deserteagle = mainView.findViewById(R.id.deserteagle);
        CompoundButton.OnCheckedChangeListener onCheckedPISTOL = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.p92:
                    setShowBoolean(p92.getText().toString(), isChecked);
                    break;
                case R.id.p1911:
                    setShowBoolean(p1911.getText().toString(), isChecked);
                    break;
                case R.id.r1895:
                    setShowBoolean(r1895.getText().toString(), isChecked);
                    break;
                case R.id.p18c:
                    setShowBoolean(p18c.getText().toString(), isChecked);
                    break;
                case R.id.r45:
                    setShowBoolean(r45.getText().toString(), isChecked);
                    break;
                case R.id.sawedoff:
                    setShowBoolean(sawedoff.getText().toString(), isChecked);
                    break;
                case R.id.flaregun:
                    setShowBoolean(flaregun.getText().toString(), isChecked);
                    break;
                case R.id.deserteagle:
                    setShowBoolean(deserteagle.getText().toString(), isChecked);
                    break;
            }
        };
        p92.setOnCheckedChangeListener(onCheckedPISTOL);
        p1911.setOnCheckedChangeListener(onCheckedPISTOL);
        r1895.setOnCheckedChangeListener(onCheckedPISTOL);
        p18c.setOnCheckedChangeListener(onCheckedPISTOL);
        r45.setOnCheckedChangeListener(onCheckedPISTOL);
        sawedoff.setOnCheckedChangeListener(onCheckedPISTOL);
        flaregun.setOnCheckedChangeListener(onCheckedPISTOL);
        deserteagle.setOnCheckedChangeListener(onCheckedPISTOL);

        p92.setChecked(getShowBoolean(p92.getText().toString()));
        p1911.setChecked(getShowBoolean(p1911.getText().toString()));
        r1895.setChecked(getShowBoolean(r1895.getText().toString()));
        p18c.setChecked(getShowBoolean(p18c.getText().toString()));
        r45.setChecked(getShowBoolean(r45.getText().toString()));
        sawedoff.setChecked(getShowBoolean(sawedoff.getText().toString()));
        flaregun.setChecked(getShowBoolean(flaregun.getText().toString()));
        deserteagle.setChecked(getShowBoolean(deserteagle.getText().toString()));

        CheckBox machete = mainView.findViewById(R.id.machete);
        CheckBox crowbar = mainView.findViewById(R.id.crowbar);
        CheckBox sickle = mainView.findViewById(R.id.sickle);
        CheckBox pan = mainView.findViewById(R.id.pan);
        CompoundButton.OnCheckedChangeListener onCheckedMELEE = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.machete:
                    setShowBoolean(machete.getText().toString(), isChecked);
                    break;
                case R.id.crowbar:
                    setShowBoolean(crowbar.getText().toString(), isChecked);
                    break;
                case R.id.sickle:
                    setShowBoolean(sickle.getText().toString(), isChecked);
                    break;
                case R.id.pan:
                    setShowBoolean(pan.getText().toString(), isChecked);
                    break;
            }
        };
        machete.setOnCheckedChangeListener(onCheckedMELEE);
        crowbar.setOnCheckedChangeListener(onCheckedMELEE);
        sickle.setOnCheckedChangeListener(onCheckedMELEE);
        pan.setOnCheckedChangeListener(onCheckedMELEE);

        machete.setChecked(getShowBoolean(machete.getText().toString()));
        crowbar.setChecked(getShowBoolean(crowbar.getText().toString()));
        sickle.setChecked(getShowBoolean(sickle.getText().toString()));
        pan.setChecked(getShowBoolean(pan.getText().toString()));


        CheckBox crossbow = mainView.findViewById(R.id.crossbow);
        crossbow.setOnCheckedChangeListener((compoundButton, isChecked) -> setShowBoolean(crossbow.getText().toString(), isChecked));
        crossbow.setChecked(getShowBoolean(crossbow.getText().toString()));

        CheckBox helmet_lv_1 = mainView.findViewById(R.id.helmet_lv_1);
        CheckBox helmet_lv_2 = mainView.findViewById(R.id.helmet_lv_2);
        CheckBox helmet_lv_3 = mainView.findViewById(R.id.helmet_lv_3);
        CheckBox vest_lv_1 = mainView.findViewById(R.id.vest_lv_1);
        CheckBox vest_lv_2 = mainView.findViewById(R.id.vest_lv_2);
        CheckBox vest_lv_3 = mainView.findViewById(R.id.vest_lv_3);
        CheckBox bagpack_lv_1 = mainView.findViewById(R.id.bagpack_lv_1);
        CheckBox bagpack_lv_2 = mainView.findViewById(R.id.bagpack_lv_2);
        CheckBox bagpack_lv_3 = mainView.findViewById(R.id.bagpack_lv_3);
        CheckBox adrenalline = mainView.findViewById(R.id.adrenalline);
        CheckBox paintkiller = mainView.findViewById(R.id.paintkiller);
        CheckBox energydrink = mainView.findViewById(R.id.energydrink);
        CheckBox bandage = mainView.findViewById(R.id.bandage);
        CheckBox firstaidkit = mainView.findViewById(R.id.firstaidkit);
        CheckBox medkit = mainView.findViewById(R.id.medkit);
        CheckBox _2x_scope = mainView.findViewById(R.id._2x_scope);
        CheckBox _3x_scope = mainView.findViewById(R.id._3x_scope);
        CheckBox _4x_scope = mainView.findViewById(R.id._4x_scope);
        CheckBox _6x_scope = mainView.findViewById(R.id._6x_scope);
        CheckBox _8x_scope = mainView.findViewById(R.id._8x_scope);
        CheckBox holo = mainView.findViewById(R.id.holo);
        CheckBox lazer = mainView.findViewById(R.id.lazer);
        CheckBox _5_56mm = mainView.findViewById(R.id._5_56mm);
        CheckBox _7_62mm = mainView.findViewById(R.id._7_62mm);
        CheckBox _300_magnum = mainView.findViewById(R.id._300_magnum);
        CheckBox _99mm = mainView.findViewById(R.id._99mm);
        CheckBox _45_acp = mainView.findViewById(R.id._45_acp);
        CheckBox _12_gauge = mainView.findViewById(R.id._12_gauge);
        CheckBox extended = mainView.findViewById(R.id.extended);
        CheckBox extended_quickdraw = mainView.findViewById(R.id.extended_quickdraw);
        CheckBox quickdraw = mainView.findViewById(R.id.quickdraw);
        CheckBox compensator = mainView.findViewById(R.id.compensator);
        CheckBox flashhider = mainView.findViewById(R.id.flashhider);
        CheckBox suppressor = mainView.findViewById(R.id.suppressor);
        CheckBox smoke = mainView.findViewById(R.id.smoke);
        CheckBox grenade = mainView.findViewById(R.id.grenade);
        CheckBox molotov = mainView.findViewById(R.id.molotov);
        CheckBox flashbang = mainView.findViewById(R.id.flashbang);
        CheckBox angled_foregrip = mainView.findViewById(R.id.angled_foregrip);
        CheckBox thumb_grip = mainView.findViewById(R.id.thumb_grip);
        CheckBox laser_sight = mainView.findViewById(R.id.laser_sight);
        CheckBox light_grip = mainView.findViewById(R.id.light_grip);
        CheckBox half_grip = mainView.findViewById(R.id.half_grip);
        CheckBox vertical_foregrip = mainView.findViewById(R.id.vertical_foregrip);
        CheckBox duckbill = mainView.findViewById(R.id.duckbill);
        CheckBox choke = mainView.findViewById(R.id.choke);
        CheckBox stock = mainView.findViewById(R.id.stock);
        CheckBox tactical_stock = mainView.findViewById(R.id.tactical_stock);
        CheckBox lootbox = mainView.findViewById(R.id.lootbox);
        CheckBox air_drop = mainView.findViewById(R.id.air_drop);
        CheckBox ghilliesuit = mainView.findViewById(R.id.ghilliesuit);
        CompoundButton.OnCheckedChangeListener onCheckedItems = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()){
                case R.id.helmet_lv_1:
                    setShowBoolean(helmet_lv_1.getText().toString(), isChecked);
                    break;
                case R.id.helmet_lv_2:
                    setShowBoolean(helmet_lv_2.getText().toString(), isChecked);
                    break;
                case R.id.helmet_lv_3:
                    setShowBoolean(helmet_lv_3.getText().toString(), isChecked);
                    break;
                case R.id.vest_lv_1:
                    setShowBoolean(vest_lv_1.getText().toString(), isChecked);
                    break;
                case R.id.vest_lv_2:
                    setShowBoolean(vest_lv_2.getText().toString(), isChecked);
                    break;
                case R.id.vest_lv_3:
                    setShowBoolean(vest_lv_3.getText().toString(), isChecked);
                    break;
                case R.id.bagpack_lv_1:
                    setShowBoolean(bagpack_lv_1.getText().toString(), isChecked);
                    break;
                case R.id.bagpack_lv_2:
                    setShowBoolean(bagpack_lv_2.getText().toString(), isChecked);
                    break;
                case R.id.bagpack_lv_3:
                    setShowBoolean(bagpack_lv_3.getText().toString(), isChecked);
                    break;
                case R.id.adrenalline:
                    setShowBoolean(adrenalline.getText().toString(), isChecked);
                    break;
                case R.id.paintkiller:
                    setShowBoolean(paintkiller.getText().toString(), isChecked);
                    break;
                case R.id.energydrink:
                    setShowBoolean(energydrink.getText().toString(), isChecked);
                    break;
                case R.id.bandage:
                    setShowBoolean(bandage.getText().toString(), isChecked);
                    break;
                case R.id.firstaidkit:
                    setShowBoolean(firstaidkit.getText().toString(), isChecked);
                    break;
                case R.id.medkit:
                    setShowBoolean(medkit.getText().toString(), isChecked);
                    break;
                case R.id._2x_scope:
                    setShowBoolean(_2x_scope.getText().toString(), isChecked);
                    break;
                case R.id._3x_scope:
                    setShowBoolean(_3x_scope.getText().toString(), isChecked);
                    break;
                case R.id._4x_scope:
                    setShowBoolean(_4x_scope.getText().toString(), isChecked);
                    break;
                case R.id._6x_scope:
                    setShowBoolean(_6x_scope.getText().toString(), isChecked);
                    break;
                case R.id._8x_scope:
                    setShowBoolean(_8x_scope.getText().toString(), isChecked);
                    break;
                case R.id.holo:
                    setShowBoolean(holo.getText().toString(), isChecked);
                    break;
                case R.id.lazer:
                    setShowBoolean(lazer.getText().toString(), isChecked);
                    break;
                case R.id._5_56mm:
                    setShowBoolean(_5_56mm.getText().toString(), isChecked);
                    break;
                case R.id._7_62mm:
                    setShowBoolean(_7_62mm.getText().toString(), isChecked);
                    break;
                case R.id._300_magnum:
                    setShowBoolean(_300_magnum.getText().toString(), isChecked);
                    break;
                case R.id._99mm:
                    setShowBoolean(_99mm.getText().toString(), isChecked);
                    break;
                case R.id._45_acp:
                    setShowBoolean(_45_acp.getText().toString(), isChecked);
                    break;
                case R.id._12_gauge:
                    setShowBoolean(_12_gauge.getText().toString(), isChecked);
                    break;
                case R.id.extended:
                    setShowBoolean(extended.getText().toString(), isChecked);
                    break;
                case R.id.extended_quickdraw:
                    setShowBoolean(extended_quickdraw.getText().toString(), isChecked);
                    break;
                case R.id.quickdraw:
                    setShowBoolean(quickdraw.getText().toString(), isChecked);
                    break;
                case R.id.compensator:
                    setShowBoolean(compensator.getText().toString(), isChecked);
                    break;
                case R.id.flashhider:
                    setShowBoolean(flashhider.getText().toString(), isChecked);
                    break;
                case R.id.suppressor:
                    setShowBoolean(suppressor.getText().toString(), isChecked);
                    break;
                case R.id.smoke:
                    setShowBoolean(smoke.getText().toString(), isChecked);
                    break;
                case R.id.grenade:
                    setShowBoolean(grenade.getText().toString(), isChecked);
                    break;
                case R.id.molotov:
                    setShowBoolean(molotov.getText().toString(), isChecked);
                    break;
                case R.id.flashbang:
                    setShowBoolean(flashbang.getText().toString(), isChecked);
                    break;
                case R.id.angled_foregrip:
                    setShowBoolean(angled_foregrip.getText().toString(), isChecked);
                    break;
                case R.id.thumb_grip:
                    setShowBoolean(thumb_grip.getText().toString(), isChecked);
                    break;
                case R.id.laser_sight:
                    setShowBoolean(laser_sight.getText().toString(), isChecked);
                    break;
                case R.id.light_grip:
                    setShowBoolean(light_grip.getText().toString(), isChecked);
                    break;
                case R.id.half_grip:
                    setShowBoolean(half_grip.getText().toString(), isChecked);
                    break;
                case R.id.vertical_foregrip:
                    setShowBoolean(vertical_foregrip.getText().toString(), isChecked);
                    break;
                case R.id.duckbill:
                    setShowBoolean(duckbill.getText().toString(), isChecked);
                    break;
                case R.id.choke:
                    setShowBoolean(choke.getText().toString(), isChecked);
                    break;
                case R.id.stock:
                    setShowBoolean(stock.getText().toString(), isChecked);
                    break;
                case R.id.tactical_stock:
                    setShowBoolean(tactical_stock.getText().toString(), isChecked);
                    break;
                case R.id.lootbox:
                    setShowBoolean(lootbox.getText().toString(), isChecked);
                    setValueBoolean(13, isChecked);
                    break;
                case R.id.air_drop:
                    setShowBoolean(air_drop.getText().toString(), isChecked);
                    break;
                case R.id.ghilliesuit:
                    setShowBoolean(ghilliesuit.getText().toString(), isChecked);
                    break;
            }
        };
        helmet_lv_1.setOnCheckedChangeListener(onCheckedItems);
        helmet_lv_2.setOnCheckedChangeListener(onCheckedItems);
        helmet_lv_3.setOnCheckedChangeListener(onCheckedItems);
        vest_lv_1.setOnCheckedChangeListener(onCheckedItems);
        vest_lv_2.setOnCheckedChangeListener(onCheckedItems);
        vest_lv_3.setOnCheckedChangeListener(onCheckedItems);
        bagpack_lv_1.setOnCheckedChangeListener(onCheckedItems);
        bagpack_lv_2.setOnCheckedChangeListener(onCheckedItems);
        bagpack_lv_3.setOnCheckedChangeListener(onCheckedItems);
        adrenalline.setOnCheckedChangeListener(onCheckedItems);
        paintkiller.setOnCheckedChangeListener(onCheckedItems);
        energydrink.setOnCheckedChangeListener(onCheckedItems);
        bandage.setOnCheckedChangeListener(onCheckedItems);
        firstaidkit.setOnCheckedChangeListener(onCheckedItems);
        medkit.setOnCheckedChangeListener(onCheckedItems);
        _2x_scope.setOnCheckedChangeListener(onCheckedItems);
        _3x_scope.setOnCheckedChangeListener(onCheckedItems);
        _4x_scope.setOnCheckedChangeListener(onCheckedItems);
        _6x_scope.setOnCheckedChangeListener(onCheckedItems);
        _8x_scope.setOnCheckedChangeListener(onCheckedItems);
        holo.setOnCheckedChangeListener(onCheckedItems);
        lazer.setOnCheckedChangeListener(onCheckedItems);
        _5_56mm.setOnCheckedChangeListener(onCheckedItems);
        _7_62mm.setOnCheckedChangeListener(onCheckedItems);
        _300_magnum.setOnCheckedChangeListener(onCheckedItems);
        _99mm.setOnCheckedChangeListener(onCheckedItems);
        _45_acp.setOnCheckedChangeListener(onCheckedItems);
        _12_gauge.setOnCheckedChangeListener(onCheckedItems);
        extended.setOnCheckedChangeListener(onCheckedItems);
        extended_quickdraw.setOnCheckedChangeListener(onCheckedItems);
        quickdraw.setOnCheckedChangeListener(onCheckedItems);
        compensator.setOnCheckedChangeListener(onCheckedItems);
        flashhider.setOnCheckedChangeListener(onCheckedItems);
        suppressor.setOnCheckedChangeListener(onCheckedItems);
        smoke.setOnCheckedChangeListener(onCheckedItems);
        grenade.setOnCheckedChangeListener(onCheckedItems);
        molotov.setOnCheckedChangeListener(onCheckedItems);
        flashbang.setOnCheckedChangeListener(onCheckedItems);
        angled_foregrip.setOnCheckedChangeListener(onCheckedItems);
        thumb_grip.setOnCheckedChangeListener(onCheckedItems);
        laser_sight.setOnCheckedChangeListener(onCheckedItems);
        light_grip.setOnCheckedChangeListener(onCheckedItems);
        half_grip.setOnCheckedChangeListener(onCheckedItems);
        vertical_foregrip.setOnCheckedChangeListener(onCheckedItems);
        duckbill.setOnCheckedChangeListener(onCheckedItems);
        choke.setOnCheckedChangeListener(onCheckedItems);
        stock.setOnCheckedChangeListener(onCheckedItems);
        tactical_stock.setOnCheckedChangeListener(onCheckedItems);
        lootbox.setOnCheckedChangeListener(onCheckedItems);
        air_drop.setOnCheckedChangeListener(onCheckedItems);
        ghilliesuit.setOnCheckedChangeListener(onCheckedItems);
        helmet_lv_1.setChecked(getShowBoolean(helmet_lv_1.getText().toString()));
        helmet_lv_2.setChecked(getShowBoolean(helmet_lv_2.getText().toString()));
        helmet_lv_3.setChecked(getShowBoolean(helmet_lv_3.getText().toString()));
        vest_lv_1.setChecked(getShowBoolean(vest_lv_1.getText().toString()));
        vest_lv_2.setChecked(getShowBoolean(vest_lv_2.getText().toString()));
        vest_lv_3.setChecked(getShowBoolean(vest_lv_3.getText().toString()));
        bagpack_lv_1.setChecked(getShowBoolean(bagpack_lv_1.getText().toString()));
        bagpack_lv_2.setChecked(getShowBoolean(bagpack_lv_2.getText().toString()));
        bagpack_lv_3.setChecked(getShowBoolean(bagpack_lv_3.getText().toString()));
        adrenalline.setChecked(getShowBoolean(adrenalline.getText().toString()));
        paintkiller.setChecked(getShowBoolean(paintkiller.getText().toString()));
        energydrink.setChecked(getShowBoolean(energydrink.getText().toString()));
        bandage.setChecked(getShowBoolean(bandage.getText().toString()));
        firstaidkit.setChecked(getShowBoolean(firstaidkit.getText().toString()));
        medkit.setChecked(getShowBoolean(medkit.getText().toString()));
        _2x_scope.setChecked(getShowBoolean(_2x_scope.getText().toString()));
        _3x_scope.setChecked(getShowBoolean(_3x_scope.getText().toString()));
        _4x_scope.setChecked(getShowBoolean(_4x_scope.getText().toString()));
        _6x_scope.setChecked(getShowBoolean(_6x_scope.getText().toString()));
        _8x_scope.setChecked(getShowBoolean(_8x_scope.getText().toString()));
        holo.setChecked(getShowBoolean(holo.getText().toString()));
        lazer.setChecked(getShowBoolean(lazer.getText().toString()));
        _5_56mm.setChecked(getShowBoolean(_5_56mm.getText().toString()));
        _7_62mm.setChecked(getShowBoolean(_7_62mm.getText().toString()));
        _300_magnum.setChecked(getShowBoolean(_300_magnum.getText().toString()));
        _99mm.setChecked(getShowBoolean(_99mm.getText().toString()));
        _45_acp.setChecked(getShowBoolean(_45_acp.getText().toString()));
        _12_gauge.setChecked(getShowBoolean(_12_gauge.getText().toString()));
        extended.setChecked(getShowBoolean(extended.getText().toString()));
        extended_quickdraw.setChecked(getShowBoolean(extended_quickdraw.getText().toString()));
        quickdraw.setChecked(getShowBoolean(quickdraw.getText().toString()));
        compensator.setChecked(getShowBoolean(compensator.getText().toString()));
        flashhider.setChecked(getShowBoolean(flashhider.getText().toString()));
        suppressor.setChecked(getShowBoolean(suppressor.getText().toString()));
        smoke.setChecked(getShowBoolean(smoke.getText().toString()));
        grenade.setChecked(getShowBoolean(grenade.getText().toString()));
        molotov.setChecked(getShowBoolean(molotov.getText().toString()));
        flashbang.setChecked(getShowBoolean(flashbang.getText().toString()));
        angled_foregrip.setChecked(getShowBoolean(angled_foregrip.getText().toString()));
        thumb_grip.setChecked(getShowBoolean(thumb_grip.getText().toString()));
        laser_sight.setChecked(getShowBoolean(laser_sight.getText().toString()));
        light_grip.setChecked(getShowBoolean(light_grip.getText().toString()));
        half_grip.setChecked(getShowBoolean(half_grip.getText().toString()));
        vertical_foregrip.setChecked(getShowBoolean(vertical_foregrip.getText().toString()));
        duckbill.setChecked(getShowBoolean(duckbill.getText().toString()));
        choke.setChecked(getShowBoolean(choke.getText().toString()));
        stock.setChecked(getShowBoolean(stock.getText().toString()));
        tactical_stock.setChecked(getShowBoolean(tactical_stock.getText().toString()));
        lootbox.setChecked(getShowBoolean(lootbox.getText().toString()));
        setValueBoolean(13, lootbox.isChecked());
        air_drop.setChecked(getShowBoolean(air_drop.getText().toString()));
        ghilliesuit.setChecked(getShowBoolean(ghilliesuit.getText().toString()));

        CheckBox buggy = mainView.findViewById(R.id.buggy);
        CheckBox uaz = mainView.findViewById(R.id.uaz);
        CheckBox motorcycleCart = mainView.findViewById(R.id.motorcycleCart);
        CheckBox motorcycle = mainView.findViewById(R.id.motorcycle);
        CheckBox dacia = mainView.findViewById(R.id.dacia);
        CheckBox aquaRail = mainView.findViewById(R.id.aquaRail);
        CheckBox pg117 = mainView.findViewById(R.id.pg117);
        CheckBox miniBus = mainView.findViewById(R.id.miniBus);
        CheckBox mirado = mainView.findViewById(R.id.mirado);
        CheckBox scooter = mainView.findViewById(R.id.scooter);
        CheckBox rony = mainView.findViewById(R.id.rony);
        CheckBox snowbike = mainView.findViewById(R.id.snowbike);
        CheckBox snowmobile = mainView.findViewById(R.id.snowmobile);
        CheckBox tuk = mainView.findViewById(R.id.tuk);
        CheckBox brdm = mainView.findViewById(R.id.brdm);
        CheckBox ladaNiva = mainView.findViewById(R.id.ladaNiva);
        CheckBox airDropPlane = mainView.findViewById(R.id.airDropPlane);

        CompoundButton.OnCheckedChangeListener onCheckedVehicles = (compoundButton, isChecked) -> {
            switch (compoundButton.getId()) {
                case R.id.buggy:
                    setShowBoolean(buggy.getText().toString(), isChecked);
                    setValueBooelan("SHOW_BUGGY", isChecked);
                    break;
                case R.id.uaz:
                    setShowBoolean(uaz.getText().toString(), isChecked);
                    setValueBooelan("SHOW_UAZ", isChecked);
                    break;
                case R.id.motorcycleCart:
                    setShowBoolean(motorcycleCart.getText().toString(), isChecked);
                    setValueBooelan("SHOW_MOTORCYLE_CART", isChecked);
                    break;
                case R.id.motorcycle:
                    setShowBoolean(motorcycle.getText().toString(), isChecked);
                    setValueBooelan("SHOW_MOTORCYLE", isChecked);
                    break;
                case R.id.dacia:
                    setShowBoolean(dacia.getText().toString(), isChecked);
                    setValueBooelan("SHOW_DACIA", isChecked);
                    break;
                case R.id.aquaRail:
                    setShowBoolean(aquaRail.getText().toString(), isChecked);
                    setValueBooelan("SHOW_AQUARAIL", isChecked);
                    break;
                case R.id.pg117:
                    setShowBoolean(pg117.getText().toString(), isChecked);
                    setValueBooelan("SHOW_PG117", isChecked);
                    break;
                case R.id.miniBus:
                    setShowBoolean(miniBus.getText().toString(), isChecked);
                    setValueBooelan("SHOW_MINIBUS", isChecked);
                    break;
                case R.id.mirado:
                    setShowBoolean(mirado.getText().toString(), isChecked);
                    setValueBooelan("SHOW_MIRADO", isChecked);
                    break;
                case R.id.scooter:
                    setShowBoolean(scooter.getText().toString(), isChecked);
                    setValueBooelan("SHOW_SCOOTER", isChecked);
                    break;
                case R.id.rony:
                    setShowBoolean(rony.getText().toString(), isChecked);
                    setValueBooelan("SHOW_RONY", isChecked);
                    break;
                case R.id.snowbike:
                    setShowBoolean(snowbike.getText().toString(), isChecked);
                    setValueBooelan("SHOW_SNOWBIKE", isChecked);
                    break;
                case R.id.snowmobile:
                    setShowBoolean(snowmobile.getText().toString(), isChecked);
                    setValueBooelan("SHOW_SNOWMOBILE", isChecked);
                    break;
                case R.id.tuk:
                    setShowBoolean(tuk.getText().toString(), isChecked);
                    setValueBooelan("SHOW_TUK", isChecked);
                    break;
                case R.id.brdm:
                    setShowBoolean(brdm.getText().toString(), isChecked);
                    setValueBooelan("SHOW_BRDM", isChecked);
                    break;
                case R.id.ladaNiva:
                    setShowBoolean(ladaNiva.getText().toString(), isChecked);
                    setValueBooelan("SHOW_LADANIVA", isChecked);
                    break;
                case R.id.airDropPlane:
                    setShowBoolean(airDropPlane.getText().toString(), isChecked);
                    setValueBooelan("SHOW_AIR_DROP_PLANE", isChecked);
                    break;
            }
        };
        buggy.setOnCheckedChangeListener(onCheckedVehicles);
        uaz.setOnCheckedChangeListener(onCheckedVehicles);
        motorcycleCart.setOnCheckedChangeListener(onCheckedVehicles);
        motorcycle.setOnCheckedChangeListener(onCheckedVehicles);
        dacia.setOnCheckedChangeListener(onCheckedVehicles);
        aquaRail.setOnCheckedChangeListener(onCheckedVehicles);
        pg117.setOnCheckedChangeListener(onCheckedVehicles);
        miniBus.setOnCheckedChangeListener(onCheckedVehicles);
        mirado.setOnCheckedChangeListener(onCheckedVehicles);
        scooter.setOnCheckedChangeListener(onCheckedVehicles);
        rony.setOnCheckedChangeListener(onCheckedVehicles);
        snowbike.setOnCheckedChangeListener(onCheckedVehicles);
        snowmobile.setOnCheckedChangeListener(onCheckedVehicles);
        tuk.setOnCheckedChangeListener(onCheckedVehicles);
        brdm.setOnCheckedChangeListener(onCheckedVehicles);
        ladaNiva.setOnCheckedChangeListener(onCheckedVehicles);
        airDropPlane.setOnCheckedChangeListener(onCheckedVehicles);

        buggy.setChecked(getShowBoolean(buggy.getText().toString()));
        uaz.setChecked(getShowBoolean(uaz.getText().toString()));
        motorcycleCart.setChecked(getShowBoolean(motorcycleCart.getText().toString()));
        motorcycle.setChecked(getShowBoolean(motorcycle.getText().toString()));
        dacia.setChecked(getShowBoolean(dacia.getText().toString()));
        aquaRail.setChecked(getShowBoolean(aquaRail.getText().toString()));
        pg117.setChecked(getShowBoolean(pg117.getText().toString()));
        miniBus.setChecked(getShowBoolean(miniBus.getText().toString()));
        mirado.setChecked(getShowBoolean(mirado.getText().toString()));
        scooter.setChecked(getShowBoolean(scooter.getText().toString()));
        rony.setChecked(getShowBoolean(rony.getText().toString()));
        snowbike.setChecked(getShowBoolean(snowbike.getText().toString()));
        snowmobile.setChecked(getShowBoolean(snowmobile.getText().toString()));
        tuk.setChecked(getShowBoolean(tuk.getText().toString()));
        brdm.setChecked(getShowBoolean(brdm.getText().toString()));
        ladaNiva.setChecked(getShowBoolean(ladaNiva.getText().toString()));
        airDropPlane.setChecked(getShowBoolean(airDropPlane.getText().toString()));

        seekbar_frame_rate = mainView.findViewById(R.id.seekbar_frame_rate);
        TextView tv_seekbar_frame_rate = mainView.findViewById(R.id.tv_seekbar_frame_rate);
        seekbar_frame_rate.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_seekbar_frame_rate.setText("" + i);
                setValueInt("FRAME_RATE", i);
                canvasDrawingView.setFPS(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        seekbar_frame_rate.setProgress(getValueInt("FRAME_RATE"));
        tv_seekbar_frame_rate.setText(String.valueOf(seekbar_frame_rate.getProgress()));

        final CheckBox cb_name = mainView.findViewById(R.id.cb_name);
        final CheckBox cb_nation = mainView.findViewById(R.id.cb_nation);
        final CheckBox cb_distance = mainView.findViewById(R.id.cb_distance);
        final CheckBox cb_health = mainView.findViewById(R.id.cb_health);
        final CheckBox cb_team_id = mainView.findViewById(R.id.cb_team_id);
        final CheckBox cb_box = mainView.findViewById(R.id.cb_box);
        final CheckBox cb_radar_line = mainView.findViewById(R.id.cb_radar_line);
        final CheckBox cb_skeleton = mainView.findViewById(R.id.cb_skeleton);
        final CheckBox cb_weapon = mainView.findViewById(R.id.cb_weapon);
        final CheckBox cb_enemy_count = mainView.findViewById(R.id.cb_enemy_count);
        final CheckBox cb_360_warning = mainView.findViewById(R.id.cb_360_warning);
        final CheckBox cb_head_dots = mainView.findViewById(R.id.cb_head_dots);
        final CheckBox cb_grenade_alert = mainView.findViewById(R.id.cb_grenade_alert);
        final CheckBox cb_uid = mainView.findViewById(R.id.cb_uid);
        CompoundButton.OnCheckedChangeListener tab1OnChecked = (buttonView, isChecked) -> {
            switch (buttonView.getId()){
                case R.id.cb_name:
                    setValueBooelan(cb_name.getText().toString(), isChecked);
                    setValueBoolean(1, isChecked);
                    break;
                case R.id.cb_nation:
                    setValueBooelan(cb_nation.getText().toString(), isChecked);
                    setValueBoolean(14, isChecked);
                    break;
                case R.id.cb_distance:
                    setValueBooelan(cb_distance.getText().toString(), isChecked);
                    setValueBoolean(2, isChecked);
                    break;
                case R.id.cb_health:
                    setValueBooelan(cb_health.getText().toString(), isChecked);
                    setValueBoolean(3, isChecked);
                    break;
                case R.id.cb_team_id:
                    setValueBooelan(cb_team_id.getText().toString(), isChecked);
                    setValueBoolean(4, isChecked);
                    break;
                case R.id.cb_box:
                    setValueBooelan(cb_box.getText().toString(), isChecked);
                    setValueBoolean(5, isChecked);
                    break;
                case R.id.cb_radar_line:
                    setValueBooelan(cb_radar_line.getText().toString(), isChecked);
                    setValueBoolean(6, isChecked);
                    break;
                case R.id.cb_skeleton:
                    setValueBooelan(cb_skeleton.getText().toString(), isChecked);
                    setValueBoolean(7, isChecked);
                    break;
                case R.id.cb_weapon:
                    setValueBooelan(cb_weapon.getText().toString(), isChecked);
                    setValueBoolean(8, isChecked);
                    break;
                case R.id.cb_enemy_count:
                    setValueBooelan(cb_enemy_count.getText().toString(), isChecked);
                    setValueBoolean(9, isChecked);
                    break;
                case R.id.cb_360_warning:
                    setValueBooelan(cb_360_warning.getText().toString(), isChecked);
                    setValueBoolean(10, isChecked);
                    break;
                case R.id.cb_head_dots:
                    setValueBooelan(cb_head_dots.getText().toString(), isChecked);
                    setValueBoolean(11, isChecked);
                    break;
                case R.id.cb_grenade_alert:
                    setValueBooelan(cb_grenade_alert.getText().toString(), isChecked);
                    setValueBoolean(12, isChecked);
                    break;
                case R.id.cb_uid:
                    setValueBooelan(cb_uid.getText().toString(), isChecked);
                    setValueBoolean(15, isChecked);
                    break;
            }
        };
        cb_name.setOnCheckedChangeListener(tab1OnChecked);
        cb_nation.setOnCheckedChangeListener(tab1OnChecked);
        cb_distance.setOnCheckedChangeListener(tab1OnChecked);
        cb_health.setOnCheckedChangeListener(tab1OnChecked);
        cb_team_id.setOnCheckedChangeListener(tab1OnChecked);
        cb_box.setOnCheckedChangeListener(tab1OnChecked);
        cb_radar_line.setOnCheckedChangeListener(tab1OnChecked);
        cb_skeleton.setOnCheckedChangeListener(tab1OnChecked);
        cb_weapon.setOnCheckedChangeListener(tab1OnChecked);
        cb_enemy_count.setOnCheckedChangeListener(tab1OnChecked);
        cb_360_warning.setOnCheckedChangeListener(tab1OnChecked);
        cb_head_dots.setOnCheckedChangeListener(tab1OnChecked);
        cb_grenade_alert.setOnCheckedChangeListener(tab1OnChecked);
        cb_uid.setOnCheckedChangeListener(tab1OnChecked);

        cb_name.setChecked(getValueBoolean(cb_name.getText().toString()));
        cb_nation.setChecked(getValueBoolean(cb_nation.getText().toString()));
        cb_distance.setChecked(getValueBoolean(cb_distance.getText().toString()));
        cb_health.setChecked(getValueBoolean(cb_health.getText().toString()));
        cb_team_id.setChecked(getValueBoolean(cb_team_id.getText().toString()));
        cb_box.setChecked(getValueBoolean(cb_box.getText().toString()));
        cb_radar_line.setChecked(getValueBoolean(cb_radar_line.getText().toString()));
        cb_skeleton.setChecked(getValueBoolean(cb_skeleton.getText().toString()));
        cb_weapon.setChecked(getValueBoolean(cb_weapon.getText().toString()));
        cb_enemy_count.setChecked(getValueBoolean(cb_enemy_count.getText().toString()));
        cb_360_warning.setChecked(getValueBoolean(cb_360_warning.getText().toString()));
        cb_head_dots.setChecked(getValueBoolean(cb_head_dots.getText().toString()));
        cb_grenade_alert.setChecked(getValueBoolean(cb_grenade_alert.getText().toString()));
        cb_uid.setChecked(getValueBoolean(cb_grenade_alert.getText().toString()));

        setValueBoolean(1, cb_name.isChecked());
        setValueBoolean(14, cb_nation.isChecked());
        setValueBoolean(2, cb_distance.isChecked());
        setValueBoolean(3, cb_health.isChecked());
        setValueBoolean(4, cb_team_id.isChecked());
        setValueBoolean(5, cb_box.isChecked());
        setValueBoolean(6, cb_radar_line.isChecked());
        setValueBoolean(7, cb_skeleton.isChecked());
        setValueBoolean(8, cb_weapon.isChecked());
        setValueBoolean(9, cb_enemy_count.isChecked());
        setValueBoolean(10, cb_360_warning.isChecked());
        setValueBoolean(11, cb_head_dots.isChecked());
        setValueBoolean(12, cb_grenade_alert.isChecked());
        setValueBoolean(15, cb_uid.isChecked());

        final Switch LessRecoil = mainView.findViewById(R.id.LessRecoil);
        LessRecoil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(16,LessRecoil.isChecked());
            }
        });

        final Switch ZeroRecoil = mainView.findViewById(R.id.ZeroRecoil);
        ZeroRecoil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(17,ZeroRecoil.isChecked());
            }
        });

        final Switch InstantHit = mainView.findViewById(R.id.InstantHit);
        InstantHit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(18,InstantHit.isChecked());
            }
        });
        final Switch FastShootInterval = mainView.findViewById(R.id.FastShootInterval);
        FastShootInterval.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(19,FastShootInterval.isChecked());
            }
        });
        final Switch HitX = mainView.findViewById(R.id.HitX);
        HitX.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(20,HitX.isChecked());
            }
        });
        final Switch SmallCrosshair = mainView.findViewById(R.id.SmallCrosshair);
        SmallCrosshair.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(21,SmallCrosshair.isChecked());
            }
        });
        final Switch NoShake = mainView.findViewById(R.id.NoShake);
        NoShake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(22,NoShake.isChecked());
            }
        });

        final Switch BulletTrack = mainView.findViewById(R.id.BulletTrack);
        final Switch AimBot = mainView.findViewById(R.id.AimBot);

        BulletTrack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(23,BulletTrack.isChecked());
                if (AimBot.isChecked()) {
                    AimBot.setChecked(!isChecked);
                }
            }
        });

        AimBot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(24,AimBot.isChecked());
                if (BulletTrack.isChecked()) {
                    BulletTrack.setChecked(!isChecked);
                }
            }
        });

        final SeekBar range = mainView.findViewById(R.id.range);
        range.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Range(range.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final RadioGroup aimlocation=mainView.findViewById(R.id.aimlocation);
        aimlocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int chkdId=aimlocation.getCheckedRadioButtonId();
                RadioButton btn=mainView.findViewById(chkdId);
                Location(Integer.parseInt(btn.getTag().toString()));
            }
        });

        final RadioGroup aimtarget=mainView.findViewById(R.id.aimtarget);
        aimtarget.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int chkdId=aimtarget.getCheckedRadioButtonId();
                RadioButton btn=mainView.findViewById(chkdId);
                Target(Integer.parseInt(btn.getTag().toString()));
            }
        });

        final RadioGroup aimtrigger=mainView.findViewById(R.id.aimtrigger);
        aimtrigger.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int chkdId=aimtrigger.getCheckedRadioButtonId();
                RadioButton btn=mainView.findViewById(chkdId);
                Trigger(Integer.parseInt(btn.getTag().toString()));
            }
        });

        final Switch aimprediction = mainView.findViewById(R.id.aimprediction);
        aimprediction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(25,aimprediction.isChecked());
            }
        });

        final Switch aimignoreknocked = mainView.findViewById(R.id.aimignoreknocked);
        aimignoreknocked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setValueBoolean(26,aimignoreknocked.isChecked());
            }
        });

    }

    public static void setValueBooelan(String name, boolean apply){
        cPreferences.setBoolean(name, apply);
    }

    public static boolean getValueBoolean(String name){
        return cPreferences.getBoolean(name);
    }
    public static void setValueInt(String name, int value){
        cPreferences.setInt(name, value);
    }
    public static int getValueInt(String name){
        return cPreferences.getInt(name);
    }

    public static void setShowBoolean(String name, boolean apply){
        cPreferences.setBoolean("SHOW_" + name, apply);
    }
    public static boolean getShowBoolean(String name){
        return cPreferences.getBoolean("SHOW_" + name);
    }

    public static int getValueColor(String name){
        return cPreferences.getInt(name + "_COLOR");
    }

    public static String getResString(int id){
        if (context!= null)
            return context.getResources().getString(id);
        return null;
    }

    public static void destroy(){
        if (menuView != null){
            windowManager.removeView(menuView);
            menuView = null;
        }
        if (canvasDrawingView != null){
            windowManager.removeView(canvasDrawingView);
            canvasDrawingView = null;
        }
    }
}
