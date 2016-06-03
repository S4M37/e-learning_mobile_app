package tn.ppp.gl3.e_learning.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.ppp.gl3.e_learning.Adapter.CategoryRecyclerViewAdapter;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.DialogFactory;
import tn.ppp.gl3.e_learning.Service.RetrofitServices;
import tn.ppp.gl3.e_learning.Utils.Utils;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class ExamsFragment extends Fragment {
    private View rootView;
    private RecyclerView listExams;
    private RecyclerView.LayoutManager layoutManager;
    private RetrofitServices retrofitServices;
    private ProgressDialog progressDialog;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        retrofitServices = Utils.getRetrofitServices();
        inisiliazeView();
        getExams();
        return rootView;
    }

    private void getExams() {
        progressDialog.show();
        Call<ResponseBody> call = retrofitServices.getExams();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                        Gson gson = new Gson();
                        Exam[] exams = new Exam[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            exams[i] = gson.fromJson(String.valueOf(jsonArray.get(i)), Exam.class);
                        }
                        CategoryRecyclerViewAdapter categoryRecyclerViewAdapter = new CategoryRecyclerViewAdapter(getContext(), exams);
                        listExams.setAdapter(categoryRecyclerViewAdapter);
                    } catch (JSONException | IOException | NullPointerException e) {
                        e.printStackTrace();
                        DialogFactory.showAlertDialog(getContext(), getString(R.string.server_error), getString(R.string.title_server_error));
                    }
                } else {
                    DialogFactory.showAlertDialog(getContext(), getString(R.string.server_error), getString(R.string.title_server_error));
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void inisiliazeView() {
        listExams = (RecyclerView) rootView.findViewById(R.id.list_categories);
        layoutManager = new LinearLayoutManager(getContext());
        listExams.setLayoutManager(layoutManager);
        listExams.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
