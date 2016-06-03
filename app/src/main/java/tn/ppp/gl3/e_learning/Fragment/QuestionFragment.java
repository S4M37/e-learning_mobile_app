package tn.ppp.gl3.e_learning.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import tn.ppp.gl3.e_learning.Model.Choice;
import tn.ppp.gl3.e_learning.Model.Item;
import tn.ppp.gl3.e_learning.R;

/**
 * Created by S4M37 on 04/05/2016.
 */
public class QuestionFragment extends Fragment {
    private View rootView;
    private TextView viewResponseButton;
    private CheckBox[] choices;
    private boolean isExam;
    private TextView label;
    private Item item;
    private LinearLayout responseLayout;

    public static QuestionFragment newInstance(boolean isExam, Item item) {

        Bundle args = new Bundle();
        args.putBoolean("isExam", isExam);
        args.putSerializable("item", item);
        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.question_fragment, container, false);
        isExam = getArguments().getBoolean("isExam");
        item = (Item) getArguments().getSerializable("item");
        inisializeView();
        return rootView;
    }

    private void inisializeView() {
        label = (TextView) rootView.findViewById(R.id.label);
        label.setText(item.getLabel());
        responseLayout = (LinearLayout) rootView.findViewById(R.id.response_layout);
        choices = new CheckBox[item.getChoices().length];
        for (int i = 0; i < item.getChoices().length; i++) {
            Choice choice = item.getChoices()[i];
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(choice.getLabel());
            checkBox.setId(choice.getId_choice());
            responseLayout.addView(checkBox);
            choices[i] = checkBox;
        }
        if (!isExam) {
            viewResponseButton = (TextView) rootView.findViewById(R.id.button_view_response);
            viewResponseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < item.getChoices().length; i++) {
                        Choice choice = item.getChoices()[i];
                        if (choices[i].isChecked() && choice.getValid() == 1) {
                            choices[i].setTextColor(Color.GREEN);
                        } else if (choices[i].isChecked() && choice.getValid() == 0) {
                            choices[i].setTextColor(Color.RED);
                        } else {
                            choices[i].setTextColor(Color.BLACK);
                        }
                    }
                }
            });
        } else {
            rootView.findViewById(R.id.card_view_view_response).setVisibility(View.GONE);
        }
    }

}
