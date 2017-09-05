package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.L;


public class AvCommdWebView extends BaseActivity {


    private ProgressBar prog;
    private WebView web;
    private Context context;
    private TopBarView webViewTop;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_commd_webview);
        context = this;
        iniBasic();
    }


    private void iniBasic() {
        webViewTop = ((TopBarView) findViewById(R.id.webViewTop));
        prog = (ProgressBar) findViewById(R.id.han_commd_webview_progress);
        web = (WebView) findViewById(R.id.han_commd_webview);
        web = DensityUtils.getCommdWebView(web, prog, context);
        doLoad();
    }

    private void doLoad() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        String city = getIntent().getStringExtra("titleRight");
        if (!TextUtils.isEmpty(city)) {
            webViewTop.setRightText(city);
        }
        webViewTop.setTitle(title);
        if (!TextUtils.isEmpty(url)) {
            web.loadUrl(url);
            L.i(" commd_web_view load url  " + url);
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        if (web.canGoBack()) {
            web.goBack();
        } else {
            this.finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        web.destroy();
    }
}
