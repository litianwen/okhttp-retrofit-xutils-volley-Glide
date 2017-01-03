package com.example.fileutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void net1(View view){
        OkHttpClient client = new OkHttpClient.Builder()
                //设置读取数据的时间
                .readTimeout(5, TimeUnit.SECONDS)
                //对象的创建
                .build();

        Request request = new Request.Builder()
                .url("https://www.baidu.com").build();


        //call就是我们可以执行的请求类
        Call call = client.newCall(request);
        //异步方法，来执行任务的处理，一般都是使用异步方法执行的
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e("TAG",Thread.currentThread().getName() + "结果  " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                //子线程
                //main thread1
                Log.e("TAG",Thread.currentThread().getName() + "结果  " + response.body().string());
            }
        });
        //  call.cancel();取消任务

        //同步方法,一般不用
       /* try {
            Response execute = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void net2(View view) {
        //创建网络处理的对象
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .build();

        //post请求来获得数据
        //创建一个RequestBody，存放重要数据的键值对
        RequestBody body = new FormBody.Builder()
                .add("showapi_appid", "13074")
                .add("showapi_sign", "ea5b4bf2e140498bb772d1bf2a51a7a0").build();
        //创建一个请求对象，传入URL地址和相关数据的键值对的对象
        Request request = new Request.Builder()
                .url("http://route.showapi.com/341-3")
                .post(body).build();

        //创建一个能处理请求数据的操作类
        Call call = client.newCall(request);

        //使用异步任务的模式请求数据
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG","错误信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("TAG",response.body().string());
            }
        });
    }


}
