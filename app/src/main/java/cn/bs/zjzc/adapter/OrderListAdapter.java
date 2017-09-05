package cn.bs.zjzc.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.dialog.MyDialog;
import cn.bs.zjzc.model.response.BaseResponse;
import cn.bs.zjzc.model.response.OrderListResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.AvCancelOrder;
import cn.bs.zjzc.ui.AvOrderDetail;
import cn.bs.zjzc.ui.AvOrderPay;
import cn.bs.zjzc.util.CheckCodeTimer;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.T;
import cn.bs.zjzc.util.TimeFormatUtils;
import cn.bs.zjzc.util.ViewUtils;

/**
 * Created by Ming on 2016/6/1.
 */
public class OrderListAdapter extends BaseNormalAdapter<OrderListResponse.DataBean.ListBean> {
    /**
     * 订单类型（1 我的接单，2 我的订单）
     */
    private String mType;

    public OrderListAdapter(Context context, String orderType) {
        super(context);
        mType = orderType;
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_order_list, parent, false);
            initViews(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final OrderListResponse.DataBean.ListBean orderBean = getData().get(position);

        holder.orderTime.setText(TimeFormatUtils.formatTimeStr(orderBean.place_time, ""));
        holder.deliveryTime.setText(TimeFormatUtils.formatTimeStr(orderBean.take_time, ""));

        holder.money.setText(Double.parseDouble(orderBean.money) + Double.parseDouble(orderBean.coupon_money) + "元");
        holder.distance.setText(orderBean.distance + "KM");

        //设置类型相关(距离显示,订单类型显示,"取"and"送"字图标显示)
        setType(holder, orderBean);
        //设置状态相关(按钮显示,按钮事件,按钮问题,状态显示)
        setStatus(holder, orderBean);

        //判断保价费是否为0,不是0则显示保价图标,否则隐藏
        if (!TextUtils.equals(orderBean.insured, "0.00") || !TextUtils.equals(orderBean.insured_money, "0.00")) {
            holder.insured.setVisibility(View.VISIBLE);
        } else {
            holder.insured.setVisibility(View.GONE);
        }

        //订单列表Item的点击事件,仅当点击价格栏,地址栏两个布局有效
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvOrderDetail.class);
                intent.putExtra("order_id", orderBean.order_id);
                intent.putExtra("type", mType);
                getContext().startActivity(intent);
            }
        };

        holder.moneyLayout.setOnClickListener(listener);
        holder.addressLayout.setOnClickListener(listener);

        //Log.i("builderItemView", "evaluate_state: " + orderBean.evaluate_state + ",status: " + orderBean.state + ",bigButton: " + holder.bigButton.getVisibility());

        return convertView;
    }

    /**
     * 状态（1 待接单，2 进行中[2 待取货，3 进行中，4 待收货，9 接单用户确认取货，10 下单用户确认取货]，5 已完成 ，6 取消订单[6 下单取消，7 接单取消，11 系统取消]，8 待付款）
     */
    private void setStatus(ViewHolder holder, final OrderListResponse.DataBean.ListBean orderBean) {
        final String status = orderBean.state;
        //8 待付款
        if (TextUtils.equals(status, "8")) {
            holder.smallButtonlayout.setVisibility(View.VISIBLE);
            holder.bigButton.setVisibility(View.GONE);
            holder.status.setText("待付款");
            holder.leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AvOrderPay.class);
                    intent.putExtra("order_id", orderBean.order_id);
                    mContext.startActivity(intent);
                }
            });

            holder.rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(orderBean);
                }
            });
            return;
        }
        //6 取消订单[6 下单取消，7 接单取消，11 系统取消]
        if (TextUtils.equals(status, "6") || TextUtils.equals(status, "7") || TextUtils.equals(status, "11")) {
            holder.bigButton.setVisibility(View.GONE);
            holder.smallButtonlayout.setVisibility(View.GONE);
            holder.status.setText("已取消");
            return;
        }
        //1 待接单
        if (TextUtils.equals(status, "1")) {
            holder.smallButtonlayout.setVisibility(View.GONE);
            holder.bigButton.setVisibility(View.VISIBLE);
            holder.status.setText("待接单");
            holder.bigButton.setText("取消订单");
            holder.bigButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(orderBean);
                }
            });
            return;
        }
        //5 已完成 [用户是否已经评价(0 未评价，1 已评价)]
        if (TextUtils.equals(status, "5")) {
            holder.smallButtonlayout.setVisibility(View.GONE);
            holder.bigButton.setVisibility(View.VISIBLE);
            holder.status.setText("已完成");
            int evaluateState = Integer.parseInt(orderBean.evaluate_state);
            //用户是否已经评价(0 未评价，1 已评价)
            if (evaluateState == 0) {
                holder.bigButton.setText("去评价");
            } else if (evaluateState == 1) {
                holder.bigButton.setText("查看评价");
            }
            holder.bigButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //进入订单详情
                    Intent intent = new Intent(mContext, AvOrderDetail.class);
                    intent.putExtra("order_id", orderBean.order_id);
                    intent.putExtra("type", mType);
                    mContext.startActivity(intent);
                }
            });
            return;
        }
        /**
         * 分割线以上的状态不需要分类型
         * ----------------------------------------------------
         * 分割线以下的状态需要分类型（1 我的接单，2 我的订单）
         */

        //1 我的接单
        if (TextUtils.equals(mType, "1")) {
            holder.smallButtonlayout.setVisibility(View.GONE);
            holder.bigButton.setVisibility(View.VISIBLE);
            /**
             * 2 进行中[2 待取货，3 进行中，4 待收货，9 接单用户确认取货，10 下单用户确认取货(订单详情中才有此项)]
             */

            // 2 进行中，9 接单用户确认取货
            if (TextUtils.equals(status, "2") || TextUtils.equals(status, "9")) {
                holder.status.setText("待取货");
                holder.bigButton.setText("已到达,我要取货");
                holder.bigButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmTakeGoods(orderBean);
                    }
                });
                return;
            }


            holder.bigButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmReceiveGoods(orderBean);
                }
            });
            //3 进行中
            if (TextUtils.equals(status, "3")) {
                holder.status.setText("配送中");
                holder.bigButton.setText("已到达,验证收货");
                return;
            }
            //4 待收货
            if (TextUtils.equals(status, "4")) {
                holder.status.setText("处理中");
                holder.bigButton.setText("完成订单");
                return;
            }
        }
        //2 我的订单
        if (TextUtils.equals(mType, "2")) {
            holder.bigButton.setVisibility(View.GONE);
            holder.smallButtonlayout.setVisibility(View.GONE);
            if (TextUtils.equals(status, "2")) {
                holder.status.setText("待取货");
                return;
            }

            if (TextUtils.equals(status, "3")) {
                holder.status.setText("配送中");
                return;
            }

            if (TextUtils.equals(status, "4")) {
                holder.status.setText("处理中");
                return;
            }
        }
    }

    /**
     * @param orderBean
     */
    private void confirmReceiveGoods(final OrderListResponse.DataBean.ListBean orderBean) {
        final MyDialog myDialog = new MyDialog(getContext());

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_content_verify_order, null);
        myDialog.setCustomContentView(contentView);
        TextView btnCantContact = (TextView) contentView.findViewById(R.id.btn_cant_contact);
        final EditText etPhoneNum = (EditText) contentView.findViewById(R.id.et_phone_num);
        final EditText etCode = (EditText) contentView.findViewById(R.id.et_code);
        TextView btnGetCode = (TextView) contentView.findViewById(R.id.btn_get_code);
        final AutoRelativeLayout codeLayout = (AutoRelativeLayout) contentView.findViewById(R.id.code_layout);

        btnCantContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeLayout.isShown()) {
                    codeLayout.setVisibility(View.GONE);
                    etPhoneNum.setHint("请输入4位收货验证码");
                    etPhoneNum.setText("");
                } else {
                    codeLayout.setVisibility(View.VISIBLE);
                    etPhoneNum.setHint("请输入被委托人的手机号");
                    etPhoneNum.setText("");

                }
            }
        });

        btnGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!DensityUtils.isMobileNO(etPhoneNum.getText().toString())) {
                    T.showShort(getContext(), "请输入正确的手机号码");
                    return;
                }
                getCode(orderBean, etPhoneNum, (TextView) v);
            }
        });

        myDialog.setNegativeButton("取消", null);
        myDialog.setPositiveButton("确定", new MyDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code;
                if (codeLayout.isShown()) {
                    code = etCode.getText().toString();
                } else {
                    code = etPhoneNum.getText().toString();
                }

                String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.finishOrder);
                PostHttpTask<BaseResponse> httpTask = new PostHttpTask<BaseResponse>(url);
                httpTask.addParams("token", App.LOGIN_USER.getToken())
                        .addParams("order_id", orderBean.order_id)
                        .addParams("code", code)
                        .execute(new GsonCallback<BaseResponse>() {
                            @Override
                            public void onFailed(String errorInfo) {
                                T.showShort(getContext(), errorInfo);
                                myDialog.dismiss();
                            }

                            @Override
                            public void onSuccess(BaseResponse response) {
                                T.showShort(getContext(), response.errinfo);
                                myDialog.dismiss();
                            }
                        });
            }
        });

        myDialog.show();
    }

    private void getCode(OrderListResponse.DataBean.ListBean orderBean, EditText etPhoneNum, final TextView v) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.sendCode);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<BaseResponse>(url);
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("order_id", orderBean.order_id)
                .addParams("phone", etPhoneNum.getText().toString())
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        T.showShort(getContext(), errorInfo);
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        new CheckCodeTimer(v).start();
                    }
                });
    }


    /**
     * 确认取货请求
     *
     * @param orderBean
     */
    private void confirmTakeGoods(OrderListResponse.DataBean.ListBean orderBean) {
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.takeGoods);
        PostHttpTask<BaseResponse> httpTask = new PostHttpTask<BaseResponse>(url);
        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("正在通知下单用户...");
        pDialog.show();
        httpTask.addParams("token", App.LOGIN_USER.getToken())
                .addParams("order_id", orderBean.order_id)
                .addParams("type", mType)
                .execute(new GsonCallback<BaseResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        T.showShort(getContext(), errorInfo);
                        pDialog.dismiss();
                    }

                    @Override
                    public void onSuccess(BaseResponse response) {
                        pDialog.dismiss();
                        MyDialog myDialog = new MyDialog(getContext());
                        myDialog.setTitle("取货提示");
                        myDialog.setContent("已通知下单用户，请等待对方确认");
                        myDialog.setNegativeButton("好的", null);
                        myDialog.show();
                    }
                });
    }

    private void cancelOrder(final OrderListResponse.DataBean.ListBean orderBean) {
        final MyDialog myDialog = new MyDialog(getContext());
        myDialog.setTitle("取消提示");
        myDialog.setContent("你确定要取消此订单?");
        myDialog.setNegativeButton("取消", null);
        myDialog.setPositiveButton("确定", new MyDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvCancelOrder.class);
                intent.putExtra("type", mType);
                intent.putExtra("isPay", false);
                intent.putExtra("order_id", orderBean.order_id);
                mContext.startActivity(intent);
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }

    /**
     * 订单类型（1 外卖，2 代买，3 代办 , 4 车友，5 速递）
     *
     * @param holder
     * @param orderBean
     */
    private void setType(ViewHolder holder, OrderListResponse.DataBean.ListBean orderBean) {
        int type = -1;
        try {
            type = Integer.parseInt(orderBean.type);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        //1 外卖，5 速递
        if (type == 1 || type == 5) {
            //显示"送"字图标和距离
            ViewUtils.showViews(holder.deliveryIcon, holder.distance);
            holder.takeIcon.setText("取");
            holder.takeIcon.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_green_circle, 0, 0, 0);
            //取货地址
            holder.takeAddress.setText(orderBean.take_add_address);
            //送货地址
            holder.deliveryAddress.setText(orderBean.receipt_add_address);

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
        ViewUtils.hideViews(holder.deliveryIcon, holder.distance);
        //隐藏了"送"字图标后把原来"取"字图标改成"送"字图标
        holder.takeIcon.setText("送");
        holder.takeIcon.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_red_circle, 0, 0, 0);
        holder.takeAddress.setText(orderBean.receipt_add_address);

        //2 代买
        if (type == 2) {
            holder.type.setText("代买服务");
            holder.deliveryAddress.setText("商品名称:" + orderBean.goods_name);
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
            holder.deliveryAddress.setText("服务名称:" + orderBean.service_name);
            holder.type.setTextColor(0xfff07031);
            holder.type.setBackgroundResource(R.mipmap.zjzc_box_comission_service);
            return;
        }
    }

    private void initViews(View convertView, ViewHolder holder) {
        holder.statusLayout = (AutoFrameLayout) convertView.findViewById(R.id.order_item_status_layout);
        holder.orderTime = (TextView) convertView.findViewById(R.id.order_item_order_time);
        holder.status = (TextView) convertView.findViewById(R.id.order_item_status);
        holder.moneyLayout = (AutoRelativeLayout) convertView.findViewById(R.id.order_item_money_layout);
        holder.deliveryTime = (TextView) convertView.findViewById(R.id.order_item_delivery_time);
        holder.type = (TextView) convertView.findViewById(R.id.order_item_type);
        holder.money = (TextView) convertView.findViewById(R.id.order_item_money);
        holder.insured = ((ImageView) convertView.findViewById(R.id.order_item_insured));
        holder.takeIcon = (TextView) convertView.findViewById(R.id.order_item_icon_take);
        holder.addressLayout = (AutoRelativeLayout) convertView.findViewById(R.id.order_item_address_layout);
        holder.takeAddress = (TextView) convertView.findViewById(R.id.order_item_take_address);
        holder.distance = (TextView) convertView.findViewById(R.id.order_item_distance);
        holder.deliveryIcon = (TextView) convertView.findViewById(R.id.order_item_icon_delivery);
        holder.deliveryAddress = (TextView) convertView.findViewById(R.id.order_item_delivery_address);
        holder.bigButton = (TextView) convertView.findViewById(R.id.order_item_btn_big);
        holder.smallButtonlayout = ((AutoRelativeLayout) convertView.findViewById(R.id.small_button_layout));
        holder.leftButton = (TextView) convertView.findViewById(R.id.order_item_btn_left);
        holder.rightButton = (TextView) convertView.findViewById(R.id.order_item_btn_right);

    }

    class ViewHolder {
        public AutoFrameLayout statusLayout;
        public TextView orderTime;
        public TextView status;
        public AutoRelativeLayout moneyLayout;
        public TextView deliveryTime;
        public TextView type;
        public TextView money;
        public ImageView insured;
        public AutoRelativeLayout addressLayout;
        public TextView takeIcon;
        public TextView takeAddress;
        public TextView distance;
        public TextView deliveryIcon;
        public TextView deliveryAddress;
        public TextView bigButton;
        public AutoRelativeLayout smallButtonlayout;
        public TextView leftButton;
        public TextView rightButton;
    }
}
