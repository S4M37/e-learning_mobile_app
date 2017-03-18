package tn.ppp.gl3.e_learning.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import tn.ppp.gl3.e_learning.models.User;
import tn.ppp.gl3.e_learning.utils.Utils;


/**
 * Created by S4M37 on 17/04/2016.
 */
public class CompteManager {
    public static final String TAG_USER_ID = "user_id";
    public static final String TAG_USER_NAME = "name";
    public static final String TAG_USER_EMAIL = "email";

    public static String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString("token", "");
    }

    public static void saveToken(Context context, String token) {
        SharedPreferences.Editor editor = Utils.getPreferencesEditor(context);
        editor.putString("token", token);
        editor.commit();
    }

    public static void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = Utils.getPreferencesEditor(context);
        editor.putInt(TAG_USER_ID, user.getId_user());
        editor.putString(TAG_USER_NAME, user.getName());
        editor.putString(TAG_USER_EMAIL, user.getEmail());
        editor.commit();
    }

    public static User getCurrentUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int userId = preferences.getInt(TAG_USER_ID, 0);
        if (userId == 0) {
            return null;
        }
        return new User(userId, preferences.getString(TAG_USER_NAME, ""), preferences.getString(TAG_USER_EMAIL, ""));
    }

    public static void logout(Context context) {
        SharedPreferences.Editor editor = Utils.getPreferencesEditor(context);
        editor.clear();
        editor.commit();
    }
}
