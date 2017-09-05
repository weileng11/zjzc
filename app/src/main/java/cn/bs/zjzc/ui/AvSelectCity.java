package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.CityTagAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.bean.ProvinceCityListResponse;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvSelectCity extends BaseActivity {

    private Context mContext = this;
    private TextView tvLocatedCity;
    private TagFlowLayout cityTags;
    private List<ProvinceCityListResponse.DataBean> provinceCityList = new ArrayList<>();
    private CityTagAdapter cityTagAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_select_city);

        initViews();

        initEvents();
    }

    private void initEvents() {

    }

    private void initViews() {
        tvLocatedCity = (TextView) findViewById(R.id.select_city_locate_city);
        cityTags = ((TagFlowLayout) findViewById(R.id.select_city_city_tag));
        provinceCityList.addAll(App.LOGIN_USER.getProvinceCityList());
        cityTagAdapter = new CityTagAdapter(mContext, provinceCityList);
        cityTags.setAdapter(cityTagAdapter);

        cityTags.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent();
                intent.putExtra("city", cityTagAdapter.getCity(position).name);
                setResult(RESULT_OK, intent);
                finish();
                return false;
            }
        });

        tvLocatedCity.setText(getIntent().getStringExtra("cityName"));
    }
}
