package com.common.projectcommonframe.components;

import com.common.projectcommonframe.R;

/**
 * 常量配置
 */
public interface ConstantConfig {

    //Glide 错误默认图片
    int  ERROR_IMG = R.drawable.ic_launcher;

    //Glide 点位默认图片
    int  PLACEHOLDER_IMG = R.drawable.ic_launcher;

    //引导图片版本号，如果SP中的版本号小于当前配置的版本号就显示引导图片(通过这种配置可以更新版本的时候也显示新的引导图片)
    int GUIDE_VERSION = 2;

    //是否显示广告
    boolean SHOW_AD = false;

}
