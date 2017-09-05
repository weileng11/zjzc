package cn.bs.zjzc.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.zhy.http.okhttp.OkHttpUtils;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.div.ExpandableTextView;
import cn.bs.zjzc.model.response.FAQListResponse;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;
import okhttp3.OkHttpClient;

/**
 * Created by Ming on 2016/7/12.
 */
public class FAQSearchAdapter extends BaseNormalAdapter<FAQListResponse.DataBean.ListBean> {
    String showStr = "... 展开";
    String hideStr = "  收起";

    public FAQSearchAdapter(Context context) {
        super(context);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_faq_search, parent, false);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FAQListResponse.DataBean.ListBean listBean = getData().get(position);
        holder.title.setText(listBean.question);
        holder.content.setText(listBean.answer);
        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView content;
    }
}
