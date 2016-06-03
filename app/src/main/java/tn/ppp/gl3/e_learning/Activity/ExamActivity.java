package tn.ppp.gl3.e_learning.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import tn.ppp.gl3.e_learning.Fragment.ExamFragment;
import tn.ppp.gl3.e_learning.Fragment.TrainingFragment;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.R;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class ExamActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private boolean isExam;
    private Exam exam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        Intent intent = getIntent();
        isExam = intent.getBooleanExtra("isExam", false);
        exam = (Exam) intent.getSerializableExtra("exam");
        if (!isExam) {
            showFragment(TrainingFragment.newInstance(exam));
        } else {
            showFragment(ExamFragment.newInstance(exam));
        }
    }

    public void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getString(R.string.exam_backpressed_title));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.setMessage(getString(R.string.exam_backpressed_message));
        builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}
