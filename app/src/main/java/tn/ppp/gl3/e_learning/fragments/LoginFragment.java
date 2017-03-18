package tn.ppp.gl3.e_learning.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.ppp.gl3.e_learning.activities.MainActivity;
import tn.ppp.gl3.e_learning.models.User;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.services.CompteManager;
import tn.ppp.gl3.e_learning.services.DialogFactory;
import tn.ppp.gl3.e_learning.services.RetrofitServices;
import tn.ppp.gl3.e_learning.utils.Utils;

/**
 * Created by S4M37 on 17/04/2016.
 */
public class LoginFragment extends Fragment {

    private View rootView = null;
    private EditText inputEmail = null;
    private EditText inputPassword = null;
    private Button buttonSend;
    private Button inscription;
    private RetrofitServices retrofitServices;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        retrofitServices = Utils.getRetrofitServices();
        inisializeView();
        return rootView;
    }

    private void inisializeView() {
        inputEmail = (EditText) rootView.findViewById(R.id.email);
        inputPassword = (EditText) rootView.findViewById(R.id.password);
        buttonSend = (Button) rootView.findViewById(R.id.email_sign_in_button);
        inscription = (Button) rootView.findViewById(R.id.inscription);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.hideKeyboard((AppCompatActivity) getActivity());
                if (inputEmail.getText().toString().equals("") || inputPassword.getText().toString().equals("")) {
                    DialogFactory.showAlertDialog(getContext(), getString(R.string.empty_field), getString(R.string.title_server_error));
                } else {
                    progressDialog.show();
                    Call<ResponseBody> call = retrofitServices.signin(inputEmail.getText().toString(), inputPassword.getText().toString());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.d("code", response.code() + "");
                            switch (response.code()) {
                                case 500:
                                    DialogFactory.showAlertDialog(getContext(), getString(R.string.invalid_user), getString(R.string.title_server_error));
                                    break;
                                case 401:
                                    DialogFactory.showAlertDialog(getContext(), getString(R.string.invalid_credential), getString(R.string.title_server_error));
                                    break;
                                case 402:
                                    DialogFactory.showAlertDialog(getContext(), getString(R.string.inactive_account), getString(R.string.title_server_error));
                                    break;
                                case 200:
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        String token = jsonObject.getString("token");
                                        CompteManager.saveToken(getContext(), "Bearer " + token);
                                        Utils.token = "Bearer " + token;
                                        Log.d("token", "Bearer " + token);
                                        Gson gson = new Gson();
                                        User user = gson.fromJson(jsonObject.getString("user"), User.class);
                                        if (user.getValide() == 1) {
                                            CompteManager.saveUser(getContext(), user);
                                            ((MainActivity) getActivity()).showFragment(new MainFragment());
                                        } else {
                                            DialogFactory.showAlertDialog(getContext(), getString(R.string.error_invalid_email), "Oups");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                        DialogFactory.showAlertDialog(getContext(), getString(R.string.server_error), getString(R.string.title_server_error));
                                    }
                                    break;
                            }
                            progressDialog.dismiss();

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                            progressDialog.dismiss();
                            DialogFactory.showAlertDialog(getContext(), getString(R.string.server_error), getString(R.string.title_server_error));
                        }
                    });
                }
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showFragment(new InscriptionFragment());
            }
        });
    }

}
