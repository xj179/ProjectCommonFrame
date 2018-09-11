package com.common.projectcommonframe.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.projectcommonframe.BuildConfig;
import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.network.BaseResponse;
import com.common.projectcommonframe.components.BusEventData;
import com.common.projectcommonframe.contract.LoginContract;
import com.common.projectcommonframe.entity.Login;
import com.common.projectcommonframe.presenter.LoginPresenter;
import com.common.projectcommonframe.ui.browser.BrowserActivity;
import com.common.projectcommonframe.ui.test.TestActivityNoPresenter;
import com.common.projectcommonframe.ui.test.TestActivityTablayoutViewPager;
import com.common.projectcommonframe.ui.test.TestActivityXRecyleView;
import com.common.projectcommonframe.ui.test.TestFragment;
import com.common.projectcommonframe.ui.test.TestActivityPickerView;
import com.common.projectcommonframe.ui.test.banner.TestBannerViewActivity;
import com.common.projectcommonframe.utils.CompoundDrawableUtil;
import com.common.projectcommonframe.utils.ScreenUtil;
import com.common.projectcommonframe.utils.SelectorUtil;
import com.common.projectcommonframe.utils.ToastUtil;
import com.common.projectcommonframe.view.Title;
import com.socks.library.KLog;
import com.yongchun.library.view.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
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
    @BindView(R.id.main_btn6)
    Button main_btn6;
    @BindView(R.id.main_btn7)
    Button main_btn7;
    @BindView(R.id.main_btn8)
    Button main_btn8;

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

//        EasyPermissions.requestPermissions(this, "请求权限", 0, PermissionsUtil.PERMISSONS_GROUP);
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

    @OnClick({R.id.main_msg_tv, R.id.main_check_btn, R.id.main_check2_btn, R.id.main_intent_btn, R.id.main_btn2,
            R.id.main_btn3, R.id.main_btn4, R.id.main_btn5, R.id.main_btn6, R.id.main_btn7, R.id.main_btn8})
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
            case R.id.main_intent_btn2:  //通用选择控件
                toActivity(TestActivityPickerView.class);
                break;
            case R.id.main_btn2:
                testSweetAlertDialog();
                break;
            case R.id.main_btn3:  //通用webview Activity
                toActivity(BrowserActivity.class);
                break;
            case R.id.main_btn4:   //加载广告示例Activity
                toActivity(TestBannerViewActivity.class);
                break;
            case R.id.main_btn5:   //加载XRecyleView  Activity
                toActivity(TestActivityXRecyleView.class);
                break;
            case R.id.main_btn6:   //测试7.0以上系统文件共享(安装APK等..)
                testApkInstall();
                break;
            case R.id.main_btn7:   //Photo picker library for Android. Support single choice、multi-choice、cropping image and preview image.

                //是否有权限
                if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    ImageSelectorActivity.start(this, 3, ImageSelectorActivity.MODE_MULTIPLE, true,true,true);
                } else{
                    EasyPermissions.requestPermissions(this, "请求权限", 0, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA);
                }
                 // Activity中重写此方法 onActivityResult
                break;
            case R.id.main_btn8:   //TabLayout+ViewPager实现tab和页面联动效果
                toActivity(TestActivityTablayoutViewPager.class);
                break;
        }
    }

    /**
     * 参加为requstCode（所有的权限同意了才会进入此方法）
     */
    @AfterPermissionGranted(0)
    public void onPermissionsSuccess() {
//        ToastUtil.show( "用户授权成功");
        ImageSelectorActivity.start(this, 3, ImageSelectorActivity.MODE_MULTIPLE, true,true,true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> permissionsList) {
//        super.onPermissionsDenied(requestCode, permissionsList);
        KLog.i("onPermissionsDenied:" + requestCode +  " permissionsList:" + permissionsList);
    }

    /**
     * 测试SweetDialog
     */
    private void testSweetAlertDialog() {
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


    /**
     * 7.0(24)系统文件之间的共享更严格(以前url前缀fil://的这种形式在7.0以上拒绝，只能改成前缀为:Content://)
     */
    private void testApkInstall(){
        String pathName = Environment.getExternalStorageDirectory() + File.separator + "test.apk" ;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){  //版本大于或等于7.0做特殊处理
                File file= new File(pathName);
                Uri apkUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID+".apkprovider", file);//在AndroidManifest中的android:authorities值
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                startActivity(intent);
            }else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(pathName)), "application/vnd.android.package-archive");
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 接受图片ImageSelecto 回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ImageSelectorActivity.REQUEST_IMAGE){
            ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
            KLog.i("onActivityResult images:" + images);
            // do something
        }
    }
}
