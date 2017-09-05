package cn.bs.zjzc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;

/**
 * Created by Ming on 2016/6/29.
 */
public class SearchAddressListAdapter extends BaseNormalAdapter<PoiInfo> {
    public SearchAddressListAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_search_address_list, parent, false);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(getItem(position).name);
        holder.address.setText(getItem(position).address);
        return convertView;
    }

    class ViewHolder {
        private TextView name,address;
    }
}
