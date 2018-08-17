package com.common.projectcommonframe.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 作者：senon on 2017/12/7 14:21
 * 邮箱：a1083911695@163.com
 */
public class ToastUtil {
    private static Toast toast;
    private static TextView stoast;

    public static void showShortToast(String msg) {
        showCustomToast(ApplicationUtil.getContext(), msg, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(int msgId) {
        showCustomToast(ApplicationUtil.getContext(), msgId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(String msg) {
        showCustomToast(ApplicationUtil.getContext(), msg, Toast.LENGTH_LONG);
    }

    public static void showLongToast(int msgId) {
        showCustomToast(ApplicationUtil.getContext(), msgId, Toast.LENGTH_LONG);
    }

    public static void showToastInUiThread(final Activity activity, final String msg) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    showCustomToast(activity, msg);
                }
            });
        }
    }

    public static void showToastInUiThread(final Activity activity, final int stringId) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    showCustomToast(activity, stringId);
                }
            });
        }
    }

    private static void showCustomToast(Context context, int msgId) {
        final String msg = context.getResources().getString(msgId);
        showCustomToast(context, msg);
    }

    private static void showCustomToast(Context context, String msg) {
        showCustomToast(context, msg, Toast.LENGTH_SHORT);
    }

    private static void showCustomToast(Context context, int msgId, int duration) {
        final String msg = context.getResources().getString(msgId);
        showCustomToast(context, msg, duration);
    }

    private static void showCustomToast(final Context context, final String msg, final int duration) {
        if (context == null) {
            return;
        }
        if (Looper.myLooper() == Looper.getMainLooper()) {
            showToast(context, msg, duration);
        } else {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    showToast(context, msg, duration);
                }
            });
        }
    }

    private static void showToast(Context context, String msg, int duration) {
        if (null != context) {
            if (toast == null) {
                toast = Toast.makeText(context, msg, duration);
            }else {
                stoast.setText(msg);
            }
            toast.show();
        }
    }

}
