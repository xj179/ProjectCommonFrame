package com.common.projectcommonframe.ui.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BaseAdapter;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.components.BusEventData;
import com.common.projectcommonframe.components.MyApplication;
import com.common.projectcommonframe.utils.ScreenUtil;
import com.common.projectcommonframe.utils.ThreadTransformer;
import com.common.projectcommonframe.utils.glide.GlideUtil;
import com.common.projectcommonframe.view.Title;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * a RecyclerView that implements pullrefresh , loadingmore and header featrues.you can use it like a standard RecyclerView. you don't need to implement a special adapter
 * 默认实现了RecyleView下拉刷新和上拉加载更多
 */
public class TestActivityXRecyleView extends BaseActivity {


    @BindView(R.id.title)
    Title title;
    @BindView(R.id.test_xrecyleview)
    XRecyclerView mRecyclerView;

    List<Integer> listImagsID ;
    XRecyleviewAdapter mAdapter;
    private String[] images = {
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public int getLayoutId() {
        return R.layout.test_xrecyleview_activity;
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
        title.setTitle("XRecyclerView测试界面");

        GridLayoutManager glm = new GridLayoutManager(this, 2) ;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(glm);
        mAdapter = new XRecyleviewAdapter(this);

        //不用默认的加载样式,设置自己喜欢的加载样式
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.TriangleSkewSpin);
        mRecyclerView.setAdapter(mAdapter);

        //禁止上拉刷新和下拉加载更多
//        mRecyclerView.setPullRefreshEnabled(false);
//        mRecyclerView.setPullRefreshEnabled(true);

//        Observable.empty().delay()
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                compositeDisposable.add(Observable.just("test").delay(2, TimeUnit.SECONDS).compose(new ThreadTransformer<String>()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mAdapter.refreshData(Arrays.asList(images));
                        mRecyclerView.refreshComplete();
                    }
                }));
            }

            @Override
            public void onLoadMore() {
                //没有分页数据打开这行
//                mRecyclerView.setNoMore(true);
                compositeDisposable.add(Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        e.onNext("test2");
                    }
                }).delay(2, TimeUnit.SECONDS).compose(new ThreadTransformer()).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        String s = Arrays.asList(images).get(images.length - 1);
                        List list = new ArrayList<String>() ;
                        list.add(s) ;
                        mAdapter.loadMoreData(list);
                        mRecyclerView.loadMoreComplete();
                        KLog.i("接受到的值:" + o);
                    }
                }));
            }
        });

        mRecyclerView.refresh();  //手动代码调用刷新
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // any time,when you finish your activity or fragment,call this below
        if(mRecyclerView != null){
            mRecyclerView.destroy(); // this will totally release XR's memory
            mRecyclerView = null;
        }

        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BusEventData bed = new BusEventData(BusEventData.KEY_REFRESH);
        bed.setContent(TestActivityXRecyleView.class.getSimpleName() + "传过去的值....");
        EventBus.getDefault().post(bed);
    }

    class XRecyleviewAdapter extends BaseAdapter<String, XRcyleviewHolder> {

        public XRecyleviewAdapter(Context context) {
            super(context);
        }

        @Override
        public XRcyleviewHolder onCreateVH(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_banner_image, parent, false);
            XRcyleviewHolder viewHolder = new XRcyleviewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindVH(XRcyleviewHolder xRcyleviewHolder, int position) {
            String item = getItem(position);
            GlideUtil.loadImage(TestActivityXRecyleView.this, item, xRcyleviewHolder.advertIv);
        }

    }

    class XRcyleviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.advert_iv)
        ImageView advertIv;

        public XRcyleviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();  //这里其实还没有渲染，所以没法得到具体的宽高,得到的宽高为负数，
            layoutParams.width = ScreenUtil.getScreenWidth(MyApplication.getInstance());
            layoutParams.height = ScreenUtil.dp2px(MyApplication.getInstance(), 300);
//            advertIv.setLayoutParams(layoutParams);  //不需要重新设置也是可以的哦~
        }
    }
}
