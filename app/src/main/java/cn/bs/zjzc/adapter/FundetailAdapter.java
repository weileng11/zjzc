package cn.bs.zjzc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.FundDetailResponse;
import cn.bs.zjzc.model.response.WithdrawDetailResponse;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/7/6.
 */
public class FundetailAdapter extends BaseNormalAdapter<FundDetailResponse.DataBean.ListBean> {
    public FundetailAdapter(Context context) {
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
        FundDetailResponse.DataBean.ListBean dataBean = getData().get(position);
        holder.tvSubject.setText("订单" + dataBean.order_num);
        holder.tvTime.setText(TimeFormatUtils.formatTimeStr(dataBean.time,"yyyy年MM月dd日 hh:mm:ss"));
        setMoney(holder.tvMoney, dataBean);
        return convertView;
    }

    private void setMoney(TextView tvMoney, FundDetailResponse.DataBean.ListBean dataBean) {
        // 	类型（1 收入，2 支出，3 奖励，4 惩罚）
        String type = dataBean.type;
        String money = dataBean.money;
        if (TextUtils.equals(type, "1")) {
            tvMoney.setText("+" + money);
            tvMoney.setTextColor(getContext().getResources().getColor(R.color.zjzc_orange_light));
            return;
        }

        if (TextUtils.equals(type, "2")) {
            tvMoney.setText("-" + money);
            tvMoney.setTextColor(getContext().getResources().getColor(R.color.zjzc_black));
            return;
        }

        if (TextUtils.equals(type, "3")) {
            tvMoney.setText("（奖励）+" + money);
            tvMoney.setTextColor(getContext().getResources().getColor(R.color.zjzc_orange_light));
            return;
        }

        if (TextUtils.equals(type, "4")) {
            tvMoney.setText("（扣罚）-" + money);
            tvMoney.setTextColor(Color.RED);
            return;
        }
    }


    class ViewHolder {
        private TextView tvSubject;
        private TextView tvTime;
        private TextView tvMoney;
    }
}
