package com.common.projectcommonframe.ui.splash;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.contract.SplashContract;
import com.common.projectcommonframe.presenter.SplashPresenter;
import com.common.projectcommonframe.ui.login.LoginActivity;
import com.common.projectcommonframe.utils.PermissionsUtil;
import com.common.projectcommonframe.view.MyFragmentPagerAdapter;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 程序启动页(包含引导页，启动页，广告页)
 */
public class SplashActivity extends BaseActivity<SplashContract.View, SplashContract.Presenter> implements SplashContract.View, View.OnClickListener{


    @BindView(R.id.bg_iv)
    ImageView bgIv;
    @BindView(R.id.ad_container)
    FrameLayout adContainer;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.skip_tv)
    TextView skipTv;
    @BindView(R.id.dot1)
    View dot1;
    @BindView(R.id.dot2)
    View dot2;
    @BindView(R.id.dot3)
    View dot3;
    @BindView(R.id.dot_ll)
    LinearLayout dotLl;

    //RxJava2 Disposable管理的对象
    CompositeDisposable compositeDisposable ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashContract.View createView() {
        return this;
    }

    @Override
    public SplashContract.Presenter createPresenter() {
        return new SplashPresenter(this);
    }

    @Override
    public void init() {
        if (getIntent().getIntExtra("need_show_ad", 0) == 1){
            if (getPresenter().hasAD()) {
               Disposable disposable = onFinish().subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        finish();
                    }
                });
               compositeDisposable.add(disposable) ;
                showAD();
                return;
            }
            finish();
        } else {
            //授权之后再加载
            EasyPermissions.requestPermissions(this, "请求权限", 0, PermissionsUtil.PERMISSONS_GROUP);
        }
    }

    /**
     * 参加为requstCode（所有的权限同意了才会进入此方法）
     */
    @AfterPermissionGranted(0)
    public void onPermissionsSuccess() {
         loading() ;
    }

    /**
     * 权限拒绝，不能使用，直接关闭
     * @param requestCode
     * @param permissionsList
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> permissionsList) {
        super.onPermissionsDenied(requestCode, permissionsList);
        //还可以弹出个提示拒绝框，引导用户去打开权限《这里做简单处理，直接关闭应用》
        finish();
    }

    @Override
    public void showGuide() {
        dotLl.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        //setOffscreenPageLimit值指的是，当前view的左右两边的预加载的页面的个数。也就是说，如果这个值mOffscreenPageLimit　＝　３，那么任何一个页面的左边可以预加载３个页面，右边也可以加载３页面。
        viewPager.setOffscreenPageLimit(2);
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(GuideFragment.newInstance(R.mipmap.bg_guide1, false));
        fragments.add(GuideFragment.newInstance(R.mipmap.bg_guide2, false));
        fragments.add(GuideFragment.newInstance(R.mipmap.bg_guide3, true));
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                skip_tv.setVisibility(position == 2 ? View.INVISIBLE : View.VISIBLE);
                dot1.setBackgroundResource(position==0? R.drawable.dot_main_selected:R.drawable.dot_main_default);
                dot2.setBackgroundResource(position==1? R.drawable.dot_main_selected:R.drawable.dot_main_default);
                dot3.setBackgroundResource(position==2? R.drawable.dot_main_selected:R.drawable.dot_main_default);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void showAD() {
        //可以配置第三方广告

    }

    @Override
    public void showLoading() {
        Disposable timerTask = Observable.timer(1500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                skipTv.callOnClick();
            }
        });
        compositeDisposable.add(timerTask) ;
    }

    @Override
    public Observable<Object> onFinish() {
        return RxView.clicks(skipTv).take(1);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return this.bindToLifecycle();
    }

    /**
     * 加载逻辑
     * 1.是否显示引导页
     * 2.是否加载广告
     * 3.是否显示默认引导页
     */
    private void loading() {
        /**
         * RXJava 2.0  点击事件订阅Subscribe
         */
        Disposable disposable = onFinish().map(new Function<Object, Boolean>() {
            @Override
            public Boolean apply(@io.reactivex.annotations.NonNull Object o) throws Exception {
                return getPresenter().hasLogin();
            }
        }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    toActivity(LoginActivity.class);    //如果登录了跳转到主页
                } else {
                    toActivity(LoginActivity.class);    //如果没有登录去登录页
                }
                finish();
            }
        });

        //是否是第一次启动(第一次启动显示引导页)
        if (getPresenter().hasNew()) {
            showGuide();
        } else if(getPresenter().hasAD()){  // //如果有广告显示广告
            showAD();
        } else{
            showLoading();  // 显示默认加载页面
        }

        compositeDisposable.add(disposable) ;
    }

    boolean canJump;
    @Override
    public void onResume() {
        super.onResume();
        if (canJump) {
            skipTv.callOnClick();
        }else {
            canJump = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null) {  //及时销毁，防止内在泄漏
            compositeDisposable.dispose();
        }
    }

    @Override
    public void onClick(View view) {
        skipTv.callOnClick() ;
    }
}
