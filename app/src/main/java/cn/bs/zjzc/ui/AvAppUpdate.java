package cn.bs.zjzc.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.R;
import cn.bs.zjzc.dialog.MyDialog;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.util.SPUtils;
import cn.bs.zjzc.util.ScreenUtils;
import okhttp3.Call;

/**
 * 版本更新
 */
public class AvAppUpdate extends Activity {

    private Context mContext = this;
    private AppVersionInfo mVersionInfo;
    private NumberProgressBar pBar;
    private TextView function;
    private TextView optimize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //点击区域外不消失
        setFinishOnTouchOutside(false);

        setContentView(R.layout.av_app_update);

        Intent intent = getIntent();
        mVersionInfo = (AppVersionInfo) intent.getSerializableExtra("AppVersionInfo");

        initView();

        setViewData(mVersionInfo);
    }

    public void clickBTN(View v) {
        switch (v.getId()) {
            case R.id.cancel://以后再说
                OkHttpUtils.getInstance().getOkHttpClient().dispatcher().cancelAll();
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.ok://现在升级
                File file = new File(getExternalFilesDir(AllConsts.app.cacheDirName), mVersionInfo.data.name);
                if (file.exists()) {
                    installAPK();
                } else {
                    pBar.setVisibility(View.VISIBLE);
                    downFile();
                }
                break;
        }
    }

    /**
     * 安装apk
     */
    private void installAPK() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(getExternalFilesDir(AllConsts.app.cacheDirName), mVersionInfo.data.name)),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    private void initView() {
        pBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        function = (TextView) findViewById(R.id.addFunction);
        optimize = (TextView) findViewById(R.id.optimize);
        pBar = ((NumberProgressBar) findViewById(R.id.number_progress_bar));
    }


    private void setViewData(AppVersionInfo mVersionInfo) {
        int verSionCode = (int) Double.parseDouble(mVersionInfo.data.num);
        SPUtils.put(mContext, "versionCode", verSionCode);
        String[] functionStr = getStringArray(mVersionInfo.data.capability);
        String[] optimizationStr = getStringArray(mVersionInfo.data.optimization);
        if (functionStr == null) {
            function.setText("- 无");  //新功能
        } else {
            String strTemp = "";
            for (int i = 1; i < functionStr.length; i++) {
                strTemp = strTemp + "- " + functionStr[i];
            }
            function.setText(strTemp);  //新功能
        }
        if (optimizationStr == null) {
            optimize.setText("- 无");  //优化
        } else {
            String strTemp = "";
            for (int i = 1; i < optimizationStr.length; i++) {
                strTemp = strTemp + "- " + optimizationStr[i];
            }
            optimize.setText(strTemp);  //优化
        }
    }

    /**
     * 下载apk
     */
    private void downFile() {
        OkHttpUtils.get().url(RequestUrl.getImgPath(mVersionInfo.data.url)).tag(this).build()
                .execute(new FileCallBack(getExternalFilesDir(AllConsts.app.cacheDirName).getAbsolutePath(),
                        mVersionInfo.data.name) {
                    @Override
                    public void inProgress(float progress, long total) {
                        pBar.setMax(100);
                        pBar.setProgress((int) (progress * 100));
                    }

                    @Override
                    public void onError(Call call, Exception e) {
                    }

                    @Override
                    public void onResponse(File response) {
                        installAPK();
                    }
                });
    }

    private String[] getStringArray(String str) {
        if (TextUtils.isEmpty(str)) return null;
        String[] strArray = str.split("-");
        return strArray;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
