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
import com.common.projectcommonframe.contract.LoginContract;
import com.common.projectcommonframe.entity.Login;
import com.common.projectcommonframe.presenter.LoginPresenter;
import com.common.projectcommonframe.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

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
        getSupportFragmentManager().
                beginTransaction().
//                replace(R.id.frame_lay, new LoginFragment()).
                commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        ToastUtil.showShortToast(msg);
    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        //        return this.bindUntilEvent(ActivityEvent.PAUSE);//绑定到Activity的pause生命周期（在pause销毁请求）
        return this.bindToLifecycle();//绑定activity，与activity生命周期一样
    }

    @OnClick({R.id.main_msg_tv, R.id.main_check_btn,R.id.main_check2_btn,R.id.main_intent_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_msg_tv:
                break;
            case R.id.main_check_btn:
                mainMsgTv.setText("");
                HashMap<String,Object> map = new HashMap<>();
                map.put("type","yuantong");
                map.put("postid","11111111111");
//                map.put("mobile","18328008870");
//                map.put("secret","34ba01d602c88790bbe81a7aca8d3a9f");
//                KLog.e("mobile:  "+"18328008870"+"  secret:   "+"34ba01d602c88790bbe81a7aca8d3a9f");
                getPresenter().login(map,true,true);
                break;
            case R.id.main_check2_btn:
                mainMsgTv.setText("");
                HashMap<String,Object> map2 = new HashMap<>();
                map2.put("type","yuantong");
                map2.put("postid","11111111111");
                getPresenter().logout(map2,false,true);
                break;
            case R.id.main_intent_btn:
//                startActivity(new Intent(LoginActivity.this,NoPresenterActivity.class));
                break;
        }
    }
}
