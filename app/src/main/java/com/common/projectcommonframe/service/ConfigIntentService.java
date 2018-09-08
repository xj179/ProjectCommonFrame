package com.common.projectcommonframe.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;

import com.common.projectcommonframe.components.AppDefault;
import com.common.projectcommonframe.components.BusEventData;
import com.common.projectcommonframe.network.Api;
import com.common.projectcommonframe.utils.ThreadTransformer;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Emitter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *程序配置Service，可以做一些版本更新和根据服务器配置APP之类的
 */
public class ConfigIntentService extends IntentService {

    Disposable disposable ;

    //小文件2M多
    public static final  String TEST_APK_URL = "http://imtt.dd.qq.com/16891/FAF5D8FFAC0F002F7353A52AE6B85ACC.apk?fsname=com.fiistudio.fiinote_11.4_147.apk&csr=1bbd" ;
    //大文件66M多
//    public static final  String TEST_APK_URL = "http://imtt.dd.qq.com/16891/756C8B919B074A7B13162F29BD1605AA.apk?fsname=com.exingxiao.insureexpert_2.1.0_20100.apk&csr=1bbd" ;

    public ConfigIntentService() {
        super(ConfigIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppDefault appDefault = new AppDefault() ;


        //1.获取服务器配置参数

        //2.获取应用版本更新
//        Api.getApiService().appUpdate() ;
        //如果有版本信息直接去下载
        disposable =  Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                final String appFilePath = getExternalCacheDir() + "/app_" + "getNewAppResult.result.ver.apk";
                //文件不存在下载
                if (!new File(appFilePath).exists()){
                    try {
                        Call<ResponseBody> call = Api.getApiService().downloadFile(TEST_APK_URL);
                      /*  Response<ResponseBody> execute = call.execute();
                        saveFile( execute.body().byteStream(), appFilePath);*/
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                ResponseBody body = response.body();
                                saveFile(body.byteStream(), appFilePath);

                                //更新
                                BusEventData data = new BusEventData(BusEventData.KEY_APP_UPDATE) ;
                                EventBus.getDefault().post(data);
                                KLog.i("responseBody:" + body);
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                KLog.i("onFailure:" + t.toString());
                            }
                        });
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                } else {
                    //EventBus直接发送消息提示安装
                    BusEventData data = new BusEventData(BusEventData.KEY_APP_UPDATE) ;
                    EventBus.getDefault().post(data);
                }
            }
        }).compose(new ThreadTransformer<String>()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String result) throws Exception {
                KLog.i("result:" + result);
            }
        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    public boolean saveFile(InputStream is, String fileName) {
        try {
            BufferedInputStream in=null;
            BufferedOutputStream out=null;
            in=new BufferedInputStream(is);
            out=new BufferedOutputStream(new FileOutputStream(fileName));
            int len=-1;
            byte[] b=new byte[1024];
            while((len=in.read(b))!=-1){
                out.write(b,0,len);
            }
            in.close();
            out.close();
            return true ;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false ;
    }
}
