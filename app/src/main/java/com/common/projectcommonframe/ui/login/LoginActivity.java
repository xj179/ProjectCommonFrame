package com.common.projectcommonframe.ui.login;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BaseResponse;
import com.common.projectcommonframe.components.BusEventData;
import com.common.projectcommonframe.contract.LoginContract;
import com.common.projectcommonframe.entity.Login;
import com.common.projectcommonframe.presenter.LoginPresenter;
import com.common.projectcommonframe.ui.browser.BrowserActivity;
import com.common.projectcommonframe.ui.test.TestActivityNoPresenter;
import com.common.projectcommonframe.ui.test.TestFragment;
import com.common.projectcommonframe.ui.test.TestPickerViewActivity;
import com.common.projectcommonframe.ui.test.banner.TestBannerViewActivity;
import com.common.projectcommonframe.ui.test.TestXRecyleViewActivity;
import com.common.projectcommonframe.utils.CompoundDrawableUtil;
import com.common.projectcommonframe.utils.PermissionsUtil;
import com.common.projectcommonframe.utils.ScreenUtil;
import com.common.projectcommonframe.utils.SelectorUtil;
import com.common.projectcommonframe.utils.ToastUtil;
import com.common.projectcommonframe.view.Title;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.ObservableTransformer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * UI层MVP当中的V（View）,以模块划分
 */
public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.main_check_btn)
    Button mainCheckBtn;
    @BindView(R.id.main_check2_btn)
    Button mainCheck2Btn;
    @BindView(R.id.main_intent_btn)
    Button mainIntentBtn;
    @BindView(R.id.main_msg_tv)
    TextView mainMsgTv;
    @BindView(R.id.frame_lay)
    FrameLayout frameLay;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    @BindView(R.id.main_intent_btn2)
    Button mainIntentBtn2;
    @BindView(R.id.main_btn2)
    Button main_btn2;
    @BindView(R.id.main_btn3)
    Button main_btn3;
    @BindView(R.id.main_btn4)
    Button main_btn4;
    @BindView(R.id.main_btn5)
    Button main_btn5;

    @BindView(R.id.title)
    Title title ;

    @BindView(R.id.container_sv)
    NestedScrollView scrollView ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init() {
        title.setTitle("登入界面");
        title.setLeftButton(R.mipmap.icon_back, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setRightButton(R.mipmap.icon_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("分享~");
            }
        });
        title.setRightButton2(R.mipmap.icon_close, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("setRightButton2 图片~");
            }
        });

        //代码动态设置文本颜色Selector
//        SelectorUtil.setColorSelectorByView(mainCheckBtn, R.color.red, R.color.orange);  //Error无效不能通过R.color方式设置
        SelectorUtil.setTextColorSelector(mainCheckBtn, Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        KLog.i("parseColor:" + Color.parseColor("#ff0000") + "\n" + "getColorColor:" + R.color.red);

        //动态设置背景颜色Selector
        ColorStateList colorStateList = SelectorUtil.createColorStateList(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"));
        mainCheck2Btn.setBackgroundTintList(colorStateList);

        //动态设置View 上 下 左 右 图片
        CompoundDrawableUtil.setCompoundDrawableOfLeft(mainIntentBtn, getDrawable(R.mipmap.icon_share));
        CompoundDrawableUtil.setCompoundDrawablePadding(mainIntentBtn, 25);

        CompoundDrawableUtil.setCompoundDrawableOfTop(mainIntentBtn2, SelectorUtil.createDrawableSelector(this, getDrawable(R.mipmap.icon_share), getDrawable(R.mipmap.icon_close)));
//        CompoundDrawableUtil.setPadding(mainIntentBtn2, 5);

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frame_lay, TestFragment.getInstance("参数1", "参数二")).
                commitAllowingStateLoss();
        KLog.i("test");
        EventBus.getDefault().register(this);

        EasyPermissions.requestPermissions(this, "请求权限", 0, PermissionsUtil.PERMISSONS_GROUP);
    }

    /**
     * 参加为requstCode（所有的权限同意了才会进入此方法）
     */
    @AfterPermissionGranted(0)
    public void onPermissionsSuccess() {
        ToastUtil.show( "用户授权成功");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void result(BaseResponse<List<Login>> response) {
        mainMsgTv.setText(response.getData().toString());
    }

    @Override
    public void logoutResult(BaseResponse<List<Login>> response) {
        mainMsgTv.setText(response.getData().toString());
    }

    @Override
    public void setMsg(String msg) {
        ToastUtil.show(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        //        return this.bindUntilEvent(ActivityEvent.PAUSE);//绑定到Activity的pause生命周期（在pause销毁请求）
        return this.bindToLifecycle();//绑定activity，与activity生命周期一样
    }

    @OnClick({R.id.main_msg_tv, R.id.main_check_btn, R.id.main_check2_btn, R.id.main_intent_btn, R.id.main_btn2, R.id.main_btn3, R.id.main_btn4, R.id.main_btn5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_msg_tv:
                break;
            case R.id.main_check_btn:
                mainMsgTv.setText("");
                HashMap<String, Object> map = new HashMap<>();
                map.put("type", "yuantong");
                map.put("postid", "11111111111");
//                map.put("mobile","18328008870");
//                map.put("secret","34ba01d602c88790bbe81a7aca8d3a9f");
//                KLog.e("mobile:  "+"18328008870"+"  secret:   "+"34ba01d602c88790bbe81a7aca8d3a9f");
                getPresenter().login(map, true, true);
                break;
            case R.id.main_check2_btn:
                mainMsgTv.setText("");
                HashMap<String, Object> map2 = new HashMap<>();
                map2.put("type", "yuantong");
                map2.put("postid", "11111111111");
                getPresenter().logout(map2, false, true);
                break;
            case R.id.main_intent_btn:
                startActivity(new Intent(LoginActivity.this, TestActivityNoPresenter.class));
//                testShoot() ;
                break;
            case R.id.main_btn3:  //通用webview Activity
                toActivity(BrowserActivity.class);
                break;
            case R.id.main_btn4:   //加载广告示例Activity
                toActivity(TestBannerViewActivity.class);
                break;
            case R.id.main_btn5:   //加载XRecyleView  Activity
                toActivity(TestXRecyleViewActivity.class);
                break;
            case R.id.main_btn2:
                new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setCancelText("取消")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
  /*              new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to recover this file!")
                        .setConfirmText("Yes,delete it!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog
                                        .setTitleText("Deleted!")
                                        .setContentText("Your imaginary file has been deleted!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();*/

                /*弹出编辑框*/
               /* EditText et = new EditText(this) ;
//                et.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(this, 60)));
                new SweetAlertDialog(this).setCustomView(et).setTitleText("编辑昵称").show();*/

              /*  EditText et = new EditText(this) ;
                BottomSheetDialog dialog = new BottomSheetDialog(this);
                dialog.setContentView(R.layout.activity_login);
                dialog.show();*/
                break;
        }
    }

    @OnClick(R.id.main_intent_btn2)
    public void onViewClicked() {
        toActivity(TestPickerViewActivity.class);
    }


    /**
     * 测试截屏
     */
    public void testShoot(){
        //测试截屏
        showProgressDialog();
        // 最好判断是否有存储权限，没有的话去请求存储权限
//        boolean shoot = ScreenUtil.shoot(this, new File(Environment.getExternalStorageDirectory() + File.separator + "testShootActivity.png"));  //截取Activity
        boolean shoot = ScreenUtil.shoot(scrollView, new File(Environment.getExternalStorageDirectory() + File.separator + "testShootView.png"));  //截取View里面的内容
        if (shoot) {
            ToastUtil.show("截屏成功");
            closeProgressDialog();
        } else {
            ToastUtil.show("截屏失败");
            closeProgressDialog();
        }
    }

    /**
     * EventBsu接受3.0后通过注解方式来接收
     * @param busEventData
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void testEventBusRevive(BusEventData busEventData){
        if (busEventData != null && busEventData.getEventKey().equals(BusEventData.KEY_REFRESH)) {
             ToastUtil.show(busEventData.getContent());
        }
    }
}
