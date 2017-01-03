# Android网络框架xUtils的数据库操作


---

一．目前xUtils主要有四大模块：

    DbUtils模块：用于数据库的操作，也是需要注解方式指定表名和列名；
    ViewUtils模块：注解方式就可以进行UI，资源和事件绑定； 
    HttpUtils模块：用于网络数据的请求； 
    BitmapUtils模块：用于图片的下载和绑定视图；

      本文主要讲解xUtils中的数据库的增删改查操作。

使用：
```
AndroidStudio使用xUtils之前添加依赖就可以了：
    compile 'org.xutils:xutils:3.3.38'
GitHub中的源码地址：https://github.com/wyouflf/xUtils3
```
二．DbUtils的使用示例

（一）添加依赖
```
compile 'org.xutils:xutils:3.3.38'
```
（二）创建MyApp类继承Application
```
package com.lwz.db;

        import android.app.Application;

        import org.xutils.DbManager;
        import org.xutils.x;

/**
 * Application类，
 * 单例方法，数据共享，
 */

public class MyApp extends Application {

    /**
     * 实现单例，任何一个页面都能拿到这个类的数据和对象
     */
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//注册xUtils
        instance = this;
    }

    /**
     * 获取数据库的管理器
     * 通过管理器进行增删改查
     */
    public DbManager getDbManager() {
        DbManager.DaoConfig daoconfig = new DbManager.DaoConfig();
        //默认在data/data/包名/database/数据库名称
//        daoconfig.setDbDir()
        daoconfig.setDbName("user.db");
//        daoconfig.setDbVersion(1);//默认1
        //通过manager进行增删改查
        return x.getDb(daoconfig);
    }
}
```
      这里在MyApp类中创建数据库的管理对象后，在整个App内的所有页面都内调用里面的对象和数据。 值得注意的是，MyApp要在AndroidManifest中注册 
（三）布局文件设计
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="save"
        android:text="save"
        android:textAllCaps="false" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="delete"
        android:text="delete"
        android:textAllCaps="false" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="update"
        android:text="update"
        android:textAllCaps="false" />

    <Button
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="query"
        android:text="query"
        android:textAllCaps="false" />
</LinearLayout>
```
    这里显示四个按钮实现简单的增删改查。 
    Android:textAllCaps=”false” 解决了按钮默认显示字母大写的情况。

（四）创建数据原型
```
package com.lwz.db;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 数据原型
 * 这里要添加数据的注解，就可以对应数据库中的表和字段，
 * 注解也是xUtils中的工具类完成的
 */
@Table(name = "table_user")//注释表名
public class User {


    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", id=" + id +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }

    @Column(name = "username")//注释列名
    private String username;

    @Column(name = "id", isId = true, autoGen = true)//注释列名主键，主动增长
    private int id;

    @Column(name = "password")//注释列名
    private String password;

    @Column(name = "age")//注释列名
    private int age;

    @Column(name = "sex")//注释列名
    private String sex;


    //必须有空参的构造方法和set与get


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
```
（五）增删改查的代码设计
```
package com.lwz.db;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 实现数据库的存储
     */
    public void save(View view) {
        //获取数据库管理器
        DbManager manager = MyApp.getInstance().getDbManager();
        //数据
        User user = new User();
        // user.setId(1);//自增长的id设置了也是没有用的
        user.setSex("女");
        user.setPassword("123456");
        user.setUsername("李文");
        user.setAge(18);
        try {
            //实现数据的存储,配合User类中的注释才能进行对应的存储
            //表名和列名都是在User中注释决定的。
            manager.save(user);//保存
            // manager.saveOrUpdate(user);//保存或更新，这如果数据不存在是不会保存的，存在的话会跟新
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除数据
     * //删除整个表的所有数据
     * //manager.delete(User.class);
     * //删除指定id的记录
     * //manager.deleteById(User.class,1);
     * //删除某一类数据  where name="张三"
     * manager.delete(User.class, WhereBuilder.b("username", "=", "王五").and("age", "<", "100"));
     */
    public void delete(View view) {
        DbManager manager = MyApp.getInstance().getDbManager();
        try {
            manager.delete(User.class, WhereBuilder.b("username", "=", "李文"));
        } catch (DbException e) {
            e.printStackTrace();

        }
    }

    /**
     * 修改数据
     */
    public void update(View view) {
        DbManager manager = MyApp.getInstance().getDbManager();
        //要修改的数据，以键值对的显示传入,
        KeyValue keyValue = new KeyValue("username", "李世民");
        try {
            //过滤年龄小于20的数据就修改,这里可以设置多个keyValue值
            manager.update(User.class, WhereBuilder.b("age", "<", "20"), keyValue);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查找数据
     */
    public void query(View view) {
        //查询所有
        //获取管理器
        DbManager manager = MyApp.getInstance().getDbManager();
        try {
            List<User> all = manager.findAll(User.class);
            //manager.findById()找单个的对象
            for (int i = 0; i < all.size(); i++) {
                Log.e("TAG", all.get(i).toString());//打印显示
            }

        } catch (DbException e) {
            e.printStackTrace();
        }
    }

}
```
        这里就用xUtils简单的实现了增删改查的功能， 
        这里是可以添加文本框来实现某些数据的增删改查的。 
        通过xUtils实现数据库的增删改查，不需要写很多的创建表和列的语句，对应用户开发是非常方便的。



