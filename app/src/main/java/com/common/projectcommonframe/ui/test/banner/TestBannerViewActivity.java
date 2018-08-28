package com.common.projectcommonframe.ui.test.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.common.projectcommonframe.R;
import com.common.projectcommonframe.components.MyApplication;
import com.common.projectcommonframe.utils.glide.GlideUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * convenientbanner 控件 的 demo
 */
public class TestBannerViewActivity extends AppCompatActivity implements OnItemClickListener {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<Integer> localImages = new ArrayList<Integer>();
    private List<String> networkImages;
    private String[] images = {
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_banner_activity_main);
        initViews();
        init();
    }

    private void initViews() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
    }

    private void init(){
        loadTestDatas();
        //本地图片例子
       /* convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)   //设置指示符位置（左，中， 右 默认是在中间）
                .setOnItemClickListener(this);*/
//                .setOnPageChangeListener(this);//监听翻页事件

//        convenientBanner.setManualPageable(false);//设置不能手动影响

        //网络加载例子
        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator() {
            @Override
            public Holder createHolder(View itemView) {
                return new ImageHolder(itemView);
            }

            @Override
            public int getLayoutId() {
                return R.layout.test_item_banner_image;
            }
        }, networkImages)
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
        .setPageIndicator(new int[]{R.drawable.dot_main_default, R.drawable.dot_main_selected})
        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)   //设置指示符位置（左，中， 右 默认是在中间）
        .setOnItemClickListener(this);


      /*  convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages);*/



//手动New并且添加到ListView Header的例子
//        ConvenientBanner mConvenientBanner = new ConvenientBanner(this,false);
//        mConvenientBanner.setMinimumHeight(500);
//        mConvenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
//                        //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnItemClickListener(this);
//        listView.addHeaderView(mConvenientBanner);
    }

    /*
    加入测试Views
    * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 7; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));


////        //各种翻页效果
//        transformerList.add(DefaultTransformer.class.getSimpleName());
//        transformerList.add(AccordionTransformer.class.getSimpleName());
//        transformerList.add(BackgroundToForegroundTransformer.class.getSimpleName());
//        transformerList.add(CubeInTransformer.class.getSimpleName());
//        transformerList.add(CubeOutTransformer.class.getSimpleName());
//        transformerList.add(DepthPageTransformer.class.getSimpleName());
//        transformerList.add(FlipHorizontalTransformer.class.getSimpleName());
//        transformerList.add(FlipVerticalTransformer.class.getSimpleName());
//        transformerList.add(ForegroundToBackgroundTransformer.class.getSimpleName());
//        transformerList.add(RotateDownTransformer.class.getSimpleName());
//        transformerList.add(RotateUpTransformer.class.getSimpleName());
//        transformerList.add(StackTransformer.class.getSimpleName());
//        transformerList.add(ZoomInTransformer.class.getSimpleName());
//        transformerList.add(ZoomOutTranformer.class.getSimpleName());

//        transformerArrayAdapter.notifyDataSetChanged();
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
//        //开始自动翻页
        convenientBanner.startTurning();
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
//        //停止翻页
        convenientBanner.stopTurning();
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
        //打开预览View
        TestImagePagerActivity.startImagePagerActivity(this, Arrays.asList(images), 0);
//        convenientBanner.setCanLoop(!convenientBanner.isCanLoop());
    }

    class ImageHolder extends Holder<String> {
        private ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.advert_iv) ;
        }

        @Override
        public void updateUI(String data) {
            //使用Glide加载网络图片
            GlideUtil.loadImage(MyApplication.getInstance(), data, imageView);
        }
    }

    /**
     * 正式项目中是一个广告对象，包括图片Uri和跳转的URL等信息
     */
    /*class ImageHolder extends Holder<Advertisement> {
        private ImageView imageView;

       public ImageHolder(View itemView) {
            super(itemView);
        }

       @Override
        protected void initView(View itemView) {
            imageView = itemView.findViewById(R.id.advert_iv) ;
        }

        @Override
        public void UpdateUI(Context context, final int position, Advertisement data) {
            String url = data.getPic_url();
            String httpUrl = UrlUtils.parseUrl(url);
            Img.load(imageView, R.mipmap.content_zwt5, httpUrl);
        }
    }*/
}

