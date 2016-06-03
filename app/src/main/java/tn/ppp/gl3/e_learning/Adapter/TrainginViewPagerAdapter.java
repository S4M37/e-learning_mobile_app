package tn.ppp.gl3.e_learning.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import tn.ppp.gl3.e_learning.Fragment.QuestionFragment;
import tn.ppp.gl3.e_learning.Model.Exam;

/**
 * Created by S4M37 on 04/05/2016.
 */
public class TrainginViewPagerAdapter extends FragmentPagerAdapter {
    private Exam exam;
    private boolean isExam;

    public TrainginViewPagerAdapter(FragmentManager fm, boolean isExam) {
        super(fm);
        this.isExam = isExam;
    }


    public TrainginViewPagerAdapter(FragmentManager childFragmentManager, boolean b, Exam exam) {
        super(childFragmentManager);
        this.isExam = b;
        this.exam = exam;
    }

    @Override
    public Fragment getItem(int position) {
        return QuestionFragment.newInstance(isExam, exam.getItems()[position]);
    }

    @Override
    public int getCount() {
        return exam.getItems().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "question " + (position + 1);
    }
}
