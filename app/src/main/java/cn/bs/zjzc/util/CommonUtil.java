package cn.bs.zjzc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;

public class CommonUtil {
    public static void clearEdits(EditText... editTexts) {
        if (editTexts == null)
            return;
        if (editTexts.length == 0)
            return;
        for (final EditText ed : editTexts) {
            ed.setText("");
        }
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    public static void mackCall(Context ctx, String phoneNumber) {
        try {
            // phoneNumber = URLEncoder.encode(phoneNumber, "UTF-8");
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                    + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
            // } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断字符串是否全为数字
     *
     * @param src
     * @return
     */
    public static boolean allIsNum(String src) {
        if (TextUtils.isEmpty(src)) {
            return false;
        }

        return regMethod(src, "^[1-9]\\d*|0$");
    }

    /**
     * 判断字符串是否数值
     *
     * @param str
     * @return true:是数值 ；false：不是数值
     * @author:WD_SUHUAFU
     */
    public static boolean isNumber(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return regMethod(str, "^[0-9]+(.[0-9]*)?$");
    }

    /**
     * 执行正则匹配
     *
     * @param str
     * @param patternStr
     * @return
     */
    private static boolean regMethod(String str, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);

        Matcher matcher = pattern.matcher(str);

        return matcher.matches();
    }

    /**
     * 时间格式化，返回时分秒
     *
     * @param seconds
     * @return
     */
    public static String TimeFormatHMS(int seconds) {
        String timeFormat = "%s小时 %s分 %s秒";

        int h = 0, m = 0, s = 0;
        if ((seconds / 3600) > 0) {
            h = seconds / 3600;
            seconds %= 3600;
        }

        if (seconds / 60 > 0) {
            m = seconds / 60;
            seconds %= 60;
        }

        s = seconds;

        return String.format(timeFormat, h, m, s);
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
