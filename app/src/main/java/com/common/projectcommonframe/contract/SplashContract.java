package com.common.projectcommonframe.contract;

import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseResponse;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.entity.Login;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

public interface SplashContract {

    /**
     * View需要的一些接口
     */
    interface  View extends BaseView{
        void showGuide();

        void showAD();

        void showLoading();

        /**
         * 欢迎页展示结束或者广告展示结束、点击跳过、引导页展示结束
         * @return
         */
        Observable<Object> onFinish();


        <T> ObservableTransformer<T, T> bindLifecycle();
    }

    abstract  class Presenter extends BasePresenter<View>{

        public abstract  boolean hasNew();

        public  abstract  boolean hasAD() ;

        public  abstract  boolean hasLogin() ;

        /**
         * 欢迎页展示结束或者广告展示结束、点击跳过、引导页展示结束
         * @return
         */
      /*  protected abstract Observable<Object> onFinish();*/
    }
}
