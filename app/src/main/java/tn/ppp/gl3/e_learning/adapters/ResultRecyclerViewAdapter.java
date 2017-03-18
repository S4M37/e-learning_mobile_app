package tn.ppp.gl3.e_learning.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tn.ppp.gl3.e_learning.models.Result;
import tn.ppp.gl3.e_learning.R;

/**
 * Created by S4M37 on 04/06/2016.
 */
public class ResultRecyclerViewAdapter extends RecyclerView.Adapter<ResultRecyclerViewAdapter.ViewHolder> {

    private Result[] results;
    private Context context;

    public ResultRecyclerViewAdapter(Context context, Result[] results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public ResultRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultRecyclerViewAdapter.ViewHolder holder, int position) {
        Result result = results[position];
        holder.resultScore.setText(result.getScore() * 100 + "%");
        holder.resultObservation.setText(result.getObservation());
        holder.resultExamName.setText(result.getExam().getLabel());
        holder.resultExamDate.setText(result.getTest_date());
    }

    @Override
    public int getItemCount() {
        return results.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView resultScore;
        public TextView resultObservation;
        public TextView resultExamName;
        public TextView resultExamDate;

        public ViewHolder(View itemView) {
            super(itemView);
            resultScore = (TextView) itemView.findViewById(R.id.result_score);
            resultObservation = (TextView) itemView.findViewById(R.id.result_observation);
            resultExamName = (TextView) itemView.findViewById(R.id.result_label);
            resultExamDate = (TextView) itemView.findViewById(R.id.result_date);
        }
    }
}
