# okhttp网络请求的简单示例


---

okhttp网络请求的功能还是比较强大的。 
      这里这是简单的演示：字符串数据的请求和json数据的请求。 
      AndroidStudio使用Okhttp之前添加依赖就可以了： 
compile ‘com.squareup.okhttp3:okhttp:3.5.0’ 
      GitHub中的源码地址：https://github.com/square/okhttp 
一般要了解它的源码地址，作用，类和使用方法， 依赖的版本是会更新的。

一．布局文件
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
 >

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="net1"
        android:text="get请求方法" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="net2"
        android:text="post键值对请求" />
</LinearLayout>
```
      这里设计两个按钮，第一个按钮用来请求网页的源码数据，第二个按钮用来请求ShowAPI中的json数据。

二．请求数据的具体操作
```
package com.lwz.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


/***
 * 演示OkHttp网络框架的使用
 * OkHttp是用于网络请求数据的一个网络框架工具
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * get请求方式
     * 请求百度网页的源码数据
     */
    public void net1(View view) {
        //创建网络处理的对象
        OkHttpClient client = new OkHttpClient.Builder()
                //设置读取数据的时间
                .readTimeout(5, TimeUnit.SECONDS)
                //对象的创建
                .build();
        //创建一个网络请求的对象，如果没有写请求方式，默认的是get
        //在请求对象里面传入链接的URL地址
        Request request = new Request.Builder()
                .url("https://www.baidu.com").build();

        //call就是我们可以执行的请求类
        Call call = client.newCall(request);
        //异步方法，来执行任务的处理，一般都是使用异步方法执行的
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                Log.e(“TAG”,Thread.currentThread().getName() + "结果  " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //成功
                //子线程
                //main thread1
               Log.e(“TAG”,Thread.currentThread().getName() + "结果  " + response.body().string());
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

    /**
     * post请求方式，请求网络数据
     * 请求ShowAPI里面的json数据
     */
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
                Log.e(“TAG”,"错误信息：" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
               Log.e(“TAG”,response.body().string());
            }
        });
    }


}

```




