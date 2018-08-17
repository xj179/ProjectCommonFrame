package com.common.projectcommonframe.presenter;

import android.content.Context;

import com.common.projectcommonframe.base.BaseResponse;
import com.common.projectcommonframe.contract.LoginContract;
import com.common.projectcommonframe.entity.Login;
import com.common.projectcommonframe.model.LoginModel;
import com.common.projectcommonframe.progress.ObserverResponseListener;
import com.common.projectcommonframe.utils.ExceptionHandle;
import com.common.projectcommonframe.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

/**
 * MVP中的Presenter类
 */
public class LoginPresenter extends LoginContract.Presenter {

    private LoginModel model ;
    private Context context ;

    public LoginPresenter(Context context) {
        model = new LoginModel() ;
        this.context = context ;
    }

    @Override
    public void login(HashMap<String, Object> map, boolean isShowDialog, boolean isCancelabe) {
        model.login(context, map, isShowDialog, isCancelabe, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (getView() != null) {
                    getView().result((BaseResponse<List<Login>>) o);
                    getView().setMsg("请求成功~");
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    //// TODO: 2017/12/28 自定义处理异常
                    ToastUtil.showShortToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }

    @Override
    public void logout(HashMap<String, Object> map, boolean isShwoDialog, boolean isCancelable) {
        model.logout(context, map, isShwoDialog, isCancelable, getView().bindLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                if (getView() != null) {
                    getView().logoutResult((BaseResponse<List<Login>>) o);
                    getView().setMsg("请求成功");
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (getView() != null) {
                    ToastUtil.showLongToast(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }
}
