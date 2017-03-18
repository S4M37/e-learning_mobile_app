package tn.ppp.gl3.e_learning.services;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;

/**
 * Created by S4M37 on 13/02/2016.
 */
public class Connectivity {
    /*----Method to Check GPS is enable or disable ----- */
    public static Boolean displayGpsStatus(LocationManager location_manager) {
        boolean gpsStatus = location_manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;
        } else {
            return false;
        }
    }

    /*----Method to Check Network is enable or disable ----- */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

}
