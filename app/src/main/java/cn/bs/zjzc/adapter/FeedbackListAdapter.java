package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.FeedbackListResponse;
import cn.bs.zjzc.util.CommonUtil;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/6/14.
 */
public class FeedbackListAdapter extends BaseNormalAdapter<FeedbackListResponse.DataBean> {


    public FeedbackListAdapter(Context context) {
        super(context);
    }

    public FeedbackListAdapter(Context context, List<FeedbackListResponse.DataBean> datas) {
        super(context, datas);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feedback_list, parent, false);
            holder.tvDate = (TextView) convertView.findViewById(R.id.date);
            holder.tvContent = (TextView) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FeedbackListResponse.DataBean feedbackBean = getData().get(position);
        long mills = new Date().getTime() - Long.parseLong(feedbackBean.ctime);
        holder.tvDate.setText(TimeFormatUtils.formatTimeStr(feedbackBean.ctime, "yyyy年MM月dd日"));
        holder.tvContent.setText(feedbackBean.content);
        return convertView;
    }

    public class ViewHolder {
        private TextView tvDate;
        private TextView tvContent;
    }
}
