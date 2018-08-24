package com.common.projectcommonframe.ui.login;

import android.content.Intent;
import android.os.Bundle;
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
import com.common.projectcommonframe.ui.test.TestActivityNoPresenter;
import com.common.projectcommonframe.ui.test.TestFragment;
import com.common.projectcommonframe.ui.test.TestPickerViewActivity;
import com.common.projectcommonframe.utils.PermissionsUtil;
import com.common.projectcommonframe.utils.ToastUtil;
import com.common.projectcommonframe.view.Title;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.ObservableTransformer;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

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

    @BindView(R.id.title)
    Title title ;

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
      /*  title.setRightButton2(R.mipmap.icon_share, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.show("setRightButton2 图片~");
            }
        });*/

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

    @OnClick({R.id.main_msg_tv, R.id.main_check_btn, R.id.main_check2_btn, R.id.main_intent_btn, R.id.main_btn2})
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
                break;
            case R.id.main_btn2:
                /*new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
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
                        .show();*/
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
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
                        .show();
                break;
        }
    }

    @OnClick(R.id.main_intent_btn2)
    public void onViewClicked() {
        toActivity(TestPickerViewActivity.class);
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
