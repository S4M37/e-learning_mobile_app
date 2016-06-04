package tn.ppp.gl3.e_learning.Service;

import android.content.Context;

import tn.ppp.gl3.e_learning.R;


/**
 * Created by S4M37 on 17/04/2016.
 */
public class DialogFactory {
    public static void showAlertDialog(Context context, String message, String titre) {
        if (context != null) {
            android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(titre);
            builder.setPositiveButton("OK", null);
            builder.setMessage(message);
            builder.show();
        }
    }


    public static void showAlertDialogEmptyResponse(Context context, int position) {
        if (context != null) {
            android.support.v7.app.AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
            builder.setTitle("Hey!");
            builder.setMessage("U have question " + position + " is empty!");
            builder.setPositiveButton("OK", null);
            builder.show();
        }
    }
}
