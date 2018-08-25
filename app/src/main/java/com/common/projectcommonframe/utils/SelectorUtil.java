package com.common.projectcommonframe.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 代码动态设置Selector(StateListDrawablet和ColorStateList)
 */
public class SelectorUtil {


    /**
     * 代码生成选择器
     *
     * @param context   当前上下文
     * @param idNormal  默认图片id
     * @param idPressed 触摸时图片id
     * @param idFocused 获得焦点时图片id
     * @param idUnable  没有选中时图片id
     * @return
     */
    public static StateListDrawable createDrawableSelector(Context context, int idNormal,
                                                           int idPressed, int idFocused, int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources()
                .getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources()
                .getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources()
                .getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources()
                .getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed,
                android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled,
                android.R.attr.state_focused}, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * 控件选择器
     *
     * @param context   当前上下文
     * @param idNormal  默认图片id
     * @param idPressed 按压时图片id
     * @return
     */
    public static StateListDrawable createDrawableSelector(Context context, int idNormal, int idPressed) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources()
                .getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources()
                .getDrawable(idPressed);
        bg.addState(new int[]{android.R.attr.state_pressed,
                android.R.attr.state_enabled}, pressed);
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * @param context         下下文
     * @param normalDrawable  图片
     * @param checkedDrawable 选中图片
     * @return
     */
    public static StateListDrawable createDrawableSelector(Context context, Drawable normalDrawable,
                                                           Drawable checkedDrawable) {
        StateListDrawable bg = new StateListDrawable();
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, checkedDrawable);
        bg.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
        bg.addState(new int[]{android.R.attr.state_enabled}, normalDrawable);
        bg.addState(new int[]{}, normalDrawable);
        return bg;
    }

    /**
     * 设置文字颜色ColorSelector
     * 重载默认选中和按下是同一个颜色
     * !!!!!默认必需在下面不然不起作用
     * @param view
     * @param normal
     * @param press
     */
    public static void setTextColorSelector(View view, int normal, int press) {
        setTextColorSelector(view, normal, press, press) ;
    }


    /**
     * 设置文字颜色ColorSelector
     * Android 代码设置TextView 字体颜色--ColorStateList
     *!!!!!默认必需在下面不然不起作用
     * @param view
     * @param normal
     * @param press 色必须设置成如上“0xffffffff”形式,设置R.Color.red 会无效,目前不清楚怎么回事。希望有高人解答。
     */
    public static void setTextColorSelector(View view, int normal, int press, int selected) {
        int[] colors = new int[]{press, selected, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setTextColor(colorStateList);
        }
    }

    /**
     * 设置背景颜色ColorSelector
     * 重载默认选中和按下是同一个颜色
     * !!!!!默认必需在下面不然不起作用
     * @param view
     * @param normal
     * @param press
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setBackgroundColorSelector(View view, int normal, int press) {
        setBackgroundColorSelector(view, normal, press, press) ;
    }

    /**
     * 设置背景颜色ColorSelector
     * @param view
     * @param normal
     * @param press
     * @param selected
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setBackgroundColorSelector(View view, int normal, int press, int selected) {
        int[] colors = new int[]{press, selected, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        ColorStateList colorStateList = new ColorStateList(states, colors);
        view.setBackgroundTintList(colorStateList);
    }


    /**
     * 返回一个ColorStateList（默认按下和选中是同一种效果）
     * @param normal
     * @param pressed
     * @return
     */
    public static ColorStateList createColorStateList(int normal, int pressed) {
        return createColorStateList(normal, pressed, pressed) ;
    }

    /**
     * 代码设置TextView 字体颜色--ColorStateList
     * !!!!!!默认必需在下面不然不起作用
     * @param normal  默认颜色
     * @param pressed   按下颜色
     * @param selected   选中颜色
     * @return
     */
    public static ColorStateList createColorStateList(int normal, int pressed, int selected) {
        int[] colors = new int[]{pressed, selected, normal};
        int[][] states = new int[3][];
        states[0] = new int[]{android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_selected};
        states[2] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }


    /**
     * 替换StateListDrawable中的颜色
     *
     * @param view
     * @param color
     * @return
     */
    public static void replaceSelctor(View view, String color) {
        StateListDrawable mySelectorGrad = (StateListDrawable) view.getBackground();

        try {
            Class slDraClass = StateListDrawable.class;
            Method getStateCountMethod = slDraClass.getDeclaredMethod("getStateCount", new Class[0]);
            Method getStateSetMethod = slDraClass.getDeclaredMethod("getStateSet", int.class);
            Method getDrawableMethod = slDraClass.getDeclaredMethod("getStateDrawable", int.class);
            int count = (Integer) getStateCountMethod.invoke(mySelectorGrad, new Object[]{});//对应item标签
            for (int i = 0; i < count; i++) {
                int[] stateSet = (int[]) getStateSetMethod.invoke(mySelectorGrad, i);//对应item标签中的 android:state_xxxx
                if (stateSet == null || stateSet.length == 0) {
                    Log.d("xjlei", "state is null");
                    ColorDrawable drawable = (ColorDrawable) getDrawableMethod.invoke(mySelectorGrad, i);//这就是你要获得的Enabled为false时候的drawable
                } else {
                    for (int j = 0; j < stateSet.length; j++) {
                        Log.d("xjlei", "state =" + stateSet[j]);
                        LayerDrawable layerDrawable = (LayerDrawable) getDrawableMethod.invoke(mySelectorGrad, i);
                        Drawable background = layerDrawable.getDrawable(0);
                        if (background instanceof ShapeDrawable) {
                            ((ShapeDrawable) background).getPaint().setColor(Color.parseColor(color));
                        } else if (background instanceof GradientDrawable) {
                            ((GradientDrawable) background).setColor(Color.parseColor(color));
                        } else if (background instanceof ColorDrawable) {
                            ((ColorDrawable) background).setColor(Color.parseColor(color));
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
