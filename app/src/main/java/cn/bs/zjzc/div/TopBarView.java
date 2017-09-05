package cn.bs.zjzc.div;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import cn.bs.zjzc.R;


/**
 * Created by Ming on 2016/5/13.
 */
public class TopBarView extends AutoFrameLayout {

    private TextView mLeftTxt;
    private TextView mTitleTxt;
    private TextView mRightTxt;

    public TopBarView(Context context) {
        this(context, null);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBarView);

        String left = typedArray.getString(R.styleable.TopBarView_left_text);
        String title = typedArray.getString(R.styleable.TopBarView_title_text);
        String right = typedArray.getString(R.styleable.TopBarView_right_text);

        //左边的图片,默认设置返回图标
        int leftDrawableResId = typedArray.getResourceId(R.styleable.TopBarView_left_drawable, R.mipmap.zjzc_topbar_back);
        //标题的图片
        int titleDrawableResId = typedArray.getResourceId(R.styleable.TopBarView_title_drawable, -1);
        //右边的图片
        int rightDrawableResId = typedArray.getResourceId(R.styleable.TopBarView_right_drawable, -1);

        //右边菜单字体颜色
        int rightColor = typedArray.getColor(R.styleable.TopBarView_right_text_color, 0xFFA8A8A8);

        //默认显示左菜单
        boolean leftEnable = typedArray.getBoolean(R.styleable.TopBarView_left_enable, true);
        //默认显示返回图标
        boolean leftDrawableEnable = typedArray.getBoolean(R.styleable.TopBarView_left_drawable_enable, true);
        //默认显示标题
        boolean titleEnable = typedArray.getBoolean(R.styleable.TopBarView_title_enable, true);
        //默认隐藏右菜单
        boolean rightEnable = typedArray.getBoolean(R.styleable.TopBarView_right_enable, false);
        typedArray.recycle();

        setLeftText(left);
        setTitle(title);
        setRightText(right);


        mRightTxt.setTextColor(rightColor);

        if (leftDrawableEnable) {
            mLeftTxt.setCompoundDrawablesWithIntrinsicBounds(leftDrawableResId, 0, 0, 0);
            //默认返回
            back();
        }

        if (titleDrawableResId > 0) {
            mTitleTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, titleDrawableResId, 0);
            mTitleTxt.setCompoundDrawablePadding(20);
        }
        if (rightDrawableResId > 0) {
            mRightTxt.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightDrawableResId, 0);
        }

        if (leftEnable) {
            showLeft();
        } else {
            hideLeft();
        }

        if (titleEnable) {
            showTitle();
        } else {
            hideTitle();
        }

        if (rightEnable) {
            showRight();
        } else {
            hideRight();
        }

    }

    public View getTitleView() {
        return mTitleTxt;
    }

    /**
     * 返回事件
     */
    private void back() {
        mLeftTxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) (getContext())).finish();
                }
            }
        });
    }

    /**
     * 初始化子View
     */
    private void initViews() {
        this.setBackgroundColor(0xFFE5E5E5);
        LayoutInflater.from(getContext()).inflate(R.layout.top_bar, this);
        AutoRelativeLayout root = (AutoRelativeLayout) getChildAt(0);
        mLeftTxt = (TextView) root.getChildAt(0);
        mTitleTxt = (TextView) root.getChildAt(1);
        mRightTxt = (TextView) root.getChildAt(2);
    }

    /**
     * 设置左边文字描述
     *
     * @param left
     */
    public void setLeftText(String left) {
        if (!TextUtils.isEmpty(left)) {
            mLeftTxt.setText(left);
        }
    }

    /**
     * 设置中间标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitleTxt.setText(title);
        }
    }

    /**
     * 设置右边文字描述
     *
     * @param right
     */
    public void setRightText(String right) {
        if (!TextUtils.isEmpty(right)) {
            mRightTxt.setText(right);
        }
    }

    /**
     * 隐藏左菜单
     */
    public void hideLeft() {
        mLeftTxt.setVisibility(GONE);
    }

    /**
     * 显示左菜单
     */
    public void showLeft() {
        mLeftTxt.setVisibility(VISIBLE);
    }

    /**
     * 隐藏标题
     */
    public void hideTitle() {
        mTitleTxt.setVisibility(GONE);
    }

    /**
     * 显示标题
     */
    public void showTitle() {
        mTitleTxt.setVisibility(VISIBLE);
    }

    /**
     * 隐藏右菜单
     */
    public void hideRight() {
        mRightTxt.setVisibility(GONE);
    }

    /**
     * 显示右菜单
     */
    public void showRight() {
        mRightTxt.setVisibility(VISIBLE);
    }

    /**
     * 设置左边点击监听
     *
     * @param listener
     */
    public void setLeftClickListener(OnClickListener listener) {
        mLeftTxt.setOnClickListener(listener);
    }

    /**
     * 设置标题点击监听
     *
     * @param listener
     */
    public void setTitleClickListener(OnClickListener listener) {
        mTitleTxt.setOnClickListener(listener);
    }

    /**
     * 设置左边点击监听
     *
     * @param listener
     */
    public void setRightClickListener(OnClickListener listener) {
        mRightTxt.setOnClickListener(listener);
    }

}
