package cn.bs.zjzc.popupwindow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import cn.bs.zjzc.R;


public class NormalPopuWindow extends PopupWindow {

    Animation in;

    public NormalPopuWindow() {
        super();
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public NormalPopuWindow(Context context, AttributeSet attrs,
                            int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public NormalPopuWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public NormalPopuWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NormalPopuWindow(Context context) {
        super(context);
        init();
    }

    public NormalPopuWindow(int width, int height) {
        super(width, height);
        init();
    }

    public NormalPopuWindow(View contentView, int width, int height,
                            boolean focusable) {
        super(contentView, width, height, focusable);
        init();
    }

    public NormalPopuWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        init();
    }

    public NormalPopuWindow(View contentView) {
        super(contentView);
        init();
    }

    private void init() {
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        update();

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); // 在显示popupwindow之后调用，否则输入法会在窗口底层
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @TargetApi(19)
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        animation4Show(parent.getContext());
    }

    private void animation4Show(Context context) {
        if (in == null) {
            in = AnimationUtils.loadAnimation(context,android.R.anim.fade_in);
        }
        getContentView().startAnimation(in);
    }
}