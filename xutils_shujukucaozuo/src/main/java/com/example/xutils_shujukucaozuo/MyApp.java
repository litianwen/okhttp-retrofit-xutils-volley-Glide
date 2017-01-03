package com.example.xutils_shujukucaozuo;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;


/**
 * Created by Administrator on 2016/12/29.
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
