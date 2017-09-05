package cn.bs.zjzc.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import cn.bs.zjzc.R;

/**
 * 消息提示框工具类
 */
public class MessageTools {

    /**
     * 从当前activity中弹出AlertDialog
     *
     * @param activity         当前activity
     * @param title            标题
     * @param msg              内容
     * @param cancelable       是否back键取消
     * @param positiveBtnTxt   左按钮文字
     * @param positiveListener 左按钮事件监听
     * @param negativeBtnTxt   右按钮文字
     * @param negativeListener 右按钮事件监听
     */
    public static void showDialog(Activity activity,
                                  String title, String msg, boolean cancelable,
                                  String positiveBtnTxt,
                                  DialogInterface.OnClickListener positiveListener,
                                  String negativeBtnTxt,
                                  DialogInterface.OnClickListener negativeListener) {
        createDialog(activity, title, msg, cancelable, positiveBtnTxt,
                positiveListener, negativeBtnTxt, negativeListener);
    }

    public static void showDialog(Activity activity,
                                  String title, View contentView, boolean cancelable,
                                  String positiveBtnTxt,
                                  DialogInterface.OnClickListener positiveListener,
                                  String negativeBtnTxt,
                                  DialogInterface.OnClickListener negativeListener) {
        createDialog(activity, title, contentView, cancelable, positiveBtnTxt,
                positiveListener, negativeBtnTxt, negativeListener);
    }

    private static void createDialog(final Activity activity,
                                     final String title, final Object msg, final boolean cancelable,
                                     final String positiveBtnTxt,
                                     final DialogInterface.OnClickListener positiveListener,
                                     final String negativeBtnTxt,
                                     final DialogInterface.OnClickListener negativeListener) {

        if (activity == null || activity.isFinishing()) return;

        activity.runOnUiThread(new Runnable() {
            public void run() {
                Builder builder = new Builder(activity);
                builder.setCancelable(cancelable);

                if (msg != null && msg instanceof String) {
                    builder.setMessage((String) msg);
                } else if (msg != null && msg instanceof View) {
                    builder.setView((View) msg);
                }

                if (!TextUtils.isEmpty(title)) {
                    builder.setTitle(title);
                }
                if (!TextUtils.isEmpty(positiveBtnTxt)) {
                    builder.setPositiveButton(positiveBtnTxt,
                            positiveListener);
                }
                if (!TextUtils.isEmpty(negativeBtnTxt)) {
                    builder.setNegativeButton(negativeBtnTxt,
                            negativeListener);
                }
                show = builder.show();
            }
        });
    }

    static AlertDialog show;

    public static void dissmiss() {
        if (show.isShowing())
            show.dismiss();
    }

    public static void showDialog(final Activity activity, final String title,
                                  final String msg, final boolean cancelable,
                                  final String positiveBtnTxt,
                                  final DialogInterface.OnClickListener positiveListener) {
        showDialog(activity, title, msg, cancelable, positiveBtnTxt,
                positiveListener, null, null);
    }

    /**
     * 将ResourceId转换成String，当resId为0时直接返回null
     *
     * @param context
     * @param resId
     * @return 返回相应的String，当resId不存在相应的String则返回null
     */
    private static String resId2String(Context context, int resId) {
        String str = null;
        if (resId != 0) {

            str = context.getString(resId);

//			str = CommonUtil.resId2String(context, resId);
        }
        return str;
    }

    public static void showDialog(final Activity activity,
                                  final int titleResId, final int msgResId, final boolean cancelable,
                                  final int positiveBtnTxtResId,
                                  final DialogInterface.OnClickListener positiveListener,
                                  final int negativeBtnTxtResId,
                                  final DialogInterface.OnClickListener negativeListener) {
        String title = resId2String(activity, titleResId);
        String msg = resId2String(activity, msgResId);
        String positiveBtnTxt = resId2String(activity, positiveBtnTxtResId);
        String negativeBtnTxt = resId2String(activity, negativeBtnTxtResId);
        showDialog(activity, title, msg, cancelable, positiveBtnTxt,
                positiveListener, negativeBtnTxt, negativeListener);
    }

    public static void showDialog(final Activity activity,
                                  final int titleResId, final int msgResId, final boolean cancelable,
                                  final int positiveBtnTxtResId,
                                  final DialogInterface.OnClickListener positiveListener) {
        showDialog(activity, titleResId, msgResId, cancelable,
                positiveBtnTxtResId, positiveListener, 0, null);
    }

    public static void showDialogOk(Activity activity, String msg,
                                    boolean cancelable, DialogInterface.OnClickListener listener) {
        showDialog(activity, resId2String(activity, R.string.tishi), msg,
                cancelable, resId2String(activity, R.string.button_ok),
                listener);
    }

    public static void showDialogOk(Activity activity, int msgResId,
                                    boolean cancelable, DialogInterface.OnClickListener listener) {
        showDialog(activity, R.string.tishi, msgResId, cancelable,
                R.string.button_ok, listener);
    }

    /**
     * 弹出温馨提示dialog，中间显示msg,只有一个确定按钮，可back键取消
     *
     * @param activity
     * @param msg
     */
    public static void showDialogOk(Activity activity, String msg) {
        showDialogOk(activity, msg, true, null);
    }

    /**
     * 弹出温馨提示dialog，中间显示msg,只有一个确定按钮，可back键取消
     *
     * @param activity
     * @param msgResId
     */
    public static void showDialogOk(Activity activity, int msgResId) {
        showDialogOk(activity, msgResId, true, null);
    }

    /**
     * Toast形式显示信息
     *
     * @param activity
     * @param msg
     */
    public static void showToast(final Activity activity, final String msg) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * Toast形式显示信息
     *
     * @param activity
     * @param msgResId
     */
    public static void showToast(final Activity activity, final int msgResId) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, msgResId, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
