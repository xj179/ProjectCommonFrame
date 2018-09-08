package com.common.projectcommonframe.model;

import android.content.Context;

import com.common.projectcommonframe.network.Api;
import com.common.projectcommonframe.base.BaseMode;
import com.common.projectcommonframe.progress.ObserverResponseListener;

import java.util.Map;

import io.reactivex.ObservableTransformer;

/**
 * MVP模式中的Model（一般做一些数据请求，网络查询等）
 * @param <T>
 */
public class LoginModel<T> extends BaseMode {

    /**
     * 登录
     * @param context
     * @param map
     * @param isShowDialog
     * @param isCancelable
     * @param transformer
     * @param observerResponseListener
     */
    public void login(Context context, Map<String, Object> map, boolean isShowDialog, boolean isCancelable,
                      ObservableTransformer<T, T> transformer, ObserverResponseListener observerResponseListener){

        //当不需要指定是否由dialog时，可以调用这个方法
        // subscribe(context, Api.getApiService().login(map), observerListener);
        subscribe(context, Api.getApiService().login(map), observerResponseListener, transformer, isShowDialog, isCancelable);
    };

    // TODO: 2018/8/17 0017 操作数据库等操作 

    /**
     * 退出
     * @param context
     * @param map
     * @param isShowDialog
     * @param isCancelable
     * @param transformer
     * @param observerResponseListener
     */
    public void logout(Context context, Map<String, Object> map, boolean isShowDialog, boolean isCancelable,
                       ObservableTransformer<T, T> transformer, ObserverResponseListener observerResponseListener){
        subscribe(context, Api.getApiService().logout(map), observerResponseListener, transformer, isShowDialog, isCancelable);
    }
}
