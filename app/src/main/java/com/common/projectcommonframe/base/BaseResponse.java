package com.common.projectcommonframe.base;

/**
 * 请求响应结果基类
 * @param <T>
 */
public class BaseResponse<T> {

    /**返回状态码*/
    protected  int code ;

    /**返回消息*/
    protected String msg ;

    /**数据*/
    protected  T data ;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
