package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.AdvertisementPagerAdatper;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.AdvertisementResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.popupwindow.NormalPopuWindow;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvActivity extends BaseActivity {
    private Context mContext = this;
    private ListView lvActivityList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_activity);
        initViews();
    }

    private void initViews() {
        lvActivityList = ((ListView) findViewById(R.id.activity_list));
    }

}
