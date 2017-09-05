package cn.bs.zjzc.div;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Ming on 2016/5/30.
 */
public class DashLine extends View {

    public DashLine(Context context) {
        this(context, null);
    }

    public DashLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }
}
