package cn.bs.zjzc.div;

import android.content.Context;
import android.graphics.Canvas;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.bs.zjzc.R;

/**
 * Created by Ming on 2016/7/12.
 */
public class ExpandableTextView extends TextView {
    private boolean isExpand = true;
    private String collapseStr = "收起";
    private String ExpandStr = "... 展开";

    public ExpandableTextView(Context context) {
        this(context, null);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getLineCount() > 2) {
            String source = getText() + collapseStr;
            SpannableString ss = new SpannableString(source);
            ss.setSpan(new Clickable(), source.lastIndexOf(collapseStr), source.length(), Spanned.SPAN_MARK_MARK);
            setText(ss);
        }
        super.onDraw(canvas);
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        invalidate();
    }

    class Clickable extends ClickableSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
            ds.setColor(getContext().getResources().getColor(R.color.zjzc_blue));
        }

        @Override
        public void onClick(View widget) {
            isExpand = !isExpand;
            Log.i("onClick", "onClick: " + isExpand);
            if (isExpand) {
                String source = getText() + collapseStr;
                SpannableString ss = new SpannableString(source);
                ss.setSpan(new Clickable(), source.lastIndexOf(collapseStr), source.length(), Spanned.SPAN_MARK_MARK);
                setText(ss);
                setLines(2);
            } else {
                int lineEnd = getLayout().getLineEnd(1) - ExpandStr.length();
                String source = getText().toString().substring(0, lineEnd) + ExpandStr;
                SpannableString ss = new SpannableString(source);
                ss.setSpan(new Clickable(), source.lastIndexOf(ExpandStr), source.length(), Spanned.SPAN_MARK_MARK);
                setText(ss);
                setLines(Integer.MAX_VALUE);
            }
        }
    }
}
