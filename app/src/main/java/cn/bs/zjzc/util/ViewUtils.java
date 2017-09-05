package cn.bs.zjzc.util;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;

/**
 * Created by Ming on 2016/6/1.
 */
public class ViewUtils {

    /**
     * 隐藏控件
     *
     * @param views
     */
    public static void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 显示控件
     *
     * @param views
     */
    public static void showViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
            ((View) view.getParent()).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏所有子控件
     */
    public static void hideAll(ViewGroup parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            parent.getChildAt(i).setVisibility(View.GONE);
        }
    }

    /**
     * 显示所有子控件)
     */
    public static void showAll(ViewGroup parent) {
        parent.setVisibility(View.VISIBLE);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            parent.getChildAt(i).setVisibility(View.VISIBLE);
        }
    }

    public static void modifyListConfig(ListView mListView) {
        //去掉原生的分割线
        Drawable translateDrawable = new ColorDrawable(Color.TRANSPARENT);
        mListView.setDivider(translateDrawable);
        mListView.setDividerHeight(0);

        //去掉list默认的点击效果
        mListView.setSelector(translateDrawable);

        //去掉本身的缓存颜色，防止拖动的时候产生黑色阴影
        mListView.setCacheColorHint(0);

        //去掉滚动条
        mListView.setVerticalScrollBarEnabled(false);
    }
}
