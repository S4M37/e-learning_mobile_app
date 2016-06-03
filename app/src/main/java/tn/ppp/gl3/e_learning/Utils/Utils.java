package tn.ppp.gl3.e_learning.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tn.ppp.gl3.e_learning.Service.RetrofitServices;

/**
 * Created by S4M37 on 17/04/2016.
 */
public class Utils {

    public static final String TAG_ACTION = "action";
    public static Retrofit retrofit;
    public static final String TAG_ROOTE_RESPONSE = "response";
    public static String token = "";

    public static RetrofitServices getRetrofitServices() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Url.base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(RetrofitServices.class);
    }

    public static SharedPreferences.Editor getPreferencesEditor(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public static void hideKeyboard(AppCompatActivity appCompatActivity) {
        if (appCompatActivity != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) appCompatActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                if (appCompatActivity.getCurrentFocus() != null)
                    inputMethodManager.hideSoftInputFromWindow(appCompatActivity.getCurrentFocus().getWindowToken(), 0);
            }
        }

    }
}
