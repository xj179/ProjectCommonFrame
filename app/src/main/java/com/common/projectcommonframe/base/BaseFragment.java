package com.common.projectcommonframe.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 继承RxFragment防止RXJava引起的内存泄漏
 * @param <V>
 * @param <P>
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 */
public abstract class BaseFragment<V extends BaseView, P extends BasePresenter<V>> extends RxFragment {

    private V view ;

    private P presenter ;

    public Context mContext ;

    //可以用系统的ProgressDialog代替
    private SweetAlertDialog dialog ;

    private Unbinder unbinder ;

    public P getPresenter() {
        return presenter;
    }

    //由子类指定具体类型
    public abstract int getLayoutId();
    public abstract P createPresenter();
    public abstract V createView();
    public abstract void init();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View layoutView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, layoutView);
        mContext = getActivity() ;
        if (view == null) {
            view = createView();
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null && view !=null) {
            presenter.attachView(view);
        }
        init();
        return layoutView ;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    public void toActivity(Class c) {
        Intent intent = new Intent(getActivity(), c);
        startActivity(intent);
    }

    public void toActivity(Class c, Intent intent) {
        intent.setClass(getActivity(), c);
        startActivity(intent);
    }

    /**
     * 显示进度框
     * @param msg  加载Title
     */
    protected  void  showProgressDialog(String msg){
        if (dialog == null) {
            dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE) ;
            if (TextUtils.isEmpty(msg)) {
                dialog.setContentText("正在加载...") ;
            }else {
                dialog.setContentText(msg) ;
            }
        }
        dialog.show();
    }

    /**
     * 显示进度框
     */
    protected  void  showProgressDialog(){
        showProgressDialog("");
    }

    /**
     * 关闭进度框
     */
    protected  void  closeProgressDialog(){
        if (dialog != null) {
            dialog.cancel();
        }
    }
}
