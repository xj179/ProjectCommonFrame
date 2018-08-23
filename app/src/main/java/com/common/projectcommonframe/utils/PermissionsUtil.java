package com.common.projectcommonframe.utils;import android.Manifest;import android.app.Activity;import android.content.pm.PackageManager;import android.os.Build;import android.support.v4.app.ActivityCompat;import android.support.v4.content.ContextCompat;/** * 权限工具类 */public class PermissionsUtil {    /**     * 还有日历权限，传感器权限需要动态申请     */    public static final String[] PERMISSONS_GROUP = new String[]{        Manifest.permission.READ_CONTACTS, Manifest.permission.RECORD_AUDIO,        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SEND_SMS    };    public static boolean checkReadContactsPermission(Activity activity, boolean request){        return checkPermission(activity, Manifest.permission.READ_CONTACTS,request);    }    public static boolean checkRecordAudioPermission(Activity activity, boolean request){        return checkPermission(activity, Manifest.permission.RECORD_AUDIO,request);    }    public static boolean checkLocationPermission(Activity activity, boolean request){        return checkPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION,request);    }    public static boolean checkCameraPermission(Activity activity, boolean request){        return checkPermission(activity, Manifest.permission.CAMERA,request);    }    private static boolean checkPermission(Activity activity, String permission, boolean request){        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&                ContextCompat.checkSelfPermission(activity,permission) != PackageManager.PERMISSION_GRANTED){            if (request && !activity.getPackageManager().isPermissionRevokedByPolicy(permission, activity.getPackageName())){                ActivityCompat.requestPermissions(activity,new String[]{permission}, 0);            }            return false;        }else {            return true;        }    }    public static boolean checkPermissionAlwaysRequest(Activity activity, String[] permissions){        return checkPermissionAlwaysRequest(activity,permissions,0);    }    public static boolean checkPermissionAlwaysRequest(Activity activity, String[] permissions, int requestCode){        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ){            boolean request = false;            for (int i = 0; i < permissions.length; i++) {                if (ContextCompat.checkSelfPermission(activity,permissions[i]) != PackageManager.PERMISSION_GRANTED) {                    request = true;                    break;                }            }            if (request){                ActivityCompat.requestPermissions(activity,permissions, requestCode);                return false;            }        }        return true;    }}