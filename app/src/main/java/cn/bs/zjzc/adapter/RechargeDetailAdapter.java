package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.RechargeDetailResponse;

/**
 * Created by Ming on 2016/6/16.
 */
public class RechargeDetailAdapter extends BaseNormalAdapter<RechargeDetailResponse.DataBean.ListBean> {
    public RechargeDetailAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_recharge_detail, parent, false);
            holder.tvSubject = (TextView) convertView.findViewById(R.id.subject);
            holder.tvTime = (TextView) convertView.findViewById(R.id.time);
            holder.tvMoney = (TextView) convertView.findViewById(R.id.money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RechargeDetailResponse.DataBean.ListBean data = getData().get(position);
        holder.tvSubject.setText(data.type);
        holder.tvTime.setText(data.charge_time);
        holder.tvMoney.setText("+" + data.money);
        return convertView;
    }

    class ViewHolder {
        private TextView tvSubject;
        private TextView tvTime;
        private TextView tvMoney;
    }
}
