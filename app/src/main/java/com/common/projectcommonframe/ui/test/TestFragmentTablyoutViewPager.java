package com.common.projectcommonframe.ui.test;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseFragment;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.view.MyFragmentPagerAdapter;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * TabLayout+ViewPager实现tab和页面联动效果Fragmnet
 */
public class TestFragmentTablyoutViewPager extends BaseFragment {

    @BindView(R.id.main_tab)
    TabLayout mTableLayout;
    @BindView(R.id.main_viewpager)
    ViewPager mViewPager;

    List<Fragment> framents ;
    MyFragmentPagerAdapter mAdapter ;
    String [] fragmentTitls ;

    public static TestFragmentTablyoutViewPager getInstance(){
        return  new TestFragmentTablyoutViewPager() ;
    }

    @Override
    public int getLayoutId() {
        return R.layout.test_activity_tablayout_viewpager;
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public BaseView createView() {
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

        FragmentActivity activity ;
        if (mContext instanceof Activity) {
            activity = (FragmentActivity) mContext;
        } else {
            activity = getActivity() ;
        }
      /*  关于在Fragment中使用多组ViewPager+TabLayout出现的部分Fragment不显示问题
        getFragmentManager(); 作用在Activity。
        getChildFragmentManager(); 作用在Fragment。*/
//        mAdapter = new MyFragmentPagerAdapter(activity.getSupportFragmentManager(), framents, fragmentTitls);  //使用getSupportFragmentManager 会出上面这个问题
        mAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), framents, fragmentTitls);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTableLayout.setupWithViewPager(mViewPager);
//        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE); //滚动 item 多的话使用滚动
        mTableLayout.setTabMode(TabLayout.MODE_FIXED);  //不滚动  item 不多的话建议使用这个


        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        KLog.i("xjlei", "Fragment Size:" + mViewPager.getAdapter().getCount());
        KLog.i("xjlei", "table Count:" +     mTableLayout.getTabCount());
    }

    @Override
    public int getTabImageId() {
        return R.drawable.tab_chat;
    }

    @Override
    public int getTabTextId() {
        return R.string.tab_home_text;
    }
}
