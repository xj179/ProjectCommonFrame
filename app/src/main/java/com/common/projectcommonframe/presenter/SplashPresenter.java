package com.common.projectcommonframe.presenter;

import android.content.Context;

import com.common.projectcommonframe.contract.SplashContract;
import com.common.projectcommonframe.model.SplashModel;

import io.reactivex.Observable;

/**
 * Splash开屏页Presenter
 */
public class SplashPresenter extends SplashContract.Presenter {

    SplashModel splashModel  ;
    Context mContext ;

    public SplashPresenter(Context mContext) {
        this.splashModel = new SplashModel();
        this.mContext = mContext;
    }


    @Override
    public boolean hasNew() {
        return splashModel.hasNew();
    }

    @Override
    public boolean hasAD() {
        return splashModel.hasAD();
    }

    @Override
    public boolean hasLogin() {
        return splashModel.hasLogin();
    }
}
