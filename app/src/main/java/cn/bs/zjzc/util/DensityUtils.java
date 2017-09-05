package cn.bs.zjzc.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 常用单位转换的辅助类
 *
 * @author zhy
 */
public class DensityUtils {
    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */
        if(TextUtils.isEmpty(mobiles))return false;
        String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    public static String parseDate(Date date, String format) {
        if (TextUtils.isEmpty(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        if (date == null) {
            date = new Date();
        }
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "无";
        }
    }

    /**
     * 随机数
     *
     * @param max
     * @param min
     * @return
     */
    public static int randNum(int max, int min) {
        return (int) Math.round(Math.random() * (max - min) + 1);
    }

    /**
     * Han
     * 得到一个webview
     *
     * @param web
     * @param prog
     * @param context
     * @return
     */
    public static WebView getCommdWebView(WebView web, final ProgressBar prog,
                                          Context context) {
        // 设置可以访问js
        web.getSettings().setJavaScriptEnabled(true);
        // 与h5页面进行绑定
        // web.addJavascriptInterface(new WebInstance(context) , "Android");
        // // 视图辅助类WebViewClient :1.防止点击当前webView 打开浏览器 2.
        WebViewClient wvc = new WebViewClient() {
            // 页面开始加载的时候
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            // 页面加载完成的时候
            @Override
            public void onPageFinished(WebView view, String url) {
            }
        };
        web.setWebViewClient(wvc);

        // 浏览器辅助类WebChromeClient onProgressChanged 回调函数:条件 载入进度发生变化
        WebChromeClient wcc = new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress >= 100) {
                    prog.setVisibility(View.INVISIBLE);
                } else {
                    prog.setVisibility(View.VISIBLE);
                    prog.setProgress(newProgress);
                }
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }
        };
        web.setWebChromeClient(wcc);
        return web;
    }

}
