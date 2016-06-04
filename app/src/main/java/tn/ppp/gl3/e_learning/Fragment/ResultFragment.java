package tn.ppp.gl3.e_learning.Fragment;

import android.app.ProgressDialog;
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
import tn.ppp.gl3.e_learning.Adapter.ResultRecyclerViewAdapter;
import tn.ppp.gl3.e_learning.Model.Result;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.CompteManager;
import tn.ppp.gl3.e_learning.Service.DialogFactory;
import tn.ppp.gl3.e_learning.Service.RetrofitServices;
import tn.ppp.gl3.e_learning.Utils.Utils;

/**
 * Created by S4M37 on 04/06/2016.
 */
public class ResultFragment extends Fragment {
    private View rootView;
    private RecyclerView listResult;
    private RecyclerView.LayoutManager layoutManager;
    private RetrofitServices retrofitServices;
    private ProgressDialog progressDialog;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_category, container, false);
        retrofitServices = Utils.getRetrofitServices();
        inisiliazeView();
        getResults();
        return rootView;
    }

    private void getResults() {
        progressDialog.show();
        Call<ResponseBody> call = retrofitServices.getResultsForUser(CompteManager.getCurrentUser(getContext()).getId_user());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                        Gson gson = new Gson();
                        Result[] results = new Result[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            results[i] = gson.fromJson(String.valueOf(jsonArray.get(i)), Result.class);
                        }
                        ResultRecyclerViewAdapter resultRecyclerViewAdapter = new ResultRecyclerViewAdapter(getContext(), results);
                        listResult.setAdapter(resultRecyclerViewAdapter);
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
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));

        listResult = (RecyclerView) rootView.findViewById(R.id.list_categories);
        layoutManager = new LinearLayoutManager(getContext());
        listResult.setLayoutManager(layoutManager);
        listResult.setHasFixedSize(true);
        ((Toolbar) rootView.findViewById(R.id.toolbar)).setTitle("Results");
    }


}
