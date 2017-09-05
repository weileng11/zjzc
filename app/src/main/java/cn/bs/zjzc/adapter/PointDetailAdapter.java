package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.PointDetailResponse;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/7/11.
 */
public class PointDetailAdapter extends BaseNormalAdapter<PointDetailResponse.DataBean.ListBean> {
    public PointDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_point_detail_list, parent, false);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.subject);
            holder.tvTime = (TextView) convertView.findViewById(R.id.time);
            holder.tvPoint = (TextView) convertView.findViewById(R.id.point);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PointDetailResponse.DataBean.ListBean listBean = getData().get(position);
        holder.tvSubject.setText(listBean.remark);
        holder.tvTime.setText(TimeFormatUtils.formatTimeStr(listBean.change_time,"yyyy-MM-dd HH:mm"));
        holder.tvPoint.setText(listBean.change_points);
        return convertView;
    }

    class ViewHolder {
        TextView tvSubject;
        TextView tvTime;
        TextView tvPoint;
    }
}
