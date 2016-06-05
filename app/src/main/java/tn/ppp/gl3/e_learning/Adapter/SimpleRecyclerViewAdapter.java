package tn.ppp.gl3.e_learning.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tn.ppp.gl3.e_learning.Activity.ExamActivity;
import tn.ppp.gl3.e_learning.Model.Category;
import tn.ppp.gl3.e_learning.Model.Course;
import tn.ppp.gl3.e_learning.Model.Exam;
import tn.ppp.gl3.e_learning.Model.Item;
import tn.ppp.gl3.e_learning.R;
import tn.ppp.gl3.e_learning.Service.DialogFactory;
import tn.ppp.gl3.e_learning.Service.DownloadPdfFile;
import tn.ppp.gl3.e_learning.Utils.Utils;
import tn.ppp.gl3.e_learning.Widget.CircleImageView;

/**
 * Created by S4M37 on 02/06/2016.
 */
public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ViewHolder> {
    private Course[] courses;
    private Exam[] exams;
    private Category[] categories;
    private Context context;
    private ProgressDialog progressDialog;

    public SimpleRecyclerViewAdapter(Context context, Category[] categories) {
        this.context = context;
        this.categories = categories;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
    }

    public SimpleRecyclerViewAdapter(Context context, Exam[] exams) {
        this.context = context;
        this.exams = exams;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(context.getString(R.string.loading));
    }

    public SimpleRecyclerViewAdapter(Context context, Course[] courses) {
        this.context = context;
        this.courses = courses;
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
        } else if (exams != null) {
            final Exam exam = exams[position];
            holder.label.setText(exam.getLabel());
            String hint = "";
            int i;
            for (i = 0; i < exam.getCategories().length - 1; i++) {
                hint += exam.getCategories()[i].getLabel() + " - ";
            }
            hint += exam.getCategories()[i].getLabel();
            holder.hint.setText(hint);
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
        } else {
            final Course course = courses[position];
            holder.label.setText(course.getLabel());
            holder.hint.setText(course.getCategory().getLabel());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.show();
                    Call<ResponseBody> call = Utils.getRetrofitServices().downloadCourse(course.getId_cource());
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.code() == 200) {
                                String fileName = course.getLabel();
                                InputStream in = response.body().byteStream();
                                new DownloadPdfFile(in, context).execute(fileName);
                            } else {
                                try {
                                    Log.d("codeResponse", response.code() + "" + response.errorBody().string() + course.getId_cource());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
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
        }
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.length;
        } else if (exams != null) {
            return exams.length;
        } else {
            return courses.length;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView img;
        public TextView label;
        public TextView hint;

        public ViewHolder(View itemView) {
            super(itemView);
            img = (CircleImageView) itemView.findViewById(R.id.img_category);
            label = (TextView) itemView.findViewById(R.id.category_label);
            hint = (TextView) itemView.findViewById(R.id.hint_label);
        }
    }
}
