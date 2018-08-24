package com.common.projectcommonframe.utils;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * 分辨率处理工具
 */
public class DensityUtil {

    public static final int SCREEN_H=750;//设计图原始宽

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float density, float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float density, float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取手机的密度
     */
    public static float getDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;
    }
    /**
     * 屏幕的宽度
     */
    public static int getScreenW(Context context) {
        return  context.getResources().getDisplayMetrics().widthPixels;

    }

    /**
     * 屏幕的高度
     */
    public static int getScreenH(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
