package tn.ppp.gl3.e_learning.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.ppp.gl3.e_learning.Activity.ExamActivity;
import tn.ppp.gl3.e_learning.Model.Category;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.Model.Item;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.DialogFactory;
import tn.ppp.gl3.e_learning.Utils.Utils;
import tn.ppp.gl3.e_learning.Widget.CircleImageView;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {
    private Exam[] exams;
    private Category[] categories;
    private Context context;
    private ProgressDialog progressDialog;

    public CategoryRecyclerViewAdapter(Context context, Category[] categories) {
        this.context = context;
        this.categories = categories;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
    }

    public CategoryRecyclerViewAdapter(Context context, Exam[] exams) {
        this.context = context;
        this.exams = exams;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (categories != null) {
            final Category category = categories[position];
            holder.label.setText(category.getLabel());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    Call<ResponseBody> call = Utils.getRetrofitServices().getTrainginByCategry(category.getId_category());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    JSONArray jsonArray = jsonObject.getJSONArray("response");
                                    Gson gson = new Gson();
                                    Item[] items = new Item[jsonArray.length()];
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        items[i] = gson.fromJson(String.valueOf(jsonArray.get(i)), Item.class);
                                    }
                                    context.startActivity(new Intent(context, ExamActivity.class).putExtra("isExam", false).putExtra("exam", new Exam(items)));
                                } catch (JSONException | IOException | NullPointerException e) {
                                    e.printStackTrace();
                                    DialogFactory.showAlertDialog(context, context.getString(R.string.server_error), context.getString(R.string.title_server_error));
                                }
                            } else {
                                DialogFactory.showAlertDialog(context, context.getString(R.string.server_error), context.getString(R.string.title_server_error))
                                ;
                            }
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
            });
        } else {
            final Exam exam = exams[position];
            holder.label.setText(exam.getLabel());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    android.support.v7.app.AlertDialog.Builder builder =
                            new android.support.v7.app.AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle(context.getString(R.string.exam_request_title));
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            context.startActivity(new Intent(context, ExamActivity.class).putExtra("isExam", true).putExtra("exam", exam));
                        }
                    });
                    builder.setMessage(context.getString(R.string.exam_request_message) + " " + exam.getDuration() + " minutes, Good Luck :D");
                    builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
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

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.length;
        } else {
            return exams.length;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView img;
        public TextView label;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img_category);
            label = (TextView) itemView.findViewById(R.id.category_label);
        }
    }
}
