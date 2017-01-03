# Android网络框架xUtils的图片处理操作



---

 - **使用示例**



（一）添加依赖语句
```
compile 'org.xutils:xutils:3.3.38'
```
（二）添加权限
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
```
（三）在自己的Application中的onCreate方法里注册xutils
```
x.Ext.init(this);
```
这里要记得在AndroidManifest中添加Application中的name。

（四）设计布局activity_main.xml文件

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="网络图片" />

    <ImageView
        android:id="@+id/iv1"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_margin="10dp"
        android:scaleType="fitXY" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="本地图片" />

    <ImageView
        android:id="@+id/iv2"
        android:layout_width="300dp"
        android:layout_height="300dp"
        android:layout_margin="10dp"
        android:scaleType="fitXY" />
</LinearLayout>
```
（五）设计java代码
```
package fuxi.xutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.iv1);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv2);

        //xUtils绑定网络图片
        //第一个参数是ImageView对象
        //第二个参数是URL字符串地址
        x.image().bind(imageView, "http://p0.so.qhmsg.com/sdr/600_900_/t01d43698fbeca29695.jpg");

        //xUtils绑定本地图片
        //第一个参数是ImageView对象
        //第二个参数是URL字符串地址,这里非要转换为File后在获取地址，直接使用"mnt/sdcard/a1.jpg"不行！
        x.image().bind(imageView2, new File("mnt/sdcard/a1.jpg").getAbsolutePath());
        //一般的如果是绑定本地图片，是不会使用框架的，下面这个语句就可以了
        // imageView2.setImageURI(Uri.fromFile(new File("mnt/sdcard/a1.jpg")));
    }
}
```

 - **复杂一点的使用示例**
设置图片的各种属性的设置，这里只做Java代码的设计，前需要的步骤和上面程序是一样的。布局文件也是用上面的，把两个TextView去掉就可以。
```
public class LoadImageView extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = (ImageView) findViewById(R.id.iv1);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv2);
        //设置图片属性的options
        ImageOptions options = new ImageOptions.Builder()
                //设置图片的大小
                .setSize(300, 300)
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true)
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                //设置加载过程中的图片
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                //设置加载失败后的图片
                .setFailureDrawableId(R.mipmap.ic_launcher)
                //设置使用缓存
                .setUseMemCache(true)
                //设置支持gif
                .setIgnoreGif(false)
                //设置显示圆形图片
                .setCircular(true).build();
        x.image().bind(imageView, "http://p4.so.qhmsg.com/sdr/600_900_/t01f4e6c53f0e23d459.jpg", options);

        //设置第二张图片属性的options
        ImageOptions options2 = new ImageOptions.Builder()
                //设置图片的大小
                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                //设置边角的圆形
                .setRadius(DensityUtil.dip2px(5))
                //创建
                .build();
        x.image().bind(imageView2, "http://p2.so.qhmsg.com/sdr/599_900_/t019e91b7618003e862.jpg", options2);
    }
}
```
        上面一样是显示两个图片，一个圆形的图片，一个简单设置显示的图片。                                                                     
        上面就是xutils绑定图片的使用的两种简单方式，第一种直接加载图片不能进行图片处理，第二种方式可以对图片进行各种处理。 