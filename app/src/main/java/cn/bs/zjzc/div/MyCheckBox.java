package cn.bs.zjzc.div;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import cn.bs.zjzc.R;

/**
 * Created by Ming on 2016/5/31.
 */
public class MyCheckBox extends CheckBox {
    private String checkedText;
    private String uncheckedText;

    public MyCheckBox(Context context) {
        this(context, null);
    }

    public MyCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyCheckBox);

        // 设置自定义提示文字,默认为"开","关"
        checkedText = ta.getString(R.styleable.MyCheckBox_checked_text);
        uncheckedText = ta.getString(R.styleable.MyCheckBox_unchecked_text);
        ta.recycle();
//        checkText();
    }

    @Override
    public void toggle() {
        super.toggle();
//        checkText();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置文字位置
        float paddingLeft = (getMeasuredWidth() * 0.632f - getPaint().measureText(getText().toString())) / 2;
        float paddingRight = (getMeasuredWidth() * 0.632f - getPaint().measureText(getText().toString())) / 2;
        setPadding((int) paddingLeft, 0, (int) paddingRight, 0);
    }

    private void checkText() {
        if (isChecked()) {
            if (!TextUtils.isEmpty(checkedText)) {
                setText(checkedText);
            } else {
                setText("开");
            }
            //打开状态文字在左边
            setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else {
            if (!TextUtils.isEmpty(uncheckedText)) {
                setText(uncheckedText);
            } else {
                setText("关");
            }
            //关闭状态文字在右边
            setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
    }
}
