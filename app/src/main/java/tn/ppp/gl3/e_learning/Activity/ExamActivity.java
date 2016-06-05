package tn.ppp.gl3.e_learning.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.ppp.gl3.e_learning.Fragment.ExamFragment;
import tn.ppp.gl3.e_learning.Fragment.QuestionFragment;
import tn.ppp.gl3.e_learning.Fragment.TrainingFragment;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.Model.Result;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.CompteManager;
import tn.ppp.gl3.e_learning.Service.DialogFactory;
import tn.ppp.gl3.e_learning.Utils.Utils;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class ExamActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private boolean isExam;
    private Exam exam;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
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

    public void stopTest(final boolean isExam) {
        final android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Hey!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (isExam) {
                            int[] response = new int[exam.getItems().length];
                            ExamFragment examFragment = (ExamFragment) fragmentManager.getFragments().get(0);
                            for (int i = 0; i < exam.getItems().length; i++) {
                                QuestionFragment questionFragment = (QuestionFragment) examFragment.getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + i);
                                int res = questionFragment.getResult();
                                if (res == -1) {
                                    response[i] = 0;
                                } else {
                                    response[i] = res;
                                }
                            }
                            calulateScore(response, true);
                        } else {
                            int[] response = new int[exam.getItems().length];
                            TrainingFragment trainingFragment = (TrainingFragment) fragmentManager.getFragments().get(0);
                            int total = 0;
                            for (int i = 0; i < exam.getItems().length; i++) {
                                QuestionFragment questionFragment = (QuestionFragment) trainingFragment.getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + i);
                                int res = questionFragment.getResult();
                                if (res == -1) {
                                    res = 0;
                                }
                                response[i] = res;
                                total += res;
                            }
                            float result = (float) total / exam.getItems().length;
                            String label;
                            if (result <= 0.5) {
                                label = "fail";
                            } else {
                                label = "succeed";
                            }
                            android.support.v7.app.AlertDialog.Builder builder =
                                    new android.support.v7.app.AlertDialog.Builder(ExamActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle(getString(R.string.exam_backpressed_title));
                            builder.setMessage(label + " with a score : " + result);
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        }
                    }
                }
        );
        if (isExam) {
            builder.setMessage(getString(R.string.exam_time_out));
        } else {
            builder.setMessage(getString(R.string.training_time_out));
        }
        builder.show();
    }

    public void calulateScore(int[] response, boolean hasResponse) {

        if (hasResponse) {
            progressDialog.show();
            Call<ResponseBody> call = Utils.getRetrofitServices().storeResult(Utils.token, exam.getId_exam(), CompteManager.getCurrentUser(ExamActivity.this).getId_user(), response);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 200) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().string());
                            jsonObject = jsonObject.getJSONObject("response");
                            Gson gson = new Gson();
                            Result result = gson.fromJson(String.valueOf(jsonObject), Result.class);
                            android.support.v7.app.AlertDialog.Builder builder =
                                    new android.support.v7.app.AlertDialog.Builder(ExamActivity.this, R.style.AppCompatAlertDialogStyle);
                            builder.setTitle(getString(R.string.exam_backpressed_title));
                            builder.setMessage(result.getLabel() + " with a score : " + result.getScore() * 100 + "%");
                            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            builder.show();
                        } catch (JSONException | IOException | NullPointerException e) {
                            e.printStackTrace();
                            DialogFactory.showAlertDialog(ExamActivity.this, getString(R.string.server_error), getString(R.string.title_server_error));
                        }
                    } else {
                        Log.d("responseCode", response.code() + "");
                        DialogFactory.showAlertDialog(ExamActivity.this, getString(R.string.server_error), getString(R.string.title_server_error));
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                }
            });
        }

    }
}
