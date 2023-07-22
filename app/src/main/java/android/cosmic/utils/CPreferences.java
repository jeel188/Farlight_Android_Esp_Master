package android.cosmic.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class CPreferences {
    private Context context;
    private SharedPreferences shared;

    public CPreferences(Context context){
        this.context = context;
        this.shared = context.getSharedPreferences("CPreferences", 0);
    }

    public static void InitShared(Context context){
        SharedPreferences shared =  context.getSharedPreferences("CPreferences", 0);
        if (shared.getBoolean("InitShared", true)){
            shared.edit().putInt("PUBGM_VERSION", 1).apply();
            //shared.edit().putInt("FRAME_RATE", 60).apply();
        }
    }
    public String getString(String name){
        return this.shared.getString(name,  null);
    }
    public boolean getBoolean(String name){
        return this.shared.getBoolean(name, true);
    }
    public int getInt(String name){
        return this.shared.getInt(name, 0);
    }
    public void setString(String name, String value){
        this.shared.edit().putString(name, value).apply();
    }
    public void setBoolean(String name, boolean value){
        this.shared.edit().putBoolean(name, value).apply();
    }
    public void setInt(String name, int value){
        this.shared.edit().putInt(name, value).apply();
    }
    public void remove(String name){
        this.shared.edit().remove(name).apply();
    }
}
