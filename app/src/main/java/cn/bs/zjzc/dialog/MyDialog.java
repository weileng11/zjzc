package cn.bs.zjzc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;

import cn.bs.zjzc.R;
import cn.bs.zjzc.util.DensityUtils;

/**
 * Created by Ming on 2016/7/2.
 */
public class MyDialog extends Dialog {
    //标题
    private TextView mTvTitle;
    //内容
    private TextView mTvContent;
    //左边按钮
    private TextView mBtnLeft;
    //右边按钮
    private TextView mBtnRight;
    //存放内容的布局,默认放的是mTvContent
    private AutoFrameLayout mContentFrame;

    public MyDialog(Context context) {
        //设置自定义style,透明背景,没有原生标题
        super(context, R.style.my_dialog_style);
        init();
    }

    private void init() {
        //默认设置不能取消
        this.setCancelable(false);
        //引入自定义布局
        this.setContentView(R.layout.dialog_normal);
        //获取dialog窗口的参数
        WindowManager.LayoutParams attr = getWindow().getAttributes();
        //设置dialog的宽度
        attr.width = getContext().getResources().getDisplayMetrics().widthPixels - DensityUtils.dp2px(getContext(), 50);
        mTvTitle = ((TextView) findViewById(R.id.title));
        mTvContent = ((TextView) findViewById(R.id.content));
        mBtnLeft = ((TextView) findViewById(R.id.left_button));
        mBtnRight = ((TextView) findViewById(R.id.right_button));
        mContentFrame = ((AutoFrameLayout) findViewById(R.id.content_frame));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setText(title);
        }
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            mTvContent.setText(content);
        }
    }

    /**
     * 自定义contentView
     *
     * @param contentView
     */
    public void setCustomContentView(View contentView) {
        if (contentView != null) {
            mContentFrame.removeAllViews();
            mContentFrame.addView(contentView);
        }
    }

    /**
     * 设置左边按钮事件和标题,点击会关闭dialog
     *
     * @param name
     * @param listener
     */
    public void setNegativeButton(String name, final OnClickListener listener) {
        mBtnLeft.setVisibility(View.VISIBLE);
        if (name != null) {
            mBtnLeft.setText(name);
        }

        mBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dismiss();
            }
        });
    }

    /**
     * 设置右边按钮事件和标题,点击不会关闭dialog
     *
     * @param name
     * @param listener
     */
    public void setPositiveButton(String name, final OnClickListener listener) {
        mBtnRight.setVisibility(View.VISIBLE);
        if (name != null) {
            mBtnRight.setText(name);
        }

        mBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
            }
        });
    }

    public interface OnClickListener {
        void onClick(View v);
    }

}
