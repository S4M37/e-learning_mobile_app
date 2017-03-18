package tn.ppp.gl3.e_learning.services;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class DownloadPdfFile extends AsyncTask<String, Void, Void> {
    private InputStream in;
    private Context context;

    public DownloadPdfFile(InputStream in, Context context) {
        this.in = in;
        this.context = context;
    }

    @Override
    protected Void doInBackground(String... params) {

        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        FileOutputStream f = null;
        File file = new File(extStorageDirectory, params[0]);
        try {
            f = new FileOutputStream(file);

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Intent i;
            i = new Intent(Intent.ACTION_VIEW);
            i.setDataAndType(Uri.fromFile(file), "application/pdf");
            context.startActivity(i);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context,
                    "No Application Available to fiew this file type",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
