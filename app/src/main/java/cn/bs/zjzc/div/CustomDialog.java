package cn.bs.zjzc.div;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import cn.bs.zjzc.R;

/**
 * 自定义弹窗
 */
public class CustomDialog extends Dialog {

    private FrameLayout layoutContainer;  //布局容器
    private TopBarView topBar;
    private Context mContext;

    public CustomDialog(Context context, View layout, int style) {

        super(context, style);

        mContext = context;
    }

    public CustomDialog(Context context, View layout, int width, int height,
                        int gravity, int style) {
        super(context, style);
        setContentView(layout);
        mContext = context;
        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();
        params.height = height;
        params.width = width;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    /**
     * @param context       上下文
     * @param title         弹窗标题
     * @param contentLayout 弹窗的内容布局
     * @param width         弹窗宽
     * @param height        弹窗高
     * @param gravity       弹窗的位置
     */
    public CustomDialog(Context context, String title, View contentLayout, int width, int height,int gravity) {
        super(context, R.style.dialog);
        setContentView(R.layout.custom_dialog);
        topBar = ((TopBarView) findViewById(R.id.top_bar));
        topBar.setTitle(title);
        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        layoutContainer = (FrameLayout) findViewById(R.id.contentLayout);
        if (contentLayout != null) {
            layoutContainer.addView(contentLayout);  //添加布局到弹窗的容器中
        }

        mContext = context;
        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();
        params.height = height;
        params.width = width;
        params.gravity = gravity;
        window.setAttributes(params);
    }
}
