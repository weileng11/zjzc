package cn.bs.zjzc.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 这个实现了下拉刷新和上拉加载更多的功能
 * 
 * @author Li Hong
 * @since 2013-7-29
 * @param <T>
 */
public abstract class PullToRefreshBase4SwipeMenu<T extends View> extends
		PullToRefreshBase<T> {

	/** 上一次移动的点 */
	private float mLastMotionX = -1;

	public PullToRefreshBase4SwipeMenu(Context context) {
		super(context);
	}

	public PullToRefreshBase4SwipeMenu(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public PullToRefreshBase4SwipeMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		 switch (event.getAction()) {
	        case MotionEvent.ACTION_DOWN:
	            mLastMotionX = event.getX();
	            break;
	            
	        case MotionEvent.ACTION_MOVE:
	            final float deltaX = event.getX() - mLastMotionX;
	            final float absDiff = Math.abs(deltaX);
	            // 这里有三个条件：
	            // 1，位移差大于mTouchSlop，这是为了防止快速拖动引发刷新
	            // 2，isPullRefreshing()，如果当前正在下拉刷新的话，是允许向上滑动，并把刷新的HeaderView挤上去
	            // 3，isPullLoading()，理由与第2条相同
	            if (absDiff > mTouchSlop)  {
	               return false;
	            }
	            break; 
	            
	        default:
	            break;
	        }
		 
		return super.onInterceptTouchEvent(event);
	}

}
