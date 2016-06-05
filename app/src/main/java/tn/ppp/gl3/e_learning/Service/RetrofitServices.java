package tn.ppp.gl3.e_learning.Service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

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
    Call<ResponseBody> getExams(@Header("Authorization") String token);


    @FormUrlEncoded
    @POST("mobile/exams/{id_Exam}/submit")
    Call<ResponseBody> storeResult(@Header("Authorization") String token,@Path("id_Exam") int id_exam, @Field("id_User") int idUser, @Field("inputs[]") int[] response);

    @GET("user/{id_User}/results")
    Call<ResponseBody> getResultsForUser(@Header("Authorization") String token,@Path("id_User") int id_user);

    @GET("courses/{id_Course}/download")
    @Streaming
    Call<ResponseBody> downloadCourse(@Path("id_Course") int id_course);

    @GET("courses")
    Call<ResponseBody> getCourses();
}


