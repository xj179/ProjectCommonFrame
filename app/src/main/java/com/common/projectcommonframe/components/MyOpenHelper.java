package com.common.projectcommonframe.components;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.common.projectcommonframe.dao.DaoMaster;

import org.greenrobot.greendao.database.Database;

/**
 * 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
 * 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
 * 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
 * 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
 * 自定义DaoMaster.OpenHelper 用于升级数据库，系统默认的会清除掉数据
 */
public class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name) {
        super(context, name);
    }

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        if (newVersion > 1) {
            //SCHEMA_VERSION:2 增加灾害天气，异常停留等推送消息
//            TaobaoMessageDao.createTable(db, false);
        }
    }
}
