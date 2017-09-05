package cn.bs.zjzc.div;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import cn.bs.zjzc.R;

/**
 * Created by Ming on 2016/5/16.
 */
public class NormalOptionView extends AutoFrameLayout {

    private TextView mTitle;//左边标题
    private AutoFrameLayout mRightContainer;//右边内容的容器,用来放非文本的内容,如图片等
    private TextView mContent;//右边文字内容
    private ImageView mRightIcon;//右边的提示图标
    private AutoRelativeLayout mRoot;
    private int defaultTextColor = 0xffa8a8a8;//默认文本颜色

    public NormalOptionView(Context context) {
        this(context, null);
    }

    public NormalOptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NormalOptionView);

        String title = typedArray.getString(R.styleable.NormalOptionView_item_title);
        String content = typedArray.getString(R.styleable.NormalOptionView_item_content);

        int titleLeftDrawableResId = typedArray.getResourceId(R.styleable.NormalOptionView_title_left_drawable, -1);
        //默认右边提示图标为向右箭头
        int rightIconResId = typedArray.getResourceId(R.styleable.NormalOptionView_item_right_icon, R.mipmap.zjzc_personal_info_right_arrow);

        int titleTextColor = typedArray.getColor(R.styleable.NormalOptionView_title_text_color, defaultTextColor);
        int contentTextColor = typedArray.getColor(R.styleable.NormalOptionView_content_text_color, defaultTextColor);

        boolean arrowEnable = typedArray.getBoolean(R.styleable.NormalOptionView_item_arrow_enable, true);
        typedArray.recycle();

        setTitle(title);
        setContent(content);

        mRightIcon.setImageResource(rightIconResId);
        if (titleLeftDrawableResId > 0) {
            mTitle.setCompoundDrawablesWithIntrinsicBounds(titleLeftDrawableResId, 0, 0, 0);
            mTitle.setCompoundDrawablePadding(20);
        }
        if (titleTextColor != defaultTextColor) {
            mTitle.setTextColor(titleTextColor);
        }
        if (contentTextColor != defaultTextColor) {
            mContent.setTextColor(contentTextColor);
        }
        if (arrowEnable) {
            showArrow();
        } else {
            hideArrow();
        }

    }

    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_normal_option, this);
        this.setBackgroundResource(R.drawable.pressed_background);
        mRoot = (AutoRelativeLayout) this.getChildAt(0);

        mTitle = (TextView) mRoot.getChildAt(0);
        mRightIcon = (ImageView) mRoot.getChildAt(2);

        mRightContainer = (AutoFrameLayout) mRoot.getChildAt(1);
        mContent = (TextView) mRightContainer.getChildAt(0);
    }

    /**
     * 显示右边箭头
     */
    public void showArrow() {
        mRightIcon.setVisibility(VISIBLE);
    }

    /**
     * 隐藏右边箭头
     */
    public void hideArrow() {
        mRightIcon.setVisibility(GONE);
    }

    /**
     * 设置右边箭点击事件
     *
     * @param resId
     */
    public void setRightIconResId(int resId) {
        mRightIcon.setImageResource(resId);
    }

    /**
     * 设置右边箭点击事件
     *
     * @param listener
     */
    public void setRightClickListenr(OnClickListener listener) {
        mRightIcon.setOnClickListener(listener);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        }
    }

    /**
     * 设置内容
     *
     * @param content
     */
    public void setContent(String content) {
        if (!TextUtils.isEmpty(content)) {
            mContent.setText(content);
        }

    }

    /**
     * 获取内容
     *
     * @return
     */
    public String getContent() {
        return mContent.getText().toString();
    }

    /**
     * 右边内容添加View
     *
     * @param view
     */
    public void setRightView(View view) {
        if (view != null) {
            mRightContainer.removeAllViews();
            mRightContainer.addView(view);
        }
    }
}
