package tn.ppp.gl3.e_learning.Service;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

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


}
