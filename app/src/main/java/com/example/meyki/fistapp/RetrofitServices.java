package com.example.meyki.fistapp;


import org.json.JSONObject;


import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface RetrofitServices {
    //get request
    @GET()
    Call<ResponseBody> getJSON(@Url String url);
//    //post request
//    @POST("{requestUrl}")
//    Call<ResponseBody> createUser(@Path("requestUrl") String url,@Body JSONObject jsonObject);
   //post request
    @POST("user/register/")
    Call<ResponseBody> createUser(@Body JSONObject jsonObject);
}

