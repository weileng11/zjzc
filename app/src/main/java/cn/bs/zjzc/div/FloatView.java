package cn.bs.zjzc.div;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import cn.bs.zjzc.util.ScreenUtils;

/**
 * 悬浮按钮
 */
public class FloatView extends ImageView {

    private float downX;//记录手指按下屏幕时的X坐标
    private float downY;
    private int lastX;//记录移动过程中最后一次手指所在的位置的X坐标
    private int lastY;
    private int screenWidth;
    private int screenHeight;
    private onFloatViewClickListener listener;

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        screenWidth = ScreenUtils.getScreenWidth(context);
        screenHeight = ScreenUtils.getScreenHeight(context) - ScreenUtils.getStatusHeight(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                lastX = (int) event.getRawX();//获取触摸事件触摸位置的原始X坐标
                lastY = (int) event.getRawY();
                downX = event.getX();
                downY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;

                int l = getLeft() + dx;
                int b = getBottom() + dy;
                int r = getRight() + dx;
                int t = getTop() + dy;

                //检查边界
                if (l < 0) {
                    l = 0;
                    r = l + getWidth();
                }
                if (t < 0) {
                    t = 0;
                    b = t + getHeight();
                }
                if (r > screenWidth) {
                    r = screenWidth;
                    l = r - getWidth();
                }
                if (b > screenHeight) {
                    b = screenHeight;
                    t = b - getHeight();
                }
                layout(l, t, r, b);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                //如果手指按下屏幕时的位置与手指抬起时的位置相同,则视为发生了点击事件
                if (downX == event.getX() && downY == event.getY()) {
                    if (listener != null) {
                        listener.onFloatViewClick();
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 设置悬浮按钮的点击监听事件
     * @param l
     */
    public void setFloatViewClickListener(onFloatViewClickListener l) {
        listener = l;
    }

    public interface onFloatViewClickListener {
        void onFloatViewClick();
    }

}
