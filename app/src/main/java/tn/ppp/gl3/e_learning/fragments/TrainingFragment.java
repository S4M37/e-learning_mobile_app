package tn.ppp.gl3.e_learning.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import java.util.Calendar;

import tn.ppp.gl3.e_learning.adapters.TrainginViewPagerAdapter;
import tn.ppp.gl3.e_learning.models.Exam;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.services.DialogFactory;
import tn.ppp.gl3.e_learning.widgets.CircularCountDown;

/**
 * Created by S4M37 on 04/05/2016.
 */
public class TrainingFragment extends Fragment {

    private View rootView;
    private FloatingActionButton fabDone;
    private ViewPager viewPager;
    private TrainginViewPagerAdapter trainginViewPagerAdapter;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout collapsingToolbar;
    private LinearLayout chronometerLayout;
    private Calendar calfin;
    private Calendar calinit;
    private CircularCountDown img;
    private Exam exam;
    private FloatingActionButton submitButton;

    public static TrainingFragment newInstance(Exam exam) {

        Bundle args = new Bundle();
        args.putSerializable("exam", exam);
        TrainingFragment fragment = new TrainingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.training_fragment, container, false);
        exam = (Exam) getArguments().getSerializable("exam");
        inisializeView();
        inisializingViewPager();
        return rootView;
    }

    private void inisializingViewPager() {
        trainginViewPagerAdapter = new TrainginViewPagerAdapter(getChildFragmentManager(), false, exam);
        viewPager.setAdapter(trainginViewPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(trainginViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void inisializeView() {
        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_layout);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        chronometerLayout = (LinearLayout) rootView.findViewById(R.id.chronometer_layout);
        showNumberPicker();
        submitButton = (FloatingActionButton) rootView.findViewById(R.id.fab);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.support.v7.app.AlertDialog.Builder builder =
                        new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Hey!");
                builder.setMessage("Have u finished ur exam?");
                builder.setPositiveButton("oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                int[] response = new int[exam.getItems().length];
                                boolean hasResponse = true;
                                int total = 0;
                                for (int i = 0; i < exam.getItems().length; i++) {
                                    QuestionFragment questionFragment = (QuestionFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + i);
                                    int res = questionFragment.getResult();
                                    if (res == -1) {
                                        hasResponse = false;
                                        DialogFactory.showAlertDialogEmptyResponse(getContext(), i + 1);
                                        viewPager.setCurrentItem(i);
                                        break;
                                    } else {
                                        response[i] = res;
                                    }
                                    total += res;
                                }
                                if (hasResponse) {
                                    float result = (float) total / exam.getItems().length;
                                    String label;
                                    if (result <= 0.5) {
                                        label = "fail";
                                    } else {
                                        label = "succeed";
                                    }
                                    android.support.v7.app.AlertDialog.Builder builder =
                                            new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
                                    builder.setTitle(getString(R.string.exam_backpressed_title));
                                    builder.setMessage(label + " with a score : " + result * 100 + "%");
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
                builder.setNegativeButton("pas encore", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    private void showNumberPicker() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout numberPickerDialogLayout = (LinearLayout) inflater.inflate(R.layout.number_picker_dialog_layout, null, false);
        final NumberPicker numberPicker = (NumberPicker) numberPickerDialogLayout.findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(180);
        android.support.v7.app.AlertDialog.Builder builder =
                new android.support.v7.app.AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getString(R.string.number_picker_title));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int number = numberPicker.getValue();
                calinit = Calendar.getInstance();
                calfin = Calendar.getInstance();
                calfin.add(Calendar.MINUTE, number);
                img = new CircularCountDown(getActivity(), true, 250, calinit, calfin, false);
                chronometerLayout.addView(img);
                img.setId(R.id.img);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(numberPickerDialogLayout);
        alertDialog.show();
    }

}
