package com.common.projectcommonframe.base;

import android.content.Context;

import com.common.projectcommonframe.progress.ObserverResponseListener;
import com.common.projectcommonframe.progress.ProgressObserver;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * M层父类(Mode层主要做一些网络请求，数据查询之类)
 */
public class BaseMode<T> {

    /**
     * 订阅者
     * @param context 上下文
     * @param observable  retrofit请求接口Observable
     * @param responseListener   响应回调
     * @param observableTransformer rxlifecycle（管理RxJava引起的内存泄漏）中实现ObservableTransformer类
     * @param isShowDialog  是否显示加载框
     * @param isCancelable  是否能取消
     */
    public void subscribe(Context context, final Observable observable, ObserverResponseListener<T> responseListener,
                          ObservableTransformer<T, T> observableTransformer, boolean isShowDialog, boolean isCancelable) {
        ProgressObserver observer = new ProgressObserver(context, responseListener, isShowDialog, isCancelable);
        observable.compose(observableTransformer)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 重载订阅方法
     * @param context
     * @param observable
     * @param responseListener
     * @param observableTransformer
     */
    public void subscribe(Context context, final Observable observable, ObserverResponseListener<T> responseListener,
                          ObservableTransformer<T, T> observableTransformer) {
        subscribe(context, observable, responseListener, observableTransformer, true, true);
    }


}
