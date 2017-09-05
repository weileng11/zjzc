package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.BankListResponse;

/**
 * Created by Ming on 2016/6/15.
 */
public class SelectBankAdapter extends BaseNormalAdapter<BankListResponse.DataBean> {


    public SelectBankAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = (TextView) LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }else{
            textView = (TextView) convertView;
        }
        textView.setText(getItem(position).name);
        return textView;
    }
}
