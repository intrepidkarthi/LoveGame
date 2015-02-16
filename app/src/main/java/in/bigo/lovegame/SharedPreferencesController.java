package in.bigo.lovegame;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by karthi on 12/04/14.
 */
public class SharedPreferencesController {

    private static final String SHARED_PREF_NAME = "matchfixing_bengaluru";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //***********************SINGLETON FACTORY***********************************

    static SharedPreferencesController sharedPreferenceController = null;

    public static SharedPreferencesController getSharedPreferencesController(Activity activity) {
        if (sharedPreferenceController == null) {
            sharedPreferenceController = new SharedPreferencesController(activity);
        }
        return sharedPreferenceController;
    }
    //**************************************************************************

    //private constructor
    SharedPreferencesController(Activity activity) {
        sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, 0);
        editor = sharedPreferences.edit();
    }


    public SharedPreferences getSharedPreferences() {
        return this.sharedPreferences;
    }

    public SharedPreferences.Editor getSharedPreferencesEditor() {

        return this.editor;
    }


    public void putString(String preferenceName, String data) {
        editor.putString(preferenceName, data);
        editor.commit();
    }

    public String getString(String preferenceName) {
        return sharedPreferences.getString(preferenceName, "");
    }


    public Integer getInt(String preferenceName) {
        return sharedPreferences.getInt(preferenceName, 0);
    }

    public void putInt(String preferenceName, Integer data) {
        editor.putInt(preferenceName, data);
        editor.commit();
    }

    public Boolean getBoolean(String preferenceName) {
        return sharedPreferences.getBoolean(preferenceName, true);
    }

    public void putBoolean(String preferenceName, Boolean data) {
        editor.putBoolean(preferenceName, data);
        editor.commit();
    }

    public void removePreference(String preferenceName) {
        editor.remove(preferenceName);
        editor.commit();
    }

}
