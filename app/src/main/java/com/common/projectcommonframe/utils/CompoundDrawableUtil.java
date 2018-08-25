package com.common.projectcommonframe.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

/**
 * 代码动态设置TextView(包括TextView的子类) 上， 下， 左， 右的图片
 * setCompoundDrawablesWithIntrinsicBounds 和 setCompoundDrawables 方法设置
 *  区别:
 *    .setCompoundDrawables： （可以设置图片的大小,必需先设置setBounds(Rect)） 可以在上、下、左、右设置图标，如果不想在某个地方显示，则设置为null。但是Drawable必须已经setBounds(Rect)。意思是你要添加的资源必须已经设置过初始位置、宽和高等信息。
 *                           mBtn = (Button) findViewById(R.id.id_btn);
                            Drawable icon = this.getResources().getDrawable(R.drawable.ic_launcher);
                           // 必须设置
                            icon.setBounds(1, 1, 100, 100);
                            mBtn.setCompoundDrawables(null, null, icon, null);

                            TextView mTvMainTitleLeft = (TextView) findViewById(R.id.tv_title_left);
                            //      Drawable dwLeft = ContextCompat.getDrawable(getContext(), res); android studio中的获取方法
                            Drawable dwLeft = getResources().getDrawable(R.mipmap.ic_launcher);
                            dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
                            mTvMainTitleLeft.setCompoundDrawables(dwLeft, null, null, null);
 *    .setCompoundDrawablesWithIntrinsicBounds （图片的大小固定死） 可以在上、下、左、右设置图标，如果不想在某个地方显示，则设置为null。图标的宽高将会设置为固有宽高，既自动通过getIntrinsicWidth和getIntrinsicHeight获取。
 *              mBtn = (Button) findViewById(R.id.id_btn);
                Drawable icon = this.getResources().getDrawable(R.drawable.ic_launcher);
                 mBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
 */
public class CompoundDrawableUtil {

    /**
     * 设置 View， 上下左右图片设置，暂只支持设置单个方向
     * @param view
     * @param leftDrawable
     * @param rightDrawable
     * @param topDrawable
     * @param downDrawable
     */
    private static void setCompoundDrawable(View view, Drawable leftDrawable, Drawable rightDrawable, Drawable topDrawable,Drawable downDrawable){
        if (view != null && view instanceof TextView) {
            TextView textView = (TextView) view;
            if (leftDrawable != null) {
                leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                textView.setCompoundDrawables(leftDrawable, null, null, null);
                return ;
            } else if(rightDrawable != null) {
                rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, rightDrawable, null);
                return ;
            }else if(topDrawable != null) {
                topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
                textView.setCompoundDrawables(null, topDrawable, null, null);
                return ;
            }else if(downDrawable != null) {
                downDrawable.setBounds(0, 0, downDrawable.getMinimumWidth(), downDrawable.getMinimumHeight());
                textView.setCompoundDrawables(null, null, null, downDrawable);
                return ;
            }
        } else {
//            new Exception("View is null....") ;
        }
    }

    /**
     * 设置左边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableOfLeft(View view, Drawable drawable){
        setCompoundDrawable(view, drawable, null, null, null) ;
    }

    /**
     * 设置右边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableOfRight(View view, Drawable drawable){
        setCompoundDrawable(view, null, drawable, null, null) ;
    }

    /**
     * 设置上边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableOfTop(View view, Drawable drawable){
        setCompoundDrawable(view, null, null, drawable, null) ;
    }

    /**
     * 设置下边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawablesOfDown(View view, Drawable drawable){
        setCompoundDrawable(view, null, null, null, drawable) ;
    }


    /**
     * 设置 View， 上下左右图片设置，暂只支持设置单个方向（图片大小固定）
     * @param view
     * @param leftDrawable
     * @param rightDrawable
     * @param topDrawable
     * @param downDrawable
     */
    private static void setCompoundDrawableWithIntrinsicBounds(View view, Drawable leftDrawable, Drawable rightDrawable, Drawable topDrawable,Drawable downDrawable){
        if (view != null && view instanceof TextView) {
            TextView textView = (TextView) view;
            if (leftDrawable != null) {
                textView.setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
                return ;
            } else if(rightDrawable != null) {
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
                return ;
            }else if(topDrawable != null) {
                textView.setCompoundDrawablesWithIntrinsicBounds(null, topDrawable, null, null);
                return ;
            }else if(downDrawable != null) {
                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, downDrawable);
                return ;
            }
        } else {
//            new Exception("View is null....") ;
        }
    }

    /**
     * 设置左边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableWithIntrinsicBoundsOfLeft(View view, Drawable drawable){
        setCompoundDrawableWithIntrinsicBounds(view, drawable, null, null, null) ;
    }

    /**
     * 设置右边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableWithIntrinsicBoundsOfRight(View view, Drawable drawable){
        setCompoundDrawableWithIntrinsicBounds(view, null, drawable, null, null) ;
    }

    /**
     * 设置上边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableWithIntrinsicBoundsOfTop(View view, Drawable drawable){
        setCompoundDrawableWithIntrinsicBounds(view, null, null, drawable, null) ;
    }

    /**
     * 设置下边图片
     * @param view
     * @param drawable
     */
    public static void setCompoundDrawableWithIntrinsicBoundsOfDown(View view, Drawable drawable){
        setCompoundDrawableWithIntrinsicBounds(view, null, null, null, drawable) ;
    }

    /**
     * 设置图片和text之间的间距
     * @param view
     * @param pad
     */
    public static void setCompoundDrawablePadding(View view, int pad){
        if (view != null && view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setCompoundDrawablePadding(pad);
        }
    }

    /**
     * 设置整体的padding
     * @param view
     * @param pad
     */
    public static void setPadding(View view, int pad){
        if (view != null && view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setPadding(pad, 0, 0, 0);
        }
    }
}
