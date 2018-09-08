package com.common.projectcommonframe.ui.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.network.BaseResponse;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.entity.Login;
import com.common.projectcommonframe.model.LoginModel;
import com.common.projectcommonframe.progress.ObserverResponseListener;
import com.common.projectcommonframe.utils.ExceptionHandle;
import com.common.projectcommonframe.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivityNoPresenter extends BaseActivity {
    @BindView(R.id.check_btn)
    Button checkBtn;
    @BindView(R.id.check_msg_tv)
    TextView checkMsgTv;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;

    private LoginModel model;

    @Override
    public int getLayoutId() {
        return R.layout.test_activity_no_presenter;
    }

    @Override
    public BaseView createView() {
        return null;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void init() {
         model = new LoginModel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @OnClick({R.id.check_msg_tv, R.id.check_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_msg_tv:
                break;
            case R.id.check_btn:
                checkMsgTv.setText("");
                HashMap<String, String> map = new HashMap<>();
                map.put("type", "yuantong");
                map.put("postid", "11111111111");
                getData(map, true, false);
                break;
        }
    }

    private void getData(HashMap<String, String> map, boolean isShowDialog, boolean isCancelable) {
        model.login(this, map, isShowDialog, isCancelable, bindToLifecycle(), new ObserverResponseListener() {
            @Override
            public void onNext(Object o) {
                //这一步是必须的，判断view是否已经被销毁
                if (this != null) {
                    //设置数据。。。
                    BaseResponse<List<Login>> data = (BaseResponse<List<Login>>) o;
                    setData(data);
                }
            }

            @Override
            public void onError(ExceptionHandle.ResponeThrowable e) {
                if (this != null) {
                    ToastUtil.show(ExceptionHandle.handleException(e).message);
                }
            }
        });
    }
    private void setData(BaseResponse<List<Login>> data) {
        checkMsgTv.setText(data.getData().toString());
    }

}
