package com.common.projectcommonframe.components;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.common.projectcommonframe.dao.DaoMaster;
import com.common.projectcommonframe.dao.DaoSession;
import com.tencent.smtt.sdk.QbSdk;

import org.greenrobot.greendao.AbstractDaoMaster;

import java.util.ArrayList;

/**
 * 自定义Application，程序的入口，做一些初始化操作(友盟初始化<统计框架>，GreenDao,realm<数据库框架>初始化，Gilde,Fresco<图片加载框架></>初始化...)
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;
    private ArrayList<Activity> activities = new ArrayList<>();

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    public static MyApplication getInstance() {
        if (mInstance == null) {
            try {
                Object obj = Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
                if (obj == null) {
                    obj = Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
                }
                if (obj != null) {
                    mInstance = (MyApplication) obj;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // 雷神你个小傻蛋1234
//        initUmengShareSDK();                     //友盟初始化
//        initFresco();                           //fresco初始化
        QbSdk.initX5Environment(this, null);   //X5内核浏览器初始化(如果需要使用webview请用这个代替系统的webview)

        //管理Activity
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activities.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
        /*        count++;
                handler.removeMessages(100);*/
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
                //可以控制启动广告
              /*  count--;
                if (count == 0 && MyConstants.SHOW_AD) {
                    handler.sendEmptyMessageDelayed(100, 30000);
                }*/
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                activities.remove(activity);
            }
        });
    }

    /**
     * 初始化FaceBook的Fresco图片加载框架
     */
    private void initFresco() {
       /* ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, getNetworkClient().okHttpClient()).build();
        Fresco.initialize(this, config);*/
    }

    /**
     * 初始化友盟统计
     */
    private void initUmengShareSDK() {
       /* PlatformConfig.setWeixin(MyConstants.WEIXIN_ID, MyConstants.WEIXIN_KEY);
        PlatformConfig.setQQZone(MyConstants.QQ_ID, MyConstants.QQ_KEY);
        Log.LOG = false;//关闭日志

//        目前SDK默认设置为在Token有效期内登录不进行二次授权，如果有需要每次登录都弹出授权页面可以自行配置
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);*/
    }

    /**
     * 如果需要避免缓存的干扰，可以获取DaoMaster之后手动调用newSession()
     *
     * @return
     */
    public DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
            mDaoMaster = new DaoMaster(new MyOpenHelper(this, "data", null).getWritableDatabase());
        }
        return mDaoMaster;
    }

    /**
     * 获取全局唯一的DaoSession
     *
     * @return
     */
    public DaoSession getDefaultSession() {
        if (mDaoSession == null) {
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。但是每个Session都有自己的缓存
            // 同一session，同样的查询条件，返回的是同一个对象
            mDaoSession = getDaoMaster().newSession();
        }
        return mDaoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //解决dex64K 引用限制”  如果不继承MultiDexApplication 直接重新此方法添加MultiDex.install(this)。  MultiDexApplication源码当中也只是重写此方法添加MultiDex.install(this)函数
        MultiDex.install(this);
    }
}
