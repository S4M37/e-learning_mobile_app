package tn.ppp.gl3.e_learning.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import tn.ppp.gl3.e_learning.adapters.SimpleRecyclerViewAdapter;
import tn.ppp.gl3.e_learning.models.Exam;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.services.DialogFactory;
import tn.ppp.gl3.e_learning.services.RetrofitServices;
import tn.ppp.gl3.e_learning.utils.Utils;

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
        rootView = inflater.inflate(R.layout.fragment_simple_list, container, false);
        retrofitServices = Utils.getRetrofitServices();
        inisiliazeView();
        getExams();
        return rootView;
    }

    private void getExams() {
        progressDialog.show();
        Call<ResponseBody> call = retrofitServices.getExams(Utils.token);
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
                        SimpleRecyclerViewAdapter categoryRecyclerViewAdapter = new SimpleRecyclerViewAdapter(getContext(), exams);
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
        ((Toolbar) rootView.findViewById(R.id.toolbar)).setTitle("Exams");
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }
}
