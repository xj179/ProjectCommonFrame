package com.common.projectcommonframe.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.socks.library.KLog;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 所以Activity的基类Activity
 * 父类->基类->动态指定类型->泛型设计（通过泛型指定动态类型->由子类指定，父类只需要规定范围即可）
 * @param <V>
 * @param <P>
 */
public abstract  class BaseActivity<V extends BaseView, P extends  BasePresenter<V>> extends RxAppCompatActivity implements EasyPermissions.PermissionCallbacks  {

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions 交给EasyPermissions去处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> permissionsList) {
        // Some permissions have been granted
        Gson son ;
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> permissionsList) {
        // Some permissions have been denied  权限拒绝
        KLog.d("onPermissionsDenied:" + requestCode + ":" + permissionsList.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, permissionsList)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限请求")
                    .setRationale("需要访问您设备上的应用权限。打开app设置界面，修改app权限。")
                    .setPositiveButton("确定")
                    .setNegativeButton("取消")
                    .build().show();
        }
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

    protected final int DEFAULT_REQUEST_CODE = 10000;

    public void toActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    public void toActivity(Class c, Intent intent) {
        intent.setClass(this, c);
        startActivity(intent);
    }

    public void toActivityForResult(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, DEFAULT_REQUEST_CODE);
    }

    public void toActivityForResult(Class c, Intent intent) {
        intent.setClass(this, c);
        startActivityForResult(intent, DEFAULT_REQUEST_CODE);
    }

    public void toActivityForResult(Class c, int requestCode) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, requestCode);
    }

    public void toActivityForResult(Class c, Intent intent, int requestCode) {
        intent.setClass(this, c);
        startActivityForResult(intent, requestCode);
    }
}
