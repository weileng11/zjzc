package cn.bs.zjzc.items;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuItemBase;

/**
 * 
 * @author pt-xuejj
 */
public class SwipeMenuItem implements SwipeMenuItemBase{

	private int id;
	private Context mContext;
	private String title;
	private Drawable icon;
	private Drawable background;
	private int titleColor;
	private int titleSize;
	private int width;

	public SwipeMenuItem(Context context) {
		mContext = context;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTitleColor() {
		return titleColor;
	}

	public int getTitleSize() {
		return titleSize;
	}

	public void setTitleSize(int titleSize) {
		this.titleSize = titleSize;
	}

	public void setTitleColor(int titleColor) {
		this.titleColor = titleColor;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle(int resId) {
		setTitle(mContext.getString(resId));
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public void setIcon(int resId) {
		this.icon = mContext.getResources().getDrawable(resId);
	}

	public Drawable getBackground() {
		return background;
	}

	public void setBackground(Drawable background) {
		this.background = background;
	}

	public void setBackground(int resId) {
		this.background = mContext.getResources().getDrawable(resId);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void createMenuItem(ViewGroup menuParent){
		if (getIcon() != null) {
			menuParent.addView(createIcon(),new LayoutParams(getWidth(), LayoutParams.MATCH_PARENT));
		}
		if (!TextUtils.isEmpty(getTitle())) {
			menuParent.addView(createTitle(),new LayoutParams(getWidth(), LayoutParams.MATCH_PARENT));
		}
	}
	
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private ImageView createIcon() {
		ImageView iv = new ImageView(mContext);
		iv.setImageDrawable(getIcon());
		iv.setBackground(getBackground());
		return iv;
	}

	private TextView createTitle() {
		TextView tv = new TextView(mContext);
		tv.setText(getTitle());
		tv.setGravity(Gravity.CENTER);
		tv.setTextSize(getTitleSize());
		tv.setTextColor(getTitleColor());
		
//		tv.setCompoundDrawablesWithIntrinsicBounds(null, mContext.getResources().getDrawable(R.drawable.ic_launcher), null, null);
		
		tv.setBackgroundDrawable(getBackground());
		return tv;
	}
}