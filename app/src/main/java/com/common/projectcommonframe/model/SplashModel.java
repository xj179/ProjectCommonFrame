package com.common.projectcommonframe.model;

import com.common.projectcommonframe.base.BaseMode;
import com.common.projectcommonframe.components.AppDefault;
import com.common.projectcommonframe.components.ConstantConfig;

/**
 * 程序启动页(包含引导页，启动页，广告页)
 * @param <T>
 */
public class SplashModel<T> extends BaseMode {

    private AppDefault appDefault = new AppDefault() ;

    /**
     * 是否有新的引导页图片
     * @return
     */
    public boolean hasNew(){
        if (appDefault.getGuidePicVersion() < ConstantConfig.GUIDE_VERSION) {
            appDefault.setGuidePicVersion(ConstantConfig.GUIDE_VERSION);
            return true  ;
        }
        return false ;
    }


    /**
     * 是否展示广告
     * @return
     */
    public boolean hasAD(){
        //app配置参数 > 服务器配置
        if (!ConstantConfig.SHOW_AD){//app参数，不显示广告
            return false;
        }
        if (!appDefault.isShowAD()) {//服务器参数，不显示广告
            return false;
        }
        return true;
    }


    /**
     * 是否已经登录(如果有的话)
     * @return
     */
    public boolean hasLogin(){
        return appDefault.getUserid()!=null;
    }

}
