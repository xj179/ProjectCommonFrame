package com.common.projectcommonframe.ui.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseFragment;
import com.common.projectcommonframe.base.BaseResponse;
import com.common.projectcommonframe.contract.LoginContract;
import com.common.projectcommonframe.entity.Login;
import com.common.projectcommonframe.presenter.LoginPresenter;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableTransformer;

public class TestFragment extends BaseFragment<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {

    @BindView(R.id.fragment_check_btn)
    Button fragmentCheckBtn;
    @BindView(R.id.fragment_msg_tv)
    TextView fragmentMsgTv;

    @Override
    public int getLayoutId() {
        return R.layout.test_fragment_login;
    }

    @Override
    public LoginContract.Presenter createPresenter() {
        return new LoginPresenter(mContext);
    }

    @Override
    public LoginContract.View createView() {
        return this;
    }


    @Override
    public void init() {

    }

    @Override
    public void result(BaseResponse<List<Login>> response) {
        fragmentMsgTv.setText(response.getData().toString());
    }

    @Override
    public void logoutResult(BaseResponse<List<Login>> response) {

    }

    @Override
    public void setMsg(String msg) {
        fragmentMsgTv.setText(msg);

    }

    @Override
    public <T> ObservableTransformer<T, T> bindLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.fragment_check_btn)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_check_btn:
                fragmentMsgTv.setText("");
                HashMap<String,Object> map = new HashMap<>();
                map.put("type","yuantong");
                map.put("postid","11111111111");
                getPresenter().login(map,false,false);
                break;
        }
    }
}
