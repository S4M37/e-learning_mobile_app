package tn.ppp.gl3.e_learning.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.adapters.SimpleRecyclerViewAdapter;
import tn.ppp.gl3.e_learning.models.Course;
import tn.ppp.gl3.e_learning.services.DialogFactory;
import tn.ppp.gl3.e_learning.services.RetrofitServices;
import tn.ppp.gl3.e_learning.utils.Utils;


public class CoursesFragment extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 122;
    private View rootView;
    private RecyclerView listCategries;
    private RetrofitServices retrofitServices;
    private ProgressDialog progressDialog;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_simple_list, container, false);
        retrofitServices = Utils.getRetrofitServices();
        inisiliazeView();
        getCategories();
        checkPermission();
        return rootView;
    }

    private void getCategories() {
        progressDialog.show();
        Call<ResponseBody> call = retrofitServices.getCourses();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("response", "onResponse: " + response.code());
                Log.d("response", "onResponse: " + response.message());
                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("response");
                        Gson gson = new Gson();
                        Course[] courses = new Course[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            courses[i] = gson.fromJson(String.valueOf(jsonArray.get(i)), Course.class);
                            Log.d("response", String.valueOf(jsonArray.get(i)));
                        }
                        SimpleRecyclerViewAdapter categoryRecyclerViewAdapter = new SimpleRecyclerViewAdapter(getContext(), courses);
                        listCategries.setAdapter(categoryRecyclerViewAdapter);
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
        listCategries = (RecyclerView) rootView.findViewById(R.id.list_categories);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        listCategries.setLayoutManager(layoutManager);
        listCategries.setHasFixedSize(true);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        ((Toolbar) rootView.findViewById(R.id.toolbar)).setTitle("Courses");
    }

    //check permissions
    // Here, thisActivity is the current activity
    void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

}
