package com.example.xutils_shujukucaozuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
