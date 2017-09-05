package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import cn.bs.zjzc.AllConsts;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.dialog.MyDialog;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.model.response.AppVersionInfo;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.HomePresenter;
import cn.bs.zjzc.presenter.SettingPresenter;
import cn.bs.zjzc.ui.view.ISettingView;
import cn.bs.zjzc.util.SPUtils;
import cn.bs.zjzc.util.T;
import okhttp3.Call;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvSetting extends BaseActivity implements View.OnClickListener, ISettingView {

    private Context mContext = this;
    private SettingPresenter mSettingPresenter;

    private NormalOptionView modifyPassword;//修改密码
    private NormalOptionView oftenUseAddress;//常用地址
    private AutoRelativeLayout optionVoiceSwitch;
    private CheckBox voiceSwitch;//声音开关
    private AutoRelativeLayout optionVibrateSwitch;
    private CheckBox vibrateSwitch;//震动开关
    private NormalOptionView customService;//客户服务
    private NormalOptionView feedback;//反馈
    private NormalOptionView userRegulation;//用户守则
    private NormalOptionView versionUpgrade;//版本升级
    private NormalOptionView aboutUs;//关于我们
    private TextView logout;
    private String mVersionName;
    private int mVersionCode;
    private MyDialog updateDailog;
    private AppVersionInfo mVersionInfo;
    private NumberProgressBar pBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_setting);
        mSettingPresenter = new SettingPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        modifyPassword.setOnClickListener(this);
        oftenUseAddress.setOnClickListener(this);
        customService.setOnClickListener(this);
        feedback.setOnClickListener(this);
        userRegulation.setOnClickListener(this);
        versionUpgrade.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    private void initViews() {
        modifyPassword = ((NormalOptionView) findViewById(R.id.option_modify_password));
        oftenUseAddress = ((NormalOptionView) findViewById(R.id.option_set_often_use_address));
        optionVoiceSwitch = ((AutoRelativeLayout) findViewById(R.id.option_voice_switch));
        voiceSwitch = ((CheckBox) findViewById(R.id.voice_switch));
        optionVibrateSwitch = ((AutoRelativeLayout) findViewById(R.id.option_vibrator_switch));
        vibrateSwitch = ((CheckBox) findViewById(R.id.vibrator_switch));
        customService = ((NormalOptionView) findViewById(R.id.option_custom_service_and_help));
        feedback = ((NormalOptionView) findViewById(R.id.option_feedback_and_reply));
        userRegulation = ((NormalOptionView) findViewById(R.id.option_user_regulation));
        versionUpgrade = ((NormalOptionView) findViewById(R.id.option_version_upgrade));
        aboutUs = ((NormalOptionView) findViewById(R.id.option_about_us));
        logout = ((TextView) findViewById(R.id.btn_logout));
    }


    @Override
    public void onClick(View v) {
        String url = "";
        String tile = "";
        switch (v.getId()) {
            case R.id.option_modify_password:
                startActivity(new Intent(mContext, AvModifyPassword.class));
                break;
            case R.id.option_set_often_use_address:
                startActivity(new Intent(mContext, AvOftenUseAddress.class));
                break;
            case R.id.option_custom_service_and_help:
                startActivity(new Intent(mContext, AvServiceHelp.class));
                break;
            case R.id.option_feedback_and_reply:
                startActivity(new Intent(mContext, AvFeedbackList.class));
                break;
            case R.id.option_user_regulation:
//                startActivity(new Intent(mContext, AvUserRegulation.class));
                url = RequestUrl.getWebRequestPath(RequestUrl.WebPath.getUserRule);
                tile = "用户守则";
                goToWebView(url, tile);
                break;
            case R.id.option_version_upgrade:
                //检查版本
                checkVersion();
                break;
            case R.id.option_about_us:
                url = RequestUrl.getWebRequestPath(RequestUrl.WebPath.aboutMe);
                tile = "关于我们";
                goToWebView(url, tile);
                break;
            case R.id.btn_logout:
                final MyDialog dialog = new MyDialog(mContext);
                dialog.setTitle("提示");
                dialog.setContent("确定要退出登录吗?");
                dialog.setNegativeButton("取消", null);
                dialog.setPositiveButton("确定", new MyDialog.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSettingPresenter.logout();
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
        }
    }

    private void checkVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            mVersionName = packageInfo.versionName;
            mVersionCode = packageInfo.versionCode;
            //获取版本信息
            mSettingPresenter.getAppVersionInfo();

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void goToWebView(String url, String tile) {
        Intent intent = new Intent(mContext, AvCommdWebView.class);
        intent.putExtra("url", url);
        intent.putExtra("title", tile);
        startActivity(intent);
    }

    @Override
    public void logout() {
        startActivity(new Intent(mContext, AvLogin.class));
        finishAll();
    }

    @Override
    public void getVersionInfoSuccess(AppVersionInfo data) {
        this.mVersionInfo = data;
        if (updateDailog == null)
            updateDailog = new MyDialog(mContext);
        updateDailog.setTitle("版本升级");
        int verSionCode = Integer.parseInt(mVersionInfo.data.num);
        if (verSionCode > mVersionCode) {  //有新版本
            View appInfoView = View.inflate(mContext, R.layout.view_app_info, null);
            TextView function = (TextView) appInfoView.findViewById(R.id.addFunction);
            TextView optimize = (TextView) appInfoView.findViewById(R.id.optimize);
            pBar = ((NumberProgressBar) appInfoView.findViewById(R.id.number_progress_bar));
            String[] functionStr = getStringArray(data.data.capability);
            String[] optimizationStr = getStringArray(data.data.optimization);
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
            updateDailog.setCustomContentView(appInfoView);
            updateDailog.setNegativeButton("以后再说", new MyDialog.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OkHttpUtils.getInstance().getOkHttpClient().dispatcher().cancelAll();
                }
            });
            updateDailog.setPositiveButton("现在升级", new MyDialog.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pBar.setVisibility(View.VISIBLE);
                    downFile();
                }
            });
        } else {
            updateDailog.setContent("您使用的已经是最新版本");
            updateDailog.setPositiveButton("确定", new MyDialog.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (updateDailog.isShowing())
                        updateDailog.dismiss();
                }
            });
        }
        updateDailog.show();
    }

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
                        T.showShort(mContext, "下载安装包发生错误");
                        if (updateDailog.isShowing())
                            updateDailog.dismiss();
                    }

                    @Override
                    public void onResponse(File response) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setDataAndType(Uri.fromFile(new File(getExternalFilesDir(AllConsts.app.cacheDirName), mVersionInfo.data.name)),
                                "application/vnd.android.package-archive");
                        mContext.startActivity(intent);
                        if (updateDailog.isShowing())
                            updateDailog.dismiss();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    private String[] getStringArray(String str) {
        if (TextUtils.isEmpty(str)) return null;
        String[] strArray = str.split("-");
        return strArray;
    }
}
