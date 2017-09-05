package cn.bs.zjzc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;

/**
 * Created by Ming on 2016/6/16.
 */
public class WithdrawDetailAdapter extends BaseNormalAdapter<WithdrawDetailResponse.DataBean.ListBean> {
    public WithdrawDetailAdapter(Context context) {
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

        WithdrawDetailResponse.DataBean.ListBean dataBean = getData().get(position);
        holder.tvSubject.setText(getState(dataBean.state));
        holder.tvTime.setText(dataBean.apply_time);

        holder.tvMoney.setTextColor(getContext().getResources().getColor(R.color.zjzc_black));
        holder.tvMoney.setText("-" + dataBean.money);
        return convertView;
    }

    private String getState(String state) {
        if (TextUtils.equals(state, "1")) {
            return "待处理";
        }

        if (TextUtils.equals(state, "2")) {
            return "提现成功";
        }

        if (TextUtils.equals(state, "3")) {
            return "提现失败";
        }
        return "";
    }

    class ViewHolder {
        private TextView tvSubject;
        private TextView tvTime;
        private TextView tvMoney;
    }
}
