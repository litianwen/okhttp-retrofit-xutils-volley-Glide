package com.example.retrofitdemo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/12/29.
 */

public interface LoginService3 {
    @POST("useruserlogin")//post请求方式，这里要获取json对象数据必须要使用post请求，可能是服务器的原因。
    @FormUrlEncoded
//键值对的定义和数据的返回类型的定义这里是LoginBean对象数据
    Call<LoginBean> login(@Field("name") String username, @Field("password") String password);
}
