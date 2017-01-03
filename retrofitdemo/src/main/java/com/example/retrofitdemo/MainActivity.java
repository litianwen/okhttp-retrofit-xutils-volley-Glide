package com.example.retrofitdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    //基地址
    String BASE_URL = "http://118.192.147.148/Matchbox/";

    TextView tv;//文本控件

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
    }
    //按钮一，get方式请求字符串数据
    public void btn1(View view) {
        //获取Retrofit对象
        Retrofit retrofit = getStringRetrofit();
        //转换成Call，在call对象包含Url的完整地址
        //可以看到这里使用了create的方法，里面传入了请求服务的接口类
        //并且后面接的是接口类的方法，方法里面传入Url地址要的键值对的值
        //这时call对象就有完整的Url地址，就可以请求数据了
        Call<String> call = retrofit.create(LoginService.class).login("13666666666", "666666");
        //使用call对象进行网络数据请求
        call.enqueue(new Callback<String>() {
            //网络数据请求成功的回调方法
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Log.e("TAG", call.request().toString());
                tv.setText(response.body().toString());
            }
            //网络数据请求失败的回调方法
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Toast.makeText(MainClass.this, "网络请求错误：" + t.getMessage(), Toast.LENGTH_LONG).show();
                tv.setText("网络请求错误：" + t.getMessage());
            }
        });

    }

    //按钮二，post方式请求字符串数据
    public void btn2(View view) {
        //获取Retrofit对象
        Retrofit retrofit = getStringRetrofit();
        //转换成Call，call里面包含了Url的地址
        Call<String> call = retrofit.create(LoginService2.class).login("13666666666", "666666");
        //使用call对象进行网路数据请求
        call.enqueue(new Callback<String>() {
            //网络数据请求成功时的回调方法
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                tv.setText(response.body().toString());
            }
            //网络数据请求失败时的回调方法
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                tv.setText("网络请求错误：" + t.getMessage());
            }
        });

    }

    /**
     * 使用post的方式请求示例，并且请求json类型的数据
     */
    public void btn3(View view) {
        //把网络数据传入对象中，这里使用的是GsonRetrofit
        Retrofit retrofit = getGsonRetrofit();
        //转换成Call，call里面包含了Url地址
        Call<LoginBean> call = retrofit.create(LoginService3.class).login("13666666666", "666666");
        //使用call进行网络数据请求
        call.enqueue(new Callback<LoginBean>() {
            //网络数据请求成功时的回调方法
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                //这里可以获得Json数据里面的任意一个对象的数据
                // Log.e("TAG", call.request().toString());
                tv.setText("提示信息：message=" + response.body().getMessage() + "\n" +
                        "返回码：result=" + response.body().getResult() + "\n" +
                        "用户名username=" + response.body().getUsername() + "\n" +
                        "性别sex=" + response.body().getSex() + "\n" +
                        "用户的IDuserId=" + response.body().getUserId() + "\n" +
                        "头像地址url=" + response.body().getUrl() + "\n" +
                        "基本信息myInfo=" + response.body().getMyInfo() + "\n"
                );
            }
            //网络数据请求失败时的回调方法
            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                tv.setText("网络请求错误：" + t.getMessage());
            }
        });

    }


    //返回一个Gson类型的对象
    public Retrofit getGsonRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }

    //返回一个Gson类型的字符串,
    public Retrofit getStringRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        return retrofit;
    }

}
