package com.common.projectcommonframe.contract;

import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.network.BaseResponse;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.entity.Login;

import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableTransformer;

public interface LoginContract {

    interface  View extends BaseView{
        void result(BaseResponse<List<Login>> response) ;

        void logoutResult(BaseResponse<List<Login>> response) ;

        void setMsg(String msg) ;

        <T> ObservableTransformer<T, T> bindLifecycle();

    }

    abstract  class Presenter extends BasePresenter<View>{
        //请求1
        public abstract  void login(HashMap<String, Object> map, boolean isShowDialog, boolean isCancelabe);

        //请求土
        public  abstract  void logout(HashMap<String, Object> map, boolean isShwoDialog, boolean isCancelable) ;
    }
}
