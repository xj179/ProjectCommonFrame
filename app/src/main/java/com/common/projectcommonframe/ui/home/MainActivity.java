package com.common.projectcommonframe.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BaseFragment;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.components.BusEventData;
import com.common.projectcommonframe.service.ConfigIntentService;
import com.common.projectcommonframe.ui.test.TestFragment;
import com.common.projectcommonframe.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 程序主页
 */
public class MainActivity extends BaseActivity {


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    List<BaseFragment> fragments ;
    //记录打开最后一次Fragment的索引
    int lastPosition;
    //有新消息view
    View red_point;
    View red_point2;

    @Override
    public int getLayoutId() {
        EventBus.getDefault().register(this);
        return R.layout.activity_main;
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
        //启动服务，检测更新...
        startService(new Intent(this, ConfigIntentService.class));

        fragments = new ArrayList<>();
        //实际开发当中，需要替换自己的Fragment
      /*  fragments.add(new HomeFragment());
        fragments.add(new NewsFragment());
        fragments.add(new ReadFragment2());
        fragments.add(new MallFragment());
        fragments.add(new SettingsFragment());*/
        fragments.add(TestFragment.getInstance("", ""));
        fragments.add(TestFragment.getInstance("", ""));
        fragments.add(TestFragment.getInstance("", ""));
        fragments.add(TestFragment.getInstance("", ""));

        initTab();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragments.get(0))
                .commit();
    }

    /**
     * 初始华底部导航栏
     */
    private void initTab() {
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0, size = fragments.size(); i < size; i++) {
            BaseFragment fragment = fragments.get(i);
            View customView = inflater.inflate(R.layout.tab_with_icon, null, false);
            if (i == 0) {
                red_point = customView.findViewById(R.id.red_point);
            }else if (i==1){
                red_point2 = customView.findViewById(R.id.red_point);
            }
            ((ImageView) customView.findViewById(R.id.tab_icon)).setImageResource(fragment.getTabImageId());
            ((TextView) customView.findViewById(R.id.tab_text)).setText(fragment.getTabTextId());
            tabLayout.addTab(tabLayout.newTab().setCustomView(customView), i == 0);
        }

        //点击Tab切换Fragmnet
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == lastPosition) {
                    return;
                }
                if (fragments.get(position).isAdded()) {
                    getSupportFragmentManager().beginTransaction()
                            .hide(fragments.get(lastPosition))
                            .show(fragments.get(position))
                            .commit();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(fragments.get(lastPosition))
                            .add(R.id.container, fragments.get(position))
                            .commit();
                }
                lastPosition = position;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }


    /**
     *EventBus消息接收
     * @param busEventData
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BusEventData busEventData){
        if (busEventData != null && busEventData.getEventKey().equals(BusEventData.KEY_APP_UPDATE)) {  //APP更新
            SweetAlertDialog dialog = new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE).setContentText("已经为您准备了新版本，是否需要更新~").setCancelButton("取消", null).setConfirmButton("确定", null);
            dialog.setTitle("应用更新");
            dialog.show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private long pressedTime = 0;
    /**
     * 点击二下退出应用
     */
    @Override
    public void onBackPressed() {
        //点击两次退出应用
        long l = SystemClock.elapsedRealtime();
        if ((l - pressedTime) > 2000) {
            ToastUtil.show(R.string.press_to_exit);
            pressedTime = l;
        } else {
            super.onBackPressed();
        }
    }
}
