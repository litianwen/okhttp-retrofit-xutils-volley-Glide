# Android框架之图片框架Glide



---

一．Glide的简单介绍

      Glide是网络框架中加载图片最简单使用的一种工具。
```
 依赖： compile 'com.github.bumptech.glide:glide:3.7.0'
GitHub中的源码地址：https://github.com/bumptech/glide
```
      我们需要了解它的源码地址，作用，类和使用方法， 依赖的版本是会更新的。 
      比如下载一张网络的图片到ImagView中，使用一句话就可以了：
```
Glide.with(this).load("http://p0.so.qhmsg.com/t015a2d97fc46c534d0.jpg")
.into(imageView).onLoadStarted(new ColorDrawable(0xf00));
```
      图片加载库的优势就在于此。简简单单一句话，下载，缓存，加载统统搞定。简直就是美好一生的东西。而Glide就是这样使人美好一生的东西之一。

      下表是.load()可以传入的参数及说明

 | 参数     |   说明  |
 | --------   |  ----:  |
 | .load(String string)	| string可以为一个文件路径、uri或者url
 | .load(Uri uri)	| uri类型
 | .load(File file)	| 文件
 | .load(Integer resourceId) |资源Id,R.drawable.xxx或者R.mipmap.xxx
 | .load(byte[] model)	| byte[]类型
 | .load(T model)	| 自定义类型
      .onLoadStarted（） 表示的是图片没加载完成前显示的图像。可以是资源文件，可以是一个颜色的背景。

设计代码：
```
package com.lwz.glide;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView imageView=new ImageView(this);
        setContentView(imageView);
        //使用glide非常简单，一句话就可以了 
         Glide.with(this).load("http://p0.so.qhmsg.com/t015a2d97fc46c534d0.jpg")
         .into(imageView).onLoadStarted(new ColorDrawable(0xf00));

    }
}
```
      当然Glide也可以用来加载本地的图片资源，但是主要需求还是用来请求网络的图片资源。

      正常情况下请求网络数据还要使用异步任务或子线程来，而这里使用Glide就可以很方便的请求到图片的资源数据，并绑定到某个控件上。

      这里有Glide的另一个工具包glide-transformations，也是非常受欢迎的，并且可以支持圆角显示最终返回的图片！

依赖：
```
compile ‘jp.wasabeef:glide-transformations:2.0.1’ 
```
这是Github中的地址：https://github.com/wasabeef/glide-transformations

加载显得成普通图片的语句：和上面的语句一模一样！ 
                    
    加载显示成圆角图片的语句： 
```
Glide.with(this).load(path).bitmapTransform(new CropCircleTransformation(this)).into(imageView);
```

