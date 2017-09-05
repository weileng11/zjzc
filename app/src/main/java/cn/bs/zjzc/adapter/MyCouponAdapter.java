package cn.bs.zjzc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.MyCoupon;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Administrator on 2016/7/14.
 */
public class MyCouponAdapter extends BaseNormalAdapter<MyCoupon.DataBean.ListBean> {

    private List<MyCoupon.DataBean.ListBean> datas;
    private Context mContext;
    private String mType;

    public MyCouponAdapter(Context context, List<MyCoupon.DataBean.ListBean> datas) {
        super(context, datas);
        this.mContext = context;
        this.datas = datas;
    }

    public MyCouponAdapter(Context context) {
        super(context);
        this.mContext = context;
    }

    public void setCouponType(String type) {
        this.mType = type;
    }


    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_my_coupon_list, parent, false);
            viewHolder.topTag = (ImageView) convertView.findViewById(R.id.topTag);
            viewHolder.tagLeft = (TextView) convertView.findViewById(R.id.tagleft);
            viewHolder.tagRight = (TextView) convertView.findViewById(R.id.tagRight);
            viewHolder.discount = (TextView) convertView.findViewById(R.id.discount);
            viewHolder.coupon_name = (TextView) convertView.findViewById(R.id.coupon_name);
            viewHolder.max_discount = (TextView) convertView.findViewById(R.id.max_discount);
            viewHolder.deadline = (TextView) convertView.findViewById(R.id.deadline);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyCoupon.DataBean.ListBean listBean = mDatas.get(position);
//        优惠券类型（1 折扣券，2 优惠券）
        viewHolder.coupon_name.setText(listBean.name);
        viewHolder.deadline.setText("·有效期至 " + TimeFormatUtils.formatTimeStr(listBean.end_time, "yyyy-MM-dd"));

        if (listBean.type.equals("1")) { //1 折扣券
            viewHolder.discount.setText(listBean.discount);
            viewHolder.tagRight.setVisibility(View.VISIBLE);
            viewHolder.max_discount.setText("·最高抵扣" + listBean.max_money + "元");
        } else { //优惠券
            viewHolder.discount.setText(listBean.discount);
            viewHolder.tagLeft.setVisibility(View.VISIBLE);
            viewHolder.max_discount.setText("·使用比例 " + listBean.proportion + "%");
        }

        if (mType.equals("2") || listBean.state.equals("3")) {  //过期
            viewHolder.topTag.setBackgroundResource(R.mipmap.zjxc_coupon_usee_bg);
            viewHolder.tagLeft.setTextColor(Color.parseColor("#999999"));
            viewHolder.tagRight.setTextColor(Color.parseColor("#999999"));
            viewHolder.discount.setTextColor(Color.parseColor("#999999"));
        } else {
            viewHolder.topTag.setBackgroundResource(R.mipmap.zjxc_coupon_unuse_bg);
            viewHolder.tagLeft.setTextColor(Color.parseColor("#f55404"));
            viewHolder.tagRight.setTextColor(Color.parseColor("#f55404"));
            viewHolder.discount.setTextColor(Color.parseColor("#f55404"));
        }

        return convertView;
    }

    class ViewHolder {
        private ImageView topTag;
        private TextView discount;
        private TextView coupon_name;
        private TextView max_discount;
        private TextView deadline;
        private TextView tagLeft;
        private TextView tagRight;
    }
}
