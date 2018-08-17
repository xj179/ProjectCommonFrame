package com.common.projectcommonframe.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * 所以Activity的基类Activity
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 * @param <V>
 * @param <P>
 */
public abstract  class BaseActivity<V extends BaseView, P extends  BasePresenter<V>> extends RxAppCompatActivity {

    //引用V层和P层
    private V view ;
    private  P presenter ;

    //layout布局的ID
    public abstract int getLayoutId() ;

    //由子类指定具体类型
    public abstract  V createView() ;

    //由子类指定具体类型
    public abstract  P createPresenter() ;

    //初始化view之类的
    public abstract void init();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        Activity 类中绑定 ：ButterKnife.bind(this);必须在setContentView();之后绑定；且父类bind绑定后，子类不需要再bind。
        ButterKnife.bind(this) ;
        if (view == null) {
            view = createView();
        }
        if (presenter == null) {
            presenter = createPresenter();
        }
        if (presenter != null && view !=null) {
            presenter.attachView(view);
        }
        init() ;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    public P getPresenter() {
        return presenter;
    }
}
