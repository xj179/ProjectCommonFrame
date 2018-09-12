package com.common.projectcommonframe.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.common.projectcommonframe.BuildConfig;

import java.io.File;
import java.util.List;

/** 
 *
 */  
public class ApkTool {

    /**
     * 安装APk
     * @param context
     * @param apkPath
     */
    public static  void apkInstall(Context context, String apkPath){
//        String pathName = Environment.getExternalStorageDirectory() + File.separator + "test.apk" ;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){  //版本大于或等于7.0做特殊处理
                File file= new File(apkPath);
                Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".apkprovider", file);//在AndroidManifest中的android:authorities值
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(intent);
            }else {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 卸载APK
     */
    public  static  void apkUninstall(Context context, String packageID){
        Uri uri = Uri.parse("package:" + packageID);
        Intent deleteIntent = new Intent(Intent.ACTION_DELETE, uri);
        deleteIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) ;
        context.startActivity(deleteIntent);

    }
  
/*    public static List<AppBean> scanLocalInstallAppList(Context context) {
        List<AppBean> appBeans = new ArrayList<AppBean>();  
        try {  
        	PackageManager packageManager = context.getPackageManager() ;
            List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);  
            for (int i = 0; i < packageInfos.size(); i++) {  
                PackageInfo packageInfo = packageInfos.get(i);  
            AppBean appBean = new AppBean();  
            //过滤掉系统app  
            if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {  
            	appBean.setAppType("0");  //系统应用
            }else {
            	appBean.setAppType("1");  //非系统应用
            }
            appBean.setPackageName(packageInfo.packageName); 
            appBean.setAppVersion(packageInfo.versionName);
            appBean.setAppVersioncode(packageInfo.versionCode+"");
            appBean.setClassName(getLauncherActivityNameByPackageName(context, packageInfo.packageName));
            appBean.setAppName(packageManager.getApplicationLabel(  
            		packageInfo.applicationInfo).toString());
            appBeans.add(appBean);  
            }  
        }catch (Exception e){  
            Log.e("xjlei","===============获取应用包信息失败");  
        } 
//        Log.i("xjlei", "appBeans--->" + appBeans.toString()) ;
        return appBeans;  
    }  */
    
    /** 
     * get the launcher activity class full name of an application by the package name 
     *  根据包名得到启动类名
     * @param context 
     *            the context of current application 
     * @param packageName 
     *            the package name of the application (it can be any application) 
     * @return 
     */  
    public static String getLauncherActivityNameByPackageName(Context context, String packageName) {  
        String className = null;  
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);//android.intent.action.MAIN  
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);//android.intent.category.LAUNCHER  
        resolveIntent.setPackage(packageName);  
        List<ResolveInfo> resolveinfoList = context.getPackageManager().queryIntentActivities(resolveIntent, 0);  
        if (!resolveinfoList.isEmpty()) {
        	ResolveInfo resolveinfo = resolveinfoList.iterator().next();  
        	if (resolveinfo != null) {  
        		className = resolveinfo.activityInfo.name;  
        	}  
		}else {
			className = "" ;
		}
        return className;  
    }  
  
}  
