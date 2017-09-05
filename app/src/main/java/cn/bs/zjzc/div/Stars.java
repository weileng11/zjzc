package cn.bs.zjzc.div;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;

import cn.bs.zjzc.R;

public class Stars extends AutoFrameLayout {

    float stars = -1;
    private AutoLinearLayout mRoot;
    private TextView mRating;

    public float getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
        refresh();
    }

    public Stars(Context context) {
        this(context, null);
    }

    public Stars(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public Stars(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initStars();
        refresh();
    }


    private void initStars() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_star, this);
        mRoot = (AutoLinearLayout) getChildAt(0);
        mRating = (TextView) mRoot.getChildAt(5);
    }

    private void refresh() {
        if (stars < 0) stars = 0;
        if (stars > 5) stars = 5;
        int allCount = mRoot.getChildCount() - 1;

        mRating.setText(stars + "");
        if (stars < 1) {
            mRating.setVisibility(GONE);
        } else {
            mRating.setVisibility(VISIBLE);
        }

        for (int i = 0; i < allCount; i++) {
            int visibility = View.GONE;
            if (i < ((int) stars)) {
                visibility = View.VISIBLE;
            }
            mRoot.getChildAt(i).setVisibility(visibility);
        }
    }
}