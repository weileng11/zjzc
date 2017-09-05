package cn.bs.zjzc.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.model.response.AdvertisementResponse;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.ui.AvCommdWebView;
import cn.bs.zjzc.util.L;

/**
 * Created by Ming on 2016/6/24.
 */
public class AdvertisementPagerAdatper extends PagerAdapter {
    private List<AdvertisementResponse.DataBean> mData;
    private Context mContext;

    public AdvertisementPagerAdatper(Context context) {
        mContext = context;
        mData = new ArrayList<>();
    }

    public void setData(List<AdvertisementResponse.DataBean> data) {
        mData.clear();
        appendData(data);
    }

    public void appendData(List<AdvertisementResponse.DataBean> data) {
        if (data == null) {
            return;
        }
        mData.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setTag(position);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.mipmap.ic_launcher);
        final String url = RequestUrl.getImgPath(RequestUrl.WebPath.advDetail) + "?id=" + mData.get(position).id + "&token=" + App.LOGIN_USER.getToken();
        L.d("Adv", "url=" + url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvCommdWebView.class);
                intent.putExtra("url", url);
                intent.putExtra("title", mData.get(position).title);
                mContext.startActivity(intent);
            }
        });
        String img = RequestUrl.getImgPath(mData.get(position).img);
        L.d("Adv", "imgpath=" + img);
        Picasso.with(mContext).load(img).error(R.drawable.test).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = container.findViewWithTag(position);
        container.removeView(view);
    }
}
