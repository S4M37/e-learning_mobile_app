package tn.ppp.gl3.e_learning.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Calendar;

import tn.ppp.gl3.e_learning.Activity.ExamActivity;
import tn.ppp.gl3.e_learning.Adapter.TrainginViewPagerAdapter;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.DialogFactory;
import tn.ppp.gl3.e_learning.Widget.CircularCountDown;

/**
 * Created by S4M37 on 31/05/2016.
 */
public class ExamFragment extends Fragment {

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

    public static ExamFragment newInstance(Exam exam) {

        Bundle args = new Bundle();
        args.putSerializable("exam", exam);
        ExamFragment fragment = new ExamFragment();
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
        trainginViewPagerAdapter = new TrainginViewPagerAdapter(getChildFragmentManager(), true, exam);
        viewPager.setAdapter(trainginViewPagerAdapter);
        tabLayout.setTabsFromPagerAdapter(trainginViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void inisializeView() {

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        collapsingToolbar = (CollapsingToolbarLayout) rootView.findViewById(R.id.toolbar_layout);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        chronometerLayout = (LinearLayout) rootView.findViewById(R.id.chronometer_layout);
        calinit = Calendar.getInstance();
        calfin = Calendar.getInstance();
        calfin.add(Calendar.MINUTE, exam.getDuration());
        img = new CircularCountDown(getActivity(), true, 250, calinit, calfin, true);
        chronometerLayout.addView(img);
        img.setId(R.id.img);
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
                                for (int i = 0; i < exam.getItems().length; i++) {
                                    QuestionFragment questionFragment = (QuestionFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + i);
                                    int res = questionFragment.getResult();
                                    Log.d("res", res + "");
                                    if (res == -1) {
                                        hasResponse = false;
                                        DialogFactory.showAlertDialogEmptyResponse(getContext(), i + 1);
                                        viewPager.setCurrentItem(i);
                                        break;
                                    } else {
                                        response[i] = questionFragment.getResult();
                                    }
                                }
                                ((ExamActivity) getActivity()).calulateScore(response, hasResponse);
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

}
