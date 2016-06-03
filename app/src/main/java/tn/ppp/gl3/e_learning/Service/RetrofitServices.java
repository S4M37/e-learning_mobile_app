package tn.ppp.gl3.e_learning.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by S4M37 on 17/04/2016.
 */
public interface RetrofitServices {


    @FormUrlEncoded
    @POST("mobile/signin")
    Call<ResponseBody> signin(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("mobile/signup")
    Call<ResponseBody> signup(@Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("region") String region);

    @GET("mobile/categories")
    Call<ResponseBody> getCategories();

    @GET("mobile/categories/{id}/training")
    Call<ResponseBody> getTrainginByCategry(@Path("id") int id_category);

    @GET("mobile/exams")
    Call<ResponseBody> getExams();

}


