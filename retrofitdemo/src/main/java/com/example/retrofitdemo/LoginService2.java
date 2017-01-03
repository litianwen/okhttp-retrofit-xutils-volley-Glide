package com.example.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/12/29.
 */

public interface LoginService2 {
    @POST("useruserlogin")//post请求方式

    @FormUrlEncoded
//键值对的定义和数据的返回类型的定义这里是String

    Call<String> login(@Field("name") String username, @Field("password") String password);
}
