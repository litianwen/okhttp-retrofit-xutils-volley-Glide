package com.example.xutils_imagemanger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ImageView imageView = (ImageView) findViewById(R.id.iv1);
//        ImageView imageView2 = (ImageView) findViewById(R.id.iv2);
//
//        //xUtils绑定网络图片
//        //第一个参数是ImageView对象
//        //第二个参数是URL字符串地址
//        x.image().bind(imageView,"http://p0.so.qhmsg.com/sdr/600_900_/t01d43698fbeca29695.jpg");
//
//        //xUtils绑定本地图片
//        //第一个参数是ImageView对象
//        //第二个参数是URL字符串地址,这里非要转换为File后在获取地址，直接使用"mnt/sdcard/a1.png"不行！
//        x.image().bind(imageView2, new File("mnt/sdcard/a1.png").getAbsolutePath());
//        //一般的如果是绑定本地图片，是不会使用框架的，下面这个语句就可以了
//        // imageView2.setImageURI(Uri.fromFile(new File("mnt/sdcard/a1.png")));


        //例2
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
