package com.common.projectcommonframe.ui.test;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.view.MyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * TabLayout+ViewPager实现tab和页面联动效果Activity
 */
public class TestActivityTablayoutViewPager extends BaseActivity {

    @BindView(R.id.main_tab)
    TabLayout mTableLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    List<Fragment> framents ;
    MyFragmentPagerAdapter mAdapter ;
    String [] fragmentTitls ;

    @Override
    public int getLayoutId() {
        return R.layout.test_activity_tablayout_viewpager;
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
        framents = new ArrayList<>() ;
        framents.add(TestFragment.getInstance("", "")) ;
        framents.add(TestFragment.getInstance("", "")) ;
        framents.add(TestFragment.getInstance("", "")) ;
        framents.add(TestFragment.getInstance("", "")) ;

        fragmentTitls = new String[framents.size()] ;
        for (int i = 0; i < framents.size(); i++) {
            fragmentTitls[i] = "标题" + (i +1) ;
        }

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), framents, fragmentTitls);
        mViewPager.setAdapter(mAdapter);
        mTableLayout.setupWithViewPager(mViewPager);
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);
    }

}
