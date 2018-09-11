package com.common.projectcommonframe.ui.test;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.common.projectcommonframe.R;
import com.common.projectcommonframe.base.BaseActivity;
import com.common.projectcommonframe.base.BasePresenter;
import com.common.projectcommonframe.base.BaseView;
import com.common.projectcommonframe.components.BusEventData;
import com.common.projectcommonframe.utils.TimeUtil;
import com.common.projectcommonframe.utils.ToastUtil;
import com.contrarywind.interfaces.IPickerViewData;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 仿 iOS 的 PickerView 控件，有时间选择和选项选择并支持一二三级联动效果，TimePopupWindow 时间选择器，支持年月日时分，年月日，时分等格式；
 * OptionsPopupWindow 选项选择器，支持一，二，三级选项选择，并且可以设置是否联动
 */
public class TestActivityPickerView extends BaseActivity {

    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();

    @BindView(R.id.check_btn)
    Button checkBtn;
    @BindView(R.id.check_btn2)
    Button checkBtn2;
    @BindView(R.id.textView)
    TextView textView;


    @Override
    public int getLayoutId() {
        return R.layout.test_picker_view_activity;
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
        getOptionData();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.check_btn, R.id.check_btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.check_btn:
                showTimePick();
                break;
            case R.id.check_btn2:
                showOptionsPick();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BusEventData bed =  new BusEventData(BusEventData.KEY_REFRESH);
        bed.setContent("TestActivityPickerView 传过去的值....");
        EventBus.getDefault().post(bed);
    }

    /**
     * 条件选择器
     */
    private void showOptionsPick() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2);
//                        + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                textView.setText(tx);
                ToastUtil.show(tx);
            }
        }).setTitleText("城市选择")
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
//                .setBgColor(Color.BLACK)
                .setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)
                .setTextColorCenter(Color.LTGRAY)
                .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setLabels("省", "市", "区")
                .setBackgroundId(0x00000000)//设置外部遮罩颜色
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        String str = "options1: " + options1 + "\noptions2: " + options2 + "\noptions3: " + options3;
                        Toast.makeText(TestActivityPickerView.this, str, Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
//      pvOptions.setSelectOptions(1,1);
//        pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        /*pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器*/
        pvOptions.show();
    }

    /**
     * 时间选择器
     */
    private void showTimePick() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(TestActivityPickerView.this, TimeUtil.dateToStr(date, TimeUtil.FORMAT_D), Toast.LENGTH_SHORT).show();
            }
        }).build();
        pvTime.show();
    }

    /**
     * 初始化Picker数据
     */
    private void getOptionData() {

        /**
         * 注意：如果是添加JavaBean实体数据，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */

//        getCardData();
//        getNoLinkData();

        //选项1
        options1Items.add(new ProvinceBean(0, "广东", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(1, "湖南", "描述部分", "其他数据"));
        options1Items.add(new ProvinceBean(2, "广西", "描述部分", "其他数据"));

        //选项2
        ArrayList<String> options2Items_01 = new ArrayList<>();
        options2Items_01.add("广州");
        options2Items_01.add("佛山");
        options2Items_01.add("东莞");
        options2Items_01.add("珠海");
        ArrayList<String> options2Items_02 = new ArrayList<>();
        options2Items_02.add("长沙");
        options2Items_02.add("岳阳");
        options2Items_02.add("株洲");
        options2Items_02.add("衡阳");
        ArrayList<String> options2Items_03 = new ArrayList<>();
        options2Items_03.add("桂林");
        options2Items_03.add("玉林");
        options2Items.add(options2Items_01);
        options2Items.add(options2Items_02);
        options2Items.add(options2Items_03);

        /*--------数据源添加完毕---------*/
    }

    class ProvinceBean implements IPickerViewData {

        int id;

        String name;

        String desc;

        String other;

        public ProvinceBean(int id, String name, String desc, String other) {
            this.id = id;
            this.name = name;
            this.desc = desc;
            this.other = other;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        //这个用来显示在PickerView上面的字符串,PickerView会通过getPickerViewText方法获取字符串显示出来。
        @Override
        public String getPickerViewText() {
            return name;
        }

    }
}
