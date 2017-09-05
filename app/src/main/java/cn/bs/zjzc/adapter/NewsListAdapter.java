package cn.bs.zjzc.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Date;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseNormalAdapter;
import cn.bs.zjzc.model.response.NewsListResponse;
import cn.bs.zjzc.util.CommonUtil;
import cn.bs.zjzc.util.DensityUtils;
import cn.bs.zjzc.util.TimeFormatUtils;

/**
 * Created by Ming on 2016/6/17.
 */
public class NewsListAdapter extends BaseNormalAdapter<NewsListResponse.DataBean.ListBean> {

    public NewsListAdapter(Context context) {
        super(context);
    }

    @Override
    public View builderItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_news_list, parent, false);

            holder.tvTime = (TextView) convertView.findViewById(R.id.time);
            holder.tvContent = (TextView) convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsListResponse.DataBean.ListBean item = getData().get(position);
        setState(item.state, holder, convertView);

        holder.tvTime.setText(TimeFormatUtils.formatTimeStr(item.push_time, "yyyy年mm月dd日  hh:MM:ss"));
        holder.tvContent.setText(item.content);

        return convertView;
    }

    public void setState(String state, ViewHolder holder, View convertView) {
        //如果消息是已读状态,改变灰色界面
        if (TextUtils.equals(state, "1")) {
            holder.tvContent.setTextColor(0xffadadad);
            holder.tvTime.setTextColor(0xffadadad);
            holder.tvTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_news_read, 0, 0, 0);
            convertView.setEnabled(false);
        }
        //新消息状态,改变彩色界面
        if (TextUtils.equals(state, "2")) {
            holder.tvTime.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.zjzc_icon_news_new, 0, 0, 0);
            holder.tvContent.setTextColor(0xff000000);
            holder.tvTime.setTextColor(0xffff8704);
            convertView.setEnabled(false);
        }
        //删除状态隐藏item
        if (TextUtils.equals(state, "3")) {
            convertView.setVisibility(View.GONE);
        }

    }

    /**
     * 设置position位置消息为已读
     *
     * @param position
     */
    public void setRead(int position) {
        getItem(position).state = "1";
        notifyDataSetChanged();
    }

    /**
     * 设置全部消息已读
     */
    public void setReadAll() {
        for (NewsListResponse.DataBean.ListBean dataBean : getData()) {
            dataBean.state = "1";
        }
        notifyDataSetChanged();
    }

    /**
     * 删除全部消息
     */
    public void deleteAll() {
        for (NewsListResponse.DataBean.ListBean dataBean : getData()) {
            dataBean.state = "3";
        }
        getData().clear();
        notifyDataSetChanged();
    }

    /**
     * 删除position位置消息
     *
     * @param position
     */
    public void delete(int position) {
        getItem(position).state = "3";
        getData().remove(position);
        notifyDataSetChanged();
    }

    class ViewHolder {
        private TextView tvTime;
        private TextView tvContent;
    }
}
