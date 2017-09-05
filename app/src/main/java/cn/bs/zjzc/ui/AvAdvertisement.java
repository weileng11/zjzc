package cn.bs.zjzc.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.AdvertisementPagerAdatper;
import cn.bs.zjzc.model.response.AdvertisementResponse;
import cn.bs.zjzc.net.GsonCallback;
import cn.bs.zjzc.net.PostHttpTask;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.util.L;

/**
 * 广告
 * mgc
 */
public class AvAdvertisement extends Activity {

    private Context mContext = this;
    private String cityName;
    private ImageView close;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //点击区域外不消失
        setFinishOnTouchOutside(false);

        setContentView(R.layout.av__advertisement);

        cityName = getIntent().getStringExtra("cityName");

        initView();
    }

    private void initView() {
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        String url = RequestUrl.getRequestPath(RequestUrl.SubPaths.getAdvertisementList);
        PostHttpTask<AdvertisementResponse> httpTask = new PostHttpTask<>(url);
        httpTask.addParams("city", cityName)
                .execute(new GsonCallback<AdvertisementResponse>() {
                    @Override
                    public void onFailed(String errorInfo) {
                        finish();
                    }

                    @Override
                    public void onSuccess(AdvertisementResponse response) {
                        AdvertisementPagerAdatper adapter = new AdvertisementPagerAdatper(mContext);
                        adapter.setData(response.data);
                        viewPager.setAdapter(adapter);
                    }
                });
    }
}
