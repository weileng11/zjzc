package cn.bs.zjzc.div;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.popupwindow.NormalPopuWindow;

/**
 * Created by Ming on 2016/5/24.
 */
public class NormalPopuMenu extends AutoFrameLayout {

    private NormalPopuWindow popuWindow;
    private ListView mMenuList;
    private ArrayAdapter<String> adapter;
    private AutoLinearLayout mHeaderLayout;
    private TextView mTitleTxt;

    public NormalPopuMenu(Context context) {
        this(context, null);
    }

    public NormalPopuMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        popuWindow = new NormalPopuWindow(this, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        initViews();
    }

    private void initViews() {
        this.setBackgroundColor(0x66000000);
        mMenuList = new ListView(getContext());
        mMenuList.setDivider(null);
        mMenuList.setVerticalScrollBarEnabled(false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //默认弹窗位置在底部
        params.gravity = Gravity.BOTTOM;
        this.addView(mMenuList, params);

        mMenuList.setDividerHeight(1);
        mMenuList.setBackgroundColor(getResources().getColor(R.color.white));
        adapter = new ArrayAdapter<>(getContext(), R.layout.item_popu_menu_list, R.id.popu_menu_item);
//        mMenuList.setAdapter(adapter);
    }

    /**
     * 设置点击空白popuWindow空白处时是否关闭PopuWindow
     *
     * @param isCancelable
     */
    public void setTouchCancelable(boolean isCancelable) {
        if (isCancelable) {
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popuWindow.dismiss();
                    return false;
                }
            });
        } else {
            this.setOnTouchListener(null);
        }
    }

    /**
     * 设置菜单项目
     *
     * @param items
     * @return
     */
    public void setMenuItems(String... items) {
        adapter.clear();
        adapter.addAll(items);
    }

    /**
     * 设置菜单项目
     *
     * @param items
     * @return
     */
    public void setMenuItems(List<String> items) {
        adapter.clear();
        adapter.addAll(items);
    }

    /**
     * 添加菜单项目
     *
     * @param items
     * @return
     */
    public void addMenuItems(String... items) {
        adapter.addAll(items);
    }

    /**
     * 添加菜单项目
     *
     * @param items
     * @return
     */
    public void addMenuItems(List<String> items) {
        adapter.addAll(items);
    }

    /**
     * 设置菜单选项的点击事件
     *
     * @param l
     */
    public void setOnMenuItemClickListener(AdapterView.OnItemClickListener l) {
        mMenuList.setOnItemClickListener(l);
    }

    /**
     * 设置弹窗位置
     *
     * @param gravity
     * @return
     */
    public void setMenuGravity(int gravity) {
        LayoutParams params = (LayoutParams) mMenuList.getLayoutParams();
        params.gravity = gravity;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public void setTitle(String title) {
        if (mHeaderLayout == null) {
            mHeaderLayout = (AutoLinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_popu_menu_header, null);
            mMenuList.addHeaderView(mHeaderLayout);
            mTitleTxt = (TextView) mHeaderLayout.getChildAt(0);
            mTitleTxt.setTextColor(getResources().getColor(R.color.zjzc_orange_light));
        }
        mTitleTxt.setText(title);
        mMenuList.setAdapter(adapter);
    }

    /**
     * 弹出通用选择菜单窗口
     *
     * @param parent
     * @param title  标题
     * @param items  菜单选项内容
     * @param l      菜单选项点击监听
     */
    public void show(View parent, String title, List<String> items, AdapterView.OnItemClickListener l) {
        setTitle(title);
        show(parent, items, l);
    }

    public void show(View parent, String title, String[] items, AdapterView.OnItemClickListener l) {
        setTitle(title);
        show(parent, items, l);
    }

    public void show(View parent, List<String> items, AdapterView.OnItemClickListener l) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        setMenuItems(items);
        setOnMenuItemClickListener(l);
    }

    public void show(View parent, String[] items, AdapterView.OnItemClickListener l) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        setMenuItems(items);
        setOnMenuItemClickListener(l);
    }

    public void show() {
        showAtLocation(this, Gravity.BOTTOM, 0, 0);
    }

    public void showAsDropDown(View anchor) {
        popuWindow.showAsDropDown(anchor);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff) {
        popuWindow.showAsDropDown(anchor, xoff, yoff);
    }

    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        setMenuGravity(gravity);
        popuWindow.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    public void showAtLocation(View parent, int gravity, int x, int y) {
        setMenuGravity(gravity);
        popuWindow.showAtLocation(parent, gravity, x, y);
    }

    public void dismiss() {
        popuWindow.dismiss();
    }


}
