package com.common.projectcommonframe.ui.test.banner;


import com.common.projectcommonframe.base.BaseBean;

/**
 * 广告
 */
public class BannerBean extends BaseBean {

    private int id;
    private String createtime;
    private String lastupdatetime;
    private String pic_url;
    private String skip_url;

    private int actiontype;
    private String title_name;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setLastupdatetime(String lastupdatetime) {
        this.lastupdatetime = lastupdatetime;
    }

    public String getLastupdatetime() {
        return lastupdatetime;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public String getPic_url() {
        if (pic_url == null) {
            pic_url = "";
        }
        return pic_url;
    }

    public String getSkip_url() {
        if (skip_url == null) {
            skip_url = "";
        }
        return skip_url;
    }

    public void setSkip_url(String skip_url) {
        this.skip_url = skip_url;
    }

    public int getActiontype() {
        return actiontype;
    }

    public void setActiontype(int actiontype) {
        this.actiontype = actiontype;
    }

    public String getTitle_name() {
        if (title_name == null) {
            title_name = "";
        }
        return title_name;
    }

    public void setTitle_name(String title_name) {
        this.title_name = title_name;
    }
}
