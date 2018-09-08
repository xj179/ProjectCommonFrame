package com.common.projectcommonframe.network;


/**
 * 网络访问请求类
 */
public class Api {

    private String baseUrl = "http://www.kuaidi100.com/";
//    private String baseUrl = "http://hbhz.sesdf.org/api/";
//    http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号

    private volatile static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (Api.class) {
                if (apiService == null) {
                    new Api();
                }
            }
        }
        return apiService;
    }

    private Api() {
        BaseApi baseApi = new BaseApi();
        apiService = baseApi.getRetrofit(baseUrl).create(ApiService.class);
    }

}
