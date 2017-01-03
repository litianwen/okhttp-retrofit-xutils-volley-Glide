# Android网络框架Retrofit的综合使用

标签（空格分隔）： 未分类

---

一．Retrofit的相关介绍
 >Retrofit和OkHttp师出同门，也是Square的开源库，它是一个类型安全的网络请求库，Retrofit简化了网络请求流程，基于OkHtttp做了封装，解耦的更彻底。 
相关连接： 
官网： http://square.github.io/retrofit/ 
github：https://github.com/square/retrofit 
     但是Retrofit使用的话一般是结合其他框架综合使用达到非常方便获取数数据的效果。 
     之前在很多网站看到过一些知识点，但是对于第一次接触Retrofit的人还是比较困惑，并且它们提供的资料好多都不能完整运行出效果。 
     这里演示使用GET和POST的方式请求数据，这个网站是我之前专门练习网络请求的一个服务端，现在还可以访问。
     请求的网址基地址：http://118.192.147.148/Matchbox/ 
数据请求时的连接地址：http://118.192.147.148/Matchbox/useruserlogin?name=13666666666&password=666666 
     这个地址在服务器里面支持GET请求和POST请求，正常网址的服务器登陆肯定是不支持GET请求的，这里只是为了达到实验效果才特意进行设置的。

下面是实际操作的步骤：

 1. （一）添加依赖，这里依赖是比较多一点的
 
```
compile 'com.google.code.gson:gson:2.8.0'
compile 'com.squareup.retrofit2:retrofit:2.1.0'
compile 'com.squareup.okhttp3:okhttp:3.4.2'
compile 'com.squareup.retrofit2:converter-gson:2.1.0'
compile 'com.squareup.retrofit2:converter-scalars:2.0.0'
```
这里导入了gson解析，获得的对象可以很方便的拿到json字符串数据中的某个数据，并且没有什么步骤

 2. （二）添加权限
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
 3. （三）布局文件的设计

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">


    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Retrofit的简单使用" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="btn1"
        android:text="btn1" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="btn2"
        android:text="btn2" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="btn3"
        android:text="btn3" />

    <TextView
        android:id="@+id/tv"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="网络请求的到的数据" />


</LinearLayout>
```
这里设计三个按钮和一个文本，第一个按钮使用get方式请求网络数据，第二个按钮使用post方式请求网络数据，第三个按钮使用post请求json数据的具体对象；所有数据显示在TextView上。
4. （四）设计LoginBean文件
Bean文件的设计是根据，获得的数据来设置的，这里请求网址：http://118.192.147.148/Matchbox/useruserlogin?name=13666666666&password=666666 
     获得的数据为：{“message”:”登陆成功”,”result”:”0”,”username”:”我的生活加减法”,”sex”:”“,”userId”:317,”url”:”\/head\/94109ed4-fc44-4592-affc-9346d4cffd36.png”,”myInfo”:”“}

     在电脑输入上面的网址，就可以得到数据，一般我们程序设计也是先获得对应的json数据的字符串，分析结构后再设计Bean文件。

LoginBean.Java文件代码：
```
package mydemo;

/**
 * 登陆时的返回基本数据
 */

public class LoginBean {

    private String message;
    private String result;
    private String username;
    private String sex;
    private int userId;
    private String url;
    private String myInfo;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMyInfo() {
        return myInfo;
    }

    public void setMyInfo(String myInfo) {
        this.myInfo = myInfo;
    }
}
```

 1. （五）设计服务接口，这是Retrofit特殊的地方：每一个服务请求要写一个接口类
 1.第一个按钮的请求接口定义
```
package mydemo;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;

/**
 * 登陆服务器的请求
 * 这里使用get请求的方式请求数据
 * 并且到的是字符串数据
 */
interface LoginService {
    @GET("useruserlogin")//get请求方式
    @FormUrlEncoded      //键值对的定义和数据的返回类型的定义这里是String
    Call<String> login(@Field("name") String username, @Field("password") String password);
}
```
2.第二个按钮的请求接口定义
```
package mydemo;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
/**
 1. 登陆服务器的请求
 2. 这里使用post请求的方式请求数据
 3. 并且到的是字符串数据
 */
interface LoginService2 {

    @POST("useruserlogin")//post请求方式

    @FormUrlEncoded//键值对的定义和数据的返回类型的定义这里是String

    Call<String> login(@Field("name") String username, @Field("password") String password);
}
```
3.第三个按钮的请求接口定义
```
/**
 4. 登陆服务器的请求
 5. 这里使用post请求的方式请求数据
 6. 并且到的是对象数据，这个用法也是比较重要的和实用的
 */
interface LoginService3 {

    @POST("useruserlogin")//post请求方式，这里要获取json对象数据必须要使用post请求，可能是服务器的原因。
    @FormUrlEncoded//键值对的定义和数据的返回类型的定义这里是LoginBean对象数据
    Call<LoginBean> login(@Field("name") String username, @Field("password") String password);
}
```

 7. （六）主方法的类

```
package mydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import fuxi.retrofitdemo.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 *Retrofit的使用示例
 */

public class MainClass extends AppCompatActivity {
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
```


像没有Retrofit之前我们要获取到json数据里面的某个值，一般要经过至少两个步骤：先获取到json字符串，再通过Gson解析来获取json数据。 
     而使用Retrofit结合Gson框架，可以马上就把获取到的数据转换为json数据对象。
     

 1. 一般使用还会结合一下框架使用，这里做简单介绍它的依赖：

```
//Butterknifer注解框架的使用
    compile 'com.jakewharton:butterknife:8.4.0'
    apt 'com.jakewharton:butterknife-compiler:8.4.0'

   //加载网络框架的工具glide：加载图片的显示
    compile 'jp.wasabeef:glide-transformations:2.0.1'

    //加载Retrofit，用于网络数据的下载和数据的处理
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.squareup.retrofit2:retrofit:2.1.0'
    compile 'com.squareup.okhttp3:okhttp:3.4.2'
    compile 'com.squareup.retrofit2:converter-gson:2.1.0'
    compile 'com.squareup.retrofit2:converter-scalars:2.0.0'

 //加载网络框架LitePal用于数据库的操作实现
    compile 'org.litepal.android:core:1.4.1'
    
```
 
 
 
  


