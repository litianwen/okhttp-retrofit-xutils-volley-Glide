# Android网络框架Volley的使用总结


---

    其实Volley网络框架实现了二级缓存机制（内存和网络），比如页面的信息刚使用Volley网络框架来请求数据，那么它的数据可以保存在缓存中，页面跳转到其他页面后，再跳转到原来缓存过的页面，那么这时是不需要重复请求网络数据的。但是内存保存时间并不长久，它只能保证短时间内相同的数据资源不需要再次请求网络，从而节省用户流量。 
       如果是要数据长久保存需要用到三级缓存机制。使用Volley也可以实现三级缓存机制，但是需要我们自己对数据经行判断和写入本地。 

一．Volley网络框架基础介绍

Volley是一个网络通信库，它使得Android进行网络请求时更加方便、快速、健壮，同时对网络图片加载也提供了良好的支持。 
Volley中有Android 异步网络请求框架和图片加载框架。 
如果要下载资源的包可以在github中直接下载，网址：https://github.com/mcxiaoke/android-volley 
如果是使用AndroidStudio开发，可以直接依赖包：
```
compile ‘com.android.volley:volley:1.0.0’
```
（一）Volley试用场景：

       适合数据量小，通信频繁的网络操作。文本数据应用较多。比如也几个页面的文本数据，只要查看过一次后，以后来回切换，或退出程序再进入页面都是不用网络都能显示文本的。 
       如果加载较多的图片还是使用其他的强大一点的网络框架会好点。手机内存毕竟容量有限。 
       AndroidStudio使用Volley之前添加依赖就可以了： 
compile ‘com.android.volley:volley:1.0.0’ 
GitHub中的源码地址： 
https://github.com/mcxiaoke/android-volley

（二）Volley四个类的作用：

1.RequestQueue：请求队列

通过 newRequestQueue(…) 函数新建并启动一个请求队列RequestQueue。

2.Request：请求的抽象类。

StringRequest、JsonRequest、ImageRequest 都是它的子类，表示某种类型的请求。

3.ImageLoader：有缓存的图片下载的操作类

4.LruCache数据缓存类

（三）StringRequest的使用方法:

1. 调用Volley的static方法 **newRequestQueue(context)**,

       得到**RequestQueue对象**, 之后所有的网络请求都添加到这个RequestQueue对象就可以了. 
```
RequestQueue mQueue = Volley.newRequestQueue(context);
```
2. new一个StringRequest对象， 访问url， 得到服务端返回的字符串.

```
StringRequest stringRequest = new StringRequest("http://www.baidu.com",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("TAG", response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("TAG", error.getMessage(), error);
                            }
                        });
```
StringRequest的构造函数需要传入三个参数，第一个参数就是目标服务器的URL地址，第二个参数是服务器响应成功的回调，第三个参数是服务器响应失败的回调。

3. 最后，将这个StringRequest对象添加到RequestQueue里面就可以了.
```
mQueue.add(stringRequest); 
```
4. 默认是GET请求， 如果要使用POST请求的话， 就需要使用带4个参数的StringRequest()构造方法. 请求参数通过重写父类Request中的getParams()方法来设置.
```
StringRequest stringRequest = new StringRequest(Method.POST, url,  listener, errorListener) {
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> map = new HashMap<String, String>();
        map.put("params1", "value1");
        map.put("params2", "value2");
        return map;
    }
};
```
（四）JsonRequest的用法

JsonRequest有两个直接的子类，JsonObjectRequest和JsonArrayRequest，一个是用于请求一段JSON数据的，一个是用于请求一段JSON数组的。 
使用方法和StringRequest类似.
```
JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
        new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
            }
        });
mQueue.add(jsonObjectRequest);
```
       Volley是将AsyncHttpClient和Universal-Image-Loader的优点集成于一身的一个框架。我们都知道，Universal-Image-Loader具备非常强大的加载网络图片的功能，而使用Volley，我们也可以实现基本类似的效果，并且在性能上也豪不逊色于Universal-Image-Loader，下面我们就来具体学习一下吧。

（五）ImageRequest的用法
```
ImageRequest imageRequest = new ImageRequest(
        "http://developer.android.com/images/home/aw_dac.png",
        new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, 0, 0, Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                imageView.setImageResource(R.drawable.default_image);
            }
        });
```
       第三第四个参数分别用于指定允许图片最大的宽度和高度，如果指定的网络图片的宽度或高度大于这里的最大值，则会对图片进行压缩，指定成0的话就表示不管图片有多大，都不会进行压缩。第五个参数用于指定图片的颜色属性，其中ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，而RGB_565则表示每个图片像素占据2个字节大小。 
       第六个参数是图片请求失败的回调，这里我们当请求失败时在ImageView中显示一张默认图片。 
       最后把这个ImageRequest对象添加到RequestQueue对象中去就可以了. 
```
mQueue.add(imageRequest);
```
（六）ImageLoader的用法

       为了让开发者更方便的加载网络图片，Volley还提供了ImageLoader, 并且它的内部也是使用ImageRequest来实现的，不过ImageLoader明显要比ImageRequest更加高效，因为它不仅可以帮我们对图片进行缓存，还可以过滤掉重复的链接，避免重复发送请求。 
分为以下几步:
```
创建一个ImageLoader对象
ImageLoader imageLoader = new ImageLoader(mQueue, new ImageCache() {
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
    }

    @Override
    public Bitmap getBitmap(String url) {
        return null;
    }
});//这里并没有让ImageLoader具备缓存功能.
获取一个ImageListener对象
ImageListener listener = ImageLoader.getImageListener(imageView,
        R.drawable.default_image, R.drawable.failed_image);//第一个参数指定用于显示图片的ImageView控件，//第二个参数指定加载图片的过程中显示的图片，第三个参数指定加载图片失败的情况下显示的图片。
调用ImageLoader的get()方法加载网络上的图片
imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg", listener);
要使用到ImageLoader的缓存功能， 就需要提供一个ImageCache接口的实现类.
这里， 我们在实现的内部通过Android framework提供的LruCache实现缓存.
public class BitmapCache implements ImageCache {

    private LruCache<String, Bitmap> mCache;

    public BitmapCache() {
        int maxSize = 10 * 1024 * 1024; //提供一个10Mb的缓存.
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mCache.put(url, bitmap);
    }

}
ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());
```
二.Volley的一个简单示例

本程序演示几种简单的网络请求， 
1.get请求文本资源， 
2.post请求文本资源 
3.请求网络图片 
4.使用一级缓存的方式请求图片 
程序运行效果，如图所示：

点击对于的按钮会触发相应的事件。。。 
上面前两个按钮请求网络的方法都是没有带缓存的，第三、四个按钮的请求网络数据后是带缓存的。第三个按钮使用的是系统的BitmapFactory.decodeByteArray 
缓存，第四个按钮使用的是手动设置的缓存。

（一）添加权限
```
<uses-permission android:name="android.permission.INTERNET" />
```
（二）设计布局页面
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="net1"
        android:text="请求网络String" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="net2"
        android:text="请求json,带参post" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="net3"
        android:text="图片下载" />

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:onClick="net4"
        android:text="缓存图片下载" />

    <ImageView
        android:id="@+id/main_iv"
        android:layout_width="match_parent"
        android:layout_height="300dp" />
</LinearLayout>
```
上面是比较简单的几个按钮和一个图像显示的布局页面。

（三）java代码的设计
```
package com.lwz.volley;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * 网络框架工具类Volley 的使用
 */
public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.main_iv);
        ShowUtils.DEBUG = false;
    }

    /**
     * 请求网络的字符串，比如json数据，网页的源码等等
     * 这里请求的是网页的源码
     */
    public void net1(View view) {
        //使用Volley的静态方法来获得队列对象
        RequestQueue queue = Volley.newRequestQueue(this);
        //创建请求对象,三个参数
        //第一个参数：URL的字符串
        //第二个参数：连接成功的回调接口
        //第三个参数：连接失败的回调接口
        Request request = new StringRequest("https://www.github.com", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //连接成功后
                ShowUtils.e("response" + response);//打印输出
            }
        }, error);
        //将请求添加到队列中，并开始执行下载任务
        queue.add(request);
    }

    /**
     * 请求网络的字符串，使用Post的方式，默认是get的方式
     * 这里在 StringRequest方法的基础上添加一个请求方式的参数
     * 这里请求的是API的json数据
     */
    public void net2(View view) {
        //使用Volley的静态方法来获得队列对象
        RequestQueue queue = Volley.newRequestQueue(this);
        //创建请求对象
        //第一个参数：请求的方式get或post
        //第二个参数：URL的字符串
        //第三个参数：连接成功的回调接口
        //第四个参数：连接失败的回调接口
        //这里在创建连接对象时，在连接的URL上添加参数，
        Request request = new StringRequest(Request.Method.POST, "http://route.showapi.com/255-1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //连接成功后
                ShowUtils.e("response" + response);//打印输出
            }
        }, error) {
            //向请求的对象中添加系统级的参数
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //创建一个HashMap对象来存放程序API的注册id和密钥
                Map<String, String> map = new HashMap<>();
                map.put("showapi_appid", "27350");
                map.put("showapi_sign", "d734772ec0fc4b2aac97d6a7f036f767");
                return map;
            }
        };
        //将请求添加到队列中，并开始执行下载任务
        queue.add(request);
    }

    /**
     * 请求网络的图片资源，并显示在页面上，这里是做了二级缓存机制
     */
    public void net3(View view) {
        //使用Volley的静态方法来获得队列对象
        RequestQueue queue = Volley.newRequestQueue(this);
        //创建请求图片对象，七个参数
        //第一个参数，URL的地址字符串
        //第二个参数，网络数据请求成功后的回调方法
        //第三个参数，图片的宽
        //第四个参数，图片的高
        //第五个参数，图片的缩放方式
        //第六个参数，图片的压缩品质格式
        //第七个参数，网络请求失败的回调方法
        Request request = new ImageRequest("https://www.showapi.com/images/apiLogo/20150606/5423acc973f41c03173a186a_22b6c6381e3444449b2cfb3724e63c6c.jpg",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        ShowUtils.e("response" + 111);//打印输出
                        //把下载到的Bitmap对象放到指定的图片对象中显示
                        imageView.setImageBitmap(response);
                    }
                }, 200, 200, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565, error);
        //将请求添加到队列中，并开始执行下载任务
        queue.add(request);
    }

    /**
     * 请求网络的图片资源，下载图片在本地和内存中,并显示在页面上，这里也是做了二级缓存机制
     *内存数据的存储需要用到一个类：LruCache
     */
    public void net4(View view) {
        //使用Volley的静态方法来获得队列对象
        RequestQueue queue = Volley.newRequestQueue(this);

        //创建下载图片对象，两个参数
        //第一个参数：队列对象
        //第二个参数：图片下载成功后的缓存接口对象
        ImageLoader loader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                //通過内存类获取数据
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                //通過内存类保存数据
                cache.put(url, bitmap);
            }

            //创建一个图片缓存数据的对象,需要的参数是图片可保存的数据大小
            int data = (int) (Runtime.getRuntime().maxMemory() / 8);
            LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(data) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    //返回图片数据的大小
                    return value.getByteCount();
                }
            };
        });

        //执行下载操作
        //设置下载图片的对象， 两个参数
        // 第一个参数:URL地址
        //第二个参数，网络请求成功的回调对象
        loader.get("http://pic1.win4000.com/pic/2/5b/557796359.jpg", new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                //把下载到的Bitmap对象放到指定的图片对象中显示
                imageView.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    /**
     * 创建一个连接错误的监听对象
     * 因为上面每一个请求的对象都需要错误接口的监听对象，所以这里抽出来写
     */
    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            //打印错误信息
            ShowUtils.e("错误信息：" + error.getMessage());
        }
    };


}
```
（四）上面使用了一个显示Log的工具类
```
package com.lwz.volley;

import android.util.Log;

/**
 * 本类用于简易的Log显示信息
 * 
 */

public class ShowUtils {
    //这里DEBUG的作用是，可以在程序完成后设置DEBUG的值为false，程序以后就不会再显示以前的打印信息
    public static boolean DEBUG = true;

    //各种Log打印
    public static void e(Object o) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + o.toString());
    }

    public static void e(int i) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + i);
    }

    public static void e(float i) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + i);
    }

    public static void e(boolean b) {
        if (DEBUG)
            Log.e("TAG", "打印：------      " + b);
    }
}

```
上面程序在断网中也能请求并显示图片，但是不能再请求字符串。第四种请求方式比第三种请求方式更高效。。

       使用Volley的设计目标就是非常适合去进行数据量不大，但通信频繁的网络操作, 例如像微博这样的APP或类似新闻客户端的APP.