package tn.ppp.gl3.e_learning.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import tn.ppp.gl3.e_learning.Activity.MainActivity;
import tn.ppp.gl3.e_learning.Model.User;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.CompteManager;
import tn.ppp.gl3.e_learning.Service.DialogFactory;

/**
 * Created by S4M37 on 04/05/2016.
 */
public class MainFragment extends Fragment {

    private View rootView;
    private Button courcesButton;
    private Button trainingButton;
    private Button examButton;
    private Button signinButton;
    private Button aboutUsButtton;
    private User user;
    private Button resultButtton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.main_fragment, container, false);
        user = CompteManager.getCurrentUser(getContext());
        inisializeView();
        return rootView;
    }

    private void inisializeView() {
        courcesButton = (Button) rootView.findViewById(R.id.cources_button);
        trainingButton = (Button) rootView.findViewById(R.id.training_button);
        examButton = (Button) rootView.findViewById(R.id.exam_button);
        signinButton = (Button) rootView.findViewById(R.id.signin_button);
        aboutUsButtton = (Button) rootView.findViewById(R.id.aboutus_button);
        resultButtton = (Button) rootView.findViewById(R.id.result_button);
        if (user == null) {
            resultButtton.setEnabled(false);
        } else {
            resultButtton.setEnabled(true);
        }
        courcesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        trainingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showFragment(new CategoryFragment());
            }
        });
        examButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user == null) {
                    DialogFactory.showAlertDialog(getContext(), getString(R.string.registration_request), getString(R.string.title_server_error));
                } else {
                    ((MainActivity) getActivity()).showFragment(new ExamsFragment());
                }
            }
        });
        aboutUsButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        resultButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showFragment(new ResultFragment());
            }
        });

        if (user != null) {
            signinButton.setText(R.string.logout);
            signinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signinButton.setText(R.string.login);
                    CompteManager.logout(getContext());
                    user = null;
                    resultButtton.setEnabled(false);
                    signinButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((MainActivity) getActivity()).showFragment(new LoginFragment());
                        }
                    });
                }
            });
        } else {
            signinButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).showFragment(new LoginFragment());
                }
            });
        }

    }
}
