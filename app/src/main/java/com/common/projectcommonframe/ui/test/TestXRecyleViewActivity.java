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
import com.common.projectcommonframe.utils.ThreadTransformer;
import com.common.projectcommonframe.view.Title;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * a RecyclerView that implements pullrefresh , loadingmore and header featrues.you can use it like a standard RecyclerView. you don't need to implement a special adapter
 * 默认实现了RecyleView下拉刷新和上拉加载更多
 */
public class TestXRecyleViewActivity extends BaseActivity {


    @BindView(R.id.title)
    Title title;
    @BindView(R.id.test_xrecyleview)
    XRecyclerView mRecyclerView;

    List<Integer> listImagsID ;
    XRecyleviewAdapter mAdapter;

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

        listImagsID = new ArrayList<Integer>();
        listImagsID.add(R.mipmap.test_0);
        listImagsID.add(R.mipmap.test_1);
        listImagsID.add(R.mipmap.test_2);
        listImagsID.add(R.mipmap.test_3);
        listImagsID.add(R.mipmap.test_4);
        listImagsID.add(R.mipmap.test_5);
//        listImagsID.add(R.mipmap.test_6);
        mAdapter.refreshData(listImagsID);
        mRecyclerView.setAdapter(mAdapter);

//        Observable.empty().delay()
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                compositeDisposable.add(Observable.just("test").delay(2, TimeUnit.SECONDS).compose(new ThreadTransformer<String>()).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mAdapter.refreshData(listImagsID);
                        mRecyclerView.refreshComplete();
                    }
                }));
            }

            @Override
            public void onLoadMore() {
                //没有分页数据打开这行
//                mRecyclerView.setNoMore(true);
                final List<Integer> list = new ArrayList<Integer>() ;
                list.add(R.mipmap.test_6) ;

                compositeDisposable.add(Observable.create(new ObservableOnSubscribe<Object>() {
                    @Override
                    public void subscribe(ObservableEmitter<Object> e) throws Exception {
                        e.onNext("test2");
                    }
                }).delay(2, TimeUnit.SECONDS).compose(new ThreadTransformer()).subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        mAdapter.loadMoreData(list);
                        mRecyclerView.loadMoreComplete();
                        KLog.i("接受到的值:" + o);
                    }
                }));
            }
        });
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
        bed.setContent(TestXRecyleViewActivity.class.getSimpleName() + "传过去的值....");
        EventBus.getDefault().post(bed);
    }

    class XRecyleviewAdapter extends BaseAdapter<Integer, XRcyleviewHolder> {

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
            Integer item = getItem(position);
            xRcyleviewHolder.advertIv.setImageResource(item);
        }

    }

    class XRcyleviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.advert_iv)
        ImageView advertIv;

        public XRcyleviewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
