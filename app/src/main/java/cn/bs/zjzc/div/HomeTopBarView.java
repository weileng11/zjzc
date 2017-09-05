package cn.bs.zjzc.div;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageButton;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import cn.bs.zjzc.R;

/**
 * Created by Ming on 2016/5/16.
 */
public class HomeTopBarView extends AutoFrameLayout {

    private ImageButton mBtnMenu;
    private ImageButton mLogo;
    private ImageButton mBtnSearch;
    private ImageButton mBtnActivity;

    public HomeTopBarView(Context context) {
        this(context,null);
    }

    public HomeTopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HomeTopBarView);

        //默认搜索按钮可见
        boolean searchEnable = typedArray.getBoolean(R.styleable.HomeTopBarView_search_enable, true);
        //默认广告按钮可见
        boolean advertisementEnable = typedArray.getBoolean(R.styleable.HomeTopBarView_advertisement_enable, true);
        typedArray.recycle();

        if (searchEnable) {
            showSearchButton();
        } else {
            hideSearchButton();
        }

        if (advertisementEnable) {
            showAdvertisementButton();
        } else {
            hideAdvertisementButton();
        }
    }

    /**
     * 隐藏搜索按钮
     */
    public void hideSearchButton() {
        mBtnSearch.setVisibility(GONE);
    }

    /**
     * 显示搜索按钮
     */
    public void showSearchButton() {
        mBtnSearch.setVisibility(VISIBLE);
    }

    /**
     * 隐藏广告按钮
     */
    public void hideAdvertisementButton() {
        mBtnActivity.setVisibility(GONE);
    }

    /**
     * 显示广告按钮
     */
    public void showAdvertisementButton() {
        mBtnActivity.setVisibility(VISIBLE);
    }


    /**
     * 初始化子View
     */
    private void initViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.top_bar_main, this);
        AutoRelativeLayout root = (AutoRelativeLayout) this.getChildAt(0);

        mBtnMenu = (ImageButton) root.getChildAt(0);
        mLogo = (ImageButton) root.getChildAt(1);
        mBtnSearch = (ImageButton) root.getChildAt(2);
        mBtnActivity = (ImageButton) root.getChildAt(3);
    }

    /**
     * 设置菜单按钮监听事件
     *
     * @param listener
     */
    public void setMenuButtonClickListener(OnClickListener listener) {
        mBtnMenu.setOnClickListener(listener);
    }

    /**
     * 设置Logo按钮监听事件
     *
     * @param listener
     */
    public void setLogoButtonClickListener(OnClickListener listener) {
        Log.i("HomeTopBarView", "setLogoButtonClickListener: ");
        mLogo.setOnClickListener(listener);
    }

    /**
     * 设置搜索按钮监听事件
     *
     * @param listener
     */
    public void setSearchButtonClickListener(OnClickListener listener) {
        mBtnSearch.setOnClickListener(listener);
    }

    /**
     * 这是广告按钮监听事件
     *
     * @param listener
     */
    public void setActivityButtonClickListener(OnClickListener listener) {
        mBtnActivity.setOnClickListener(listener);
    }


}
