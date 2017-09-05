package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.EvaluationListResponse;
import cn.bs.zjzc.util.CommonUtil;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/7/5.
 */
public class MyEvaluationAdapter extends BaseNormalAdapter<EvaluationListResponse.DataBean.ListBean> {

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_evaluation_list, parent, false);
            holder.tvUser = (TextView) convertView.findViewById(R.id.user);
            holder.rbLevel = (RatingBar) convertView.findViewById(R.id.rating_bar);
            holder.tvTime = (TextView) convertView.findViewById(R.id.time);
            holder.tvContent = (TextView) convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EvaluationListResponse.DataBean.ListBean evaluationBean = getData().get(position);
        holder.tvUser.setText(evaluationBean.phone);
        holder.rbLevel.setRating(Float.parseFloat(evaluationBean.level));
        holder.tvTime.setText(TimeFormatUtils.formatTimeStr(evaluationBean.time, "yyyy-MM-dd"));
        holder.tvContent.setText(evaluationBean.content);
        return convertView;
    }

    public MyEvaluationAdapter(Context context) {
        super(context);
    }

    class ViewHolder {
        TextView tvUser;
        RatingBar rbLevel;
        TextView tvTime;
        TextView tvContent;
    }
}
