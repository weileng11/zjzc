package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.bean.ProvinceCityListResponse;

/**
 * Created by Ming on 2016/6/28.
 */
public class CityTagAdapter extends TagAdapter<ProvinceCityListResponse.DataBean.CityListBean> {

    private List<ProvinceCityListResponse.DataBean> provinceCityList;
    private List<ProvinceCityListResponse.DataBean.CityListBean> cityList;
    private Context mContext;

    public CityTagAdapter(Context context, List<ProvinceCityListResponse.DataBean> datas) {
        mContext = context;
        provinceCityList = datas;
        cityList = new ArrayList<>();
        for (int i = 0; i < provinceCityList.size(); i++) {
            cityList.addAll(provinceCityList.get(i).city_list);
        }
        setData(cityList);
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public ProvinceCityListResponse.DataBean.CityListBean getItem(int position) {
        return cityList.get(position);
    }

    public ProvinceCityListResponse.DataBean getProvince(int position) {
        for (int i = 0; i < provinceCityList.size(); i++) {
            ProvinceCityListResponse.DataBean province = provinceCityList.get(i);
            if (province.city_list.contains(cityList.get(position))) {
                return province;
            }
        }
        return null;
    }

    public ProvinceCityListResponse.DataBean.CityListBean getCity(int position) {
        return cityList.get(position);
    }

    @Override
    public View getView(FlowLayout parent, int position, ProvinceCityListResponse.DataBean.CityListBean cityListBean) {
        TextView textView = new TextView(mContext);
        textView.setTextSize(18);
        textView.setTextColor(mContext.getResources().getColor(R.color.zjzc_hint_text_color));
        textView.setBackgroundResource(R.drawable.corner_grey_box_r8);
        textView.setText(cityList.get(position).name);
        textView.setPadding(20, 10, 20, 10);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 40;
        textView.setLayoutParams(params);
        return textView;
    }
}
