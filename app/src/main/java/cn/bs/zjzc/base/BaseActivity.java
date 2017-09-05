package cn.bs.zjzc.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.T;
import okhttp3.OkHttpClient;

/**
 * Created by Ming on 2016/5/24.
 */
public class BaseActivity extends AppCompatActivity {
    protected static final String TAG = "MING";

    protected View loadingView;
    private TextView loadingMsg;
    private View closeLoading;
    private static List<Activity> activities = new LinkedList<>();

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
    }

    ProgressDialog dialog;

    public void showLoading() {
        showLoading("正在加载,请稍后...");
    }

    public void showLoading(String message) {
        // loadingMsg.setText(message);
        // loadingView.setVisibility(View.VISIBLE);
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setCancelable(false);
        }
        dialog.setMessage(message);
        dialog.show();
    }

    public void hideLoading() {
        // loadingView.setVisibility(View.GONE);
        dialog.dismiss();
    }

    /**
     * 销毁栈顶的activity
     *
     * @param number 销毁个数
     */
    protected void finishTop(int number) {
        for (int i = 0; i < number; i++) {
            activities.get(activities.size() - 1).finish();
        }
    }

    /**
     * 销毁全部的activity
     */

    protected void finishAll() {
        for (int i = 0; i < activities.size(); i++) {
            activities.get(i).finish();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }

    public void showMsg(String msg) {
        T.showShort(this, msg);
    }

    public Context getContext() {
        return getApplicationContext();
    }

    /**
     * 是否输入为空
     *
     * @param editText
     * @return
     */
    public boolean isInputEmpty(EditText editText) {
        if (TextUtils.isEmpty(editText.getText())) {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            return true;
        }
        return false;
    }

    /**
     * 是否输入正确手机号码
     *
     * @param editText
     * @return
     */
    public boolean isInputMobileNO(EditText editText) {
        if (DensityUtils.isMobileNO(editText.getText().toString())) {
            return true;
        } else {
            editText.setFocusable(true);
            editText.setFocusableInTouchMode(true);
            editText.requestFocus();
            return false;
        }
    }
}