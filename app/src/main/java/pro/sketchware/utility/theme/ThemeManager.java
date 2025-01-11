package pro.sketchware.utility.theme;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.view.ContextThemeWrapper;

import com.android.tools.r8.internal.C;

import java.util.ArrayList;

import pro.sketchware.R;

public class ThemeManager {

    private static final String THEME_PREF = "themedata";
    private static final String MODE_KEY = "idetheme"; // theme mode : light , dark and follow system
    private static final String THEME_KEY = "idethemecolor"; // theme : greenapple , dark and follow system
    private static final String AMOLED_KEY = "ideisamoled"; // theme : greenapple , dark and follow system


    public static final int THEME_SYSTEM = 0;
    public static final int THEME_LIGHT = 1;
    public static final int THEME_DARK = 2;

    public static void applyMode(Context context, int type) {
        saveThemeMode(context, type);
        applyTheme(context, getCurrentTheme(context));
        if (isAmoledEnabled(context)) applyAmoled(context);


        switch (type) {
            case THEME_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case THEME_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }

    public static void applyTheme(Context context, int theme) {
        switch (theme) {
            case (0):
                context.setTheme(R.style.Dynamic);
                break;
            case (1):
                context.setTheme(R.style.Lavender);
                break;
            case (2):
                context.setTheme(R.style.yogNesh);
                break;
            case (3):
                context.setTheme(R.style.YinYang);
                break;
            case (4):
                context.setTheme(R.style.sketchwareOg);
                break;
            case (5):
                context.setTheme(R.style.GreenApple);
                break;
            default:
                context.setTheme(R.style.Dynamic);
        }
        saveTheme(context,theme);
    }

    public static void applyAmoled(Context context) {
        context.getTheme().applyStyle(R.style.sketchware_Amoled, true);

    }


    public static int getCurrentMode(Context context) {
        return getPreferences(context).getInt(MODE_KEY, THEME_SYSTEM);
    }

    public static int getCurrentTheme(Context context) {
        return getPreferences(context).getInt(THEME_KEY, THEME_SYSTEM);
    }

    public static boolean isAmoledEnabled(Context context) {
        return getPreferences(context).getBoolean(AMOLED_KEY, false);
    }

    public static int getSystemAppliedTheme(Context context){
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.themeId, outValue, true);
        return outValue.data;
    }

    public static boolean isSystemMode(Context context) {
        return getCurrentMode(context) == THEME_SYSTEM;
    }


    private static void saveThemeMode(Context context, int mode) {
        getPreferences(context).edit().putInt(MODE_KEY, mode).apply();
    }

    public static void saveTheme(Context context, int theme) {
        getPreferences(context).edit().putInt(THEME_KEY, theme).apply();
    }

    public static void setAmoled(Context context, boolean bool) {
        getPreferences(context).edit().putBoolean(AMOLED_KEY, bool).apply();
        if (bool) applyAmoled(context);;
    }

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(THEME_PREF, Context.MODE_PRIVATE);
    }

    public static ArrayList<ThemeItem> getThemesList(){
        ArrayList<ThemeItem> themeList = new ArrayList<>();

        themeList.add(new ThemeItem("Dynamic", R.style.Dynamic, 0));
        themeList.add(new ThemeItem("Lavender", R.style.Lavender, 1));
        themeList.add(new ThemeItem("Yog & esh", R.style.yogNesh, 2));
        themeList.add(new ThemeItem("Yin & Yang", R.style.YinYang, 3));
        themeList.add(new ThemeItem("Sketchware original", R.style.sketchwareOg, 4));
        themeList.add(new ThemeItem("Green Apple", R.style.GreenApple, 5));


        return themeList;
    }

    public static int getColorFromTheme(Context context, int themeResId, int attrResId) {
        ContextThemeWrapper themeWrapper = new ContextThemeWrapper(context, themeResId);
        TypedArray typedArray = themeWrapper.obtainStyledAttributes(new int[]{attrResId});
        int color = typedArray.getColor(0, 0xbdbdbd);
        typedArray.recycle();
        return color;
    }

    public static int getSystemAppliedMode(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        return switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_NO -> THEME_LIGHT;
            case Configuration.UI_MODE_NIGHT_YES -> THEME_DARK;
            default -> THEME_SYSTEM;
        };
    }



}