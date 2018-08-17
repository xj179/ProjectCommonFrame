package com.common.projectcommonframe.base;

/**
 * 基类Presenter
 * @param <V>
 */
public abstract class BasePresenter<V extends BaseView> {

    private V  mView ;

    public V getView() {
        return mView;
    }

    public void attachView(V view){
        this.mView = view ;
    }

    public void detachView(){
        mView = null ;
    }
}
