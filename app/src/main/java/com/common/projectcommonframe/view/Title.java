package com.common.projectcommonframe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.projectcommonframe.R;
import com.common.projectcommonframe.utils.DensityUtil;

/**
 * 自定义公共Title
 */
public class Title extends RelativeLayout {

    private int padding;
    private int margin;
    private TextView titleView;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView rightButton2;//右边靠里面的图片
    private int textColor;
    private TextView rightText;

    public Title(Context context) {
        this(context,null);
    }

    public Title(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Title(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        padding = DensityUtil.dip2px(context,8);
        margin = DensityUtil.dip2px(context,8);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Title);
        boolean hasDivider = a.getBoolean(R.styleable.Title_hasDivider, false);
        if (hasDivider){
            View view = new View(context);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            view.setBackgroundColor(ContextCompat.getColor(context,R.color.dividerColor));
            addView(view,layoutParams);
        }
        setBackgroundColor(a.getColor(R.styleable.Title_titleBg, ContextCompat.getColor(context,R.color.colorPrimary)));
        textColor = a.getColor(R.styleable.Title_textColor, Color.WHITE);
        a.recycle();
    }

    public void reset(){
        if (titleView!=null) {
            titleView.setVisibility(View.INVISIBLE);
        }
        if (leftButton!=null) {
            leftButton.setVisibility(View.INVISIBLE);
        }
        if (rightButton!=null) {
            rightButton.setVisibility(View.INVISIBLE);
        }
        if (rightButton2!=null) {
            rightButton2.setVisibility(View.INVISIBLE);
        }
    }

    public void setTitle(String title){
        if (titleView == null) {
            titleView = new TextView(getContext());
            titleView.setTextSize(20);
            titleView.setTextColor(textColor);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(titleView,layoutParams);
        }else {
            titleView.setVisibility(View.VISIBLE);
        }
        titleView.setPadding(150, 0, 150, 0);
        titleView.setMaxLines(1);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        titleView.setText(title);
    }

    public void setTitle(int title){
        setTitle(getContext().getString(title));
    }

    public void setLeftButton(int imgId,OnClickListener listener){
        if (leftButton==null) {
            leftButton = new ImageView(getContext());
            leftButton.setPadding(padding,padding,padding,padding);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.leftMargin = margin;
            addView(leftButton,layoutParams);
        }else {
            leftButton.setVisibility(View.VISIBLE);
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), imgId).mutate();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable ,textColor);
        leftButton.setImageDrawable(wrappedDrawable);
        leftButton.setOnClickListener(listener);
    }

    public void setRightButton(int imgId,OnClickListener listener){
        if (rightButton==null) {
            rightButton = new ImageView(getContext());
            rightButton.setPadding(padding,padding,padding,padding);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.rightMargin = margin;
            addView(rightButton,layoutParams);
        }else {
            rightButton.setVisibility(View.VISIBLE);
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), imgId).mutate();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable ,textColor);
        rightButton.setImageDrawable(wrappedDrawable);
        rightButton.setOnClickListener(listener);
    }
    /**
     * 右边靠里面的的image
     */

    public void setRightButton2(int imgId,OnClickListener listener){
        if (rightButton2==null) {
            rightButton2 = new ImageView(getContext());
            rightButton2.setPadding(padding,padding,padding,padding);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.rightMargin = margin*6;//有可能需要设置一下
            addView(rightButton2,layoutParams);
        }else {
            rightButton2.setVisibility(View.VISIBLE);
        }
        Drawable drawable = ContextCompat.getDrawable(getContext(), imgId).mutate();
        Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrappedDrawable ,textColor);
        rightButton2.setImageDrawable(wrappedDrawable);
        rightButton2.setOnClickListener(listener);
    }

    public void setTitleTextColor(int color){
        if (titleView!=null) {
            titleView.setTextColor(color);
        }
    }

    public void setRightButtonVisiable(boolean visiable){
        if (rightButton!=null){
            rightButton.setVisibility(visiable? View.VISIBLE: View.INVISIBLE);
        }
    }

    public void setRightButtonEnable(boolean enable){
        if (rightButton!=null){
            rightButton.setEnabled(enable);
        }
    }

    public void setRightText(int rightstring,OnClickListener listener){
        if (rightText==null) {
            rightText = new TextView(getContext());
            rightText.setClickable(true);
            rightText.setPadding(padding,padding,padding,padding);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.rightMargin = margin;
            addView(rightText,layoutParams);
        }else {
            rightText.setVisibility(View.VISIBLE);
        }
        rightText.setText(getContext().getString(rightstring));
        rightText.setOnClickListener(listener);
    }
    public void setRightTextColor(int color){
        if (rightText!=null) {
            rightText.setTextColor(color);
        }
    }
    public void setRightTextSize(int size){
        if (rightText!=null) {
            rightText.setTextSize(size);
        }
    }

    public ImageView getRightButton2() {
        return rightButton2;
    }
}
