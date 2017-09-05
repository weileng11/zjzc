package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.HistoryAddressResponse;
import cn.bs.zjzc.util.DBUtils;

/**
 * 地址历史记录
 * Created by mgc on 2016/7/4.
 */
public class HistroyAddressAdapter extends BaseNormalAdapter<HistoryAddressResponse.Data> {

    private DBUtils db;

    public HistroyAddressAdapter(Context context, DBUtils db, List<HistoryAddressResponse.Data> datas) {
        super(context, datas);
        this.db = db;
    }

    @Override
    public View builderItemView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_history_record_list, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.phone_number = (TextView) convertView.findViewById(R.id.phone_number);
            holder.address_detail = (TextView) convertView.findViewById(R.id.order_detail_address_take);
            holder.btn_delete = (ImageView) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(mDatas.get(position).name);
        holder.phone_number.setText(mDatas.get(position).phone);
        holder.address_detail.setText(mDatas.get(position).address);

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteOneData("'" + new Gson().toJson(mDatas.get(position)) + "'");
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    class ViewHolder {
        public TextView name, phone_number, address_detail;
        public ImageView btn_delete;
    }
}
