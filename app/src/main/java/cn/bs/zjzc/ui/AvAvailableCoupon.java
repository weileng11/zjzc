package cn.bs.zjzc.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.AvailableCouponAdapter;
import cn.bs.zjzc.model.response.OrderCouponResponse;
import cn.bs.zjzc.net.RequestUrl;

/**
 * 订单支付可用优惠卷
 */
public class AvAvailableCoupon extends Activity {

    private ListView listView;
    private TextView emptyView;
    private List<OrderCouponResponse.DataBean> data = new ArrayList<>();
    private AvailableCouponAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_use_coupon);

        initView();

        getData();
    }

    private void getData() {
        if (getIntent().getSerializableExtra("data") != null) {
            data = (List<OrderCouponResponse.DataBean>) getIntent().getSerializableExtra("data");
        }
        adapter = new AvailableCouponAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("name", data.get(position).name);
                intent.putExtra("coupon_id", data.get(position).coupon_id);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initView() {
        emptyView = (TextView) findViewById(R.id.emptyView);
        listView = (ListView) findViewById(R.id.listView);
    }

    public void clickBTN(View view) {
        switch (view.getId()) {
            case R.id.tv_no_use://不使用优惠卷
                Intent intent = new Intent();
                intent.putExtra("name", "不使用优惠卷");
//                intent.putExtra("coupon_id", null);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.tv_use_rule://使用规则
                Intent intentRule = new Intent(this, AvCommdWebView.class);
                intentRule.putExtra("title", "使用规则");
                intentRule.putExtra("url", RequestUrl.getRequestPath(RequestUrl.SubPaths.couponRule));
                startActivity(intentRule);
                break;
        }
    }
}
