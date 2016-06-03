package tn.ppp.gl3.e_learning.Fragment;

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

import tn.ppp.gl3.e_learning.Adapter.TrainginViewPagerAdapter;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Widget.CircularCountDown;

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
