package cn.bs.zjzc.items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenuItemBase;

import cn.bs.zjzc.util.DensityUtils;


public class SwipeMenuBuilder{
	public static SwipeMenuItemBase createSwipeMenu(final int menuLayoutId,final int widthDip){
		return new SwipeMenuItemBase() {
			@Override
			public void createMenuItem(ViewGroup arg0) {
				View menuView = LayoutInflater.from(arg0.getContext()).inflate(menuLayoutId, null);
				final int width = DensityUtils.dp2px(arg0.getContext(), widthDip);
				arg0.addView(menuView, new ViewGroup.LayoutParams(width, ViewGroup.LayoutParams.MATCH_PARENT));
			}
		};
	}
}
