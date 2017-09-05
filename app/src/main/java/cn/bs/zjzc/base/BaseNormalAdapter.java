package cn.bs.zjzc.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BaseNormalAdapter<T> extends BaseAdapter {

    protected List<T> mDatas = new ArrayList<T>();

    protected Context mContext;

    protected boolean setDescendant = true;

    public BaseNormalAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    public BaseNormalAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }


    protected Context getContext() {
        return mContext;
    }

    public void setDescendant(boolean isDescendant) {
        setDescendant = isDescendant;
    }

    public List<T> getData() {
        return mDatas;
    }

    public void setData(List<T> srcDatas) {
        if (srcDatas == null) return;
//		if(srcDatas.isEmpty()) return ;
        mDatas.clear();
        addDatas(srcDatas);
    }

    public void addDatas(List<T> appendDatas) {
        if (appendDatas == null) return;
        mDatas.addAll(appendDatas);
        notifyDataSetChanged();
    }

    public void addData(T appendDatas) {
        if (appendDatas == null) return;
        mDatas.add(appendDatas);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    public void clearData() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = builderItemView(position, convertView, parent);
        if (setDescendant && (convertView instanceof ViewGroup)) {
            /**
             FOCUS_BEFORE_DESCENDANTS ViewGroup本身先对焦点进行处理，如果没有处理则分发给child View进行处理
             FOCUS_AFTER_DESCENDANTS 先分发给Child View进行处理，如果所有的Child View都没有处理，则自己再处理
             FOCUS_BLOCK_DESCENDANTS ViewGroup本身进行处理，不管是否处理成功，都不会分发给ChildView进行处理
             */
            ((ViewGroup) convertView).setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
        return convertView;
    }

    public abstract View builderItemView(int position, View convertView, ViewGroup parent);

}
