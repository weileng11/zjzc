package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.socket.response.OrderCompeitionResponse;
import cn.bs.zjzc.socket.response.PushOrderResponse;
import cn.bs.zjzc.util.ViewUtils;

/**
 * Created by Ming on 2016/7/14.
 */
public class OrderCompetitionAdapter extends BaseNormalAdapter<PushOrderResponse> {
    public OrderCompetitionAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order_competition_list, parent, false);
            initViews(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        PushOrderResponse orderBean = getData().get(position);
        setType(holder, orderBean);
        return convertView;
    }

    /**
     * 订单类型（1 外卖，2 代买，3 代办 , 4 车友，5 速递）
     *
     * @param holder
     * @param orderBean
     */
    private void setType(ViewHolder holder, PushOrderResponse orderBean) {
        int type = -1;
        try {
            type = Integer.parseInt(orderBean.type);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //1 外卖，5 速递
        if (type == 1 || type == 5) {
            //显示"送"字图标和距离
            ViewUtils.showViews(holder.deliveryIcon, holder.deliveryDistance);
            holder.takeIcon.setText("取");
            holder.takeIcon.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_green_circle, 0, 0, 0);
            //取货地址
            holder.takeAddress.setText(orderBean.take.add_address);
            //送货地址
            holder.deliveryAddress.setText(orderBean.receipt.add_address);

            //设置服务类型及其字体颜色和背景
            if (type == 1) {
                holder.type.setText("外卖服务");
                holder.type.setTextColor(0xffcf69ff);
                holder.type.setBackgroundResource(R.mipmap.zjzc_box_delivry);
            } else if (type == 5) {
                holder.type.setText("同城速递");
                holder.type.setTextColor(0xffe1d61f);
                holder.type.setBackgroundResource(R.mipmap.zjzc_box_city_express);
            }
            return;
        }

        //如果不是"外卖服务","同城速递"则隐藏"送"字图标和距离
        ViewUtils.hideViews(holder.deliveryIcon, holder.deliveryDistance);
        //隐藏了"送"字图标后把原来"取"字图标改成"送"字图标
        holder.takeIcon.setText("送");
        holder.takeIcon.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_red_circle, 0, 0, 0);
        holder.takeAddress.setText(orderBean.receipt.add_address);

        //2 代买
        if (type == 2) {
            holder.type.setText("代买服务");
            holder.deliveryAddress.setText("商品名称:" + orderBean.receipt.add_address);
            holder.type.setTextColor(0xff52a7ff);
            holder.type.setBackgroundResource(R.mipmap.zjzc_box_buy_service);
            return;
        }
        //3 代办 , 4 车友
        if (type == 3 || type == 4) {
            if (type == 3) {
                holder.type.setText("代办服务");
            } else if (type == 4) {
                holder.type.setText("车友之家");
            }
            holder.deliveryAddress.setText("服务名称:" + orderBean.receipt.add_address);
            holder.type.setTextColor(0xfff07031);
            holder.type.setBackgroundResource(R.mipmap.zjzc_box_comission_service);
            return;
        }
    }

    private void initViews(View convertView, ViewHolder holder) {
        holder.moneyLayout = (AutoRelativeLayout) convertView.findViewById(R.id.order_item_money_layout);
        holder.deliveryTime = (TextView) convertView.findViewById(R.id.order_item_delivery_time);
        holder.type = (TextView) convertView.findViewById(R.id.order_item_type);
        holder.money = (TextView) convertView.findViewById(R.id.order_item_money);
        holder.insured = ((ImageView) convertView.findViewById(R.id.order_item_insured));
        holder.takeIcon = (TextView) convertView.findViewById(R.id.order_item_icon_take);
        holder.addressLayout = (AutoRelativeLayout) convertView.findViewById(R.id.order_item_address_layout);
        holder.takeAddress = (TextView) convertView.findViewById(R.id.order_item_take_address);
        holder.deliveryIcon = (TextView) convertView.findViewById(R.id.order_item_icon_delivery);
        holder.deliveryAddress = (TextView) convertView.findViewById(R.id.order_item_delivery_address);
        holder.takeDistance = (TextView) convertView.findViewById(R.id.order_item_distance_take);
        holder.deliveryDistance = (TextView) convertView.findViewById(R.id.order_item_distance_delivery);

    }

    class ViewHolder {
        public AutoRelativeLayout moneyLayout;
        public TextView deliveryTime;
        public TextView type;
        public TextView money;
        public ImageView insured;
        public AutoRelativeLayout addressLayout;
        public TextView takeIcon;
        public TextView takeAddress;
        public TextView takeDistance;
        public TextView deliveryIcon;
        public TextView deliveryAddress;
        public TextView deliveryDistance;
    }
}
