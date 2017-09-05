package cn.bs.zjzc.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.FAQAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.dialog.MyDialog;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.div.TopBarView;
import cn.bs.zjzc.model.response.FAQListResponse;
import cn.bs.zjzc.presenter.ServiceHelpPresenter;
import cn.bs.zjzc.ui.view.IServiceHelpView;
import cn.bs.zjzc.util.SPUtils;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvServiceHelp extends BaseActivity implements View.OnClickListener, IServiceHelpView {

    private Context mContext = this;
    private ServiceHelpPresenter mServiceHelpPresenter;
    private int currentPage = 1;

    private TopBarView topBar;
    private TextView callCenter;//客服电话
    private TextView feedBack;
    private NormalOptionView FAQProblem;
    private ListView lvFAQList;
    private FAQAdapter adapter;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int CALLPHONE_CODE = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_service_and_help);
        mServiceHelpPresenter = new ServiceHelpPresenter(this);
        mServiceHelpPresenter.getFQAList(null, "1");
        initViews();
        initEvents();
        initListView();
    }

    private void initListView() {
        adapter = new FAQAdapter(mContext);
        lvFAQList.setAdapter(adapter);

        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrFrameLayout.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                mServiceHelpPresenter.getFQAList(null, String.valueOf(++currentPage));
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {

            }
        });
    }

    private void initEvents() {
        callCenter.setOnClickListener(this);
        feedBack.setOnClickListener(this);

        FAQProblem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lvFAQList.isShown()) {
                    lvFAQList.setVisibility(View.GONE);
                    FAQProblem.setRightIconResId(R.mipmap.zjzc_arrow_down);
                } else {
                    lvFAQList.setVisibility(View.VISIBLE);
                    FAQProblem.setRightIconResId(R.mipmap.zjzc_arrow_up);
                }
            }
        });

        topBar.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AvFAQSearch.class));
            }
        });
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.custom_service_top_bar));
        callCenter = ((TextView) findViewById(R.id.custom_service_call_center));
        feedBack = ((TextView) findViewById(R.id.custom_service_feedback));
        FAQProblem = ((NormalOptionView) findViewById(R.id.custom_service_faq_problem));
        lvFAQList = ((ListView) findViewById(R.id.custom_service_faq_list));
        ptrFrameLayout = (PtrClassicFrameLayout) findViewById(R.id.custom_service_ptr_frame);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_service_call_center:
                //拨号
                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {  //检查是否已授权
                    boolean shouldShow = ActivityCompat.shouldShowRequestPermissionRationale(AvServiceHelp.this, Manifest.permission.CALL_PHONE);
                    if (!shouldShow) {
                        ActivityCompat.requestPermissions(AvServiceHelp.this,
                                new String[]{Manifest.permission.CALL_PHONE},
                                CALLPHONE_CODE);
                    } else {
                        final MyDialog tipDialog = new MyDialog(this);
                        tipDialog.setContent("您已关闭拨号权限");
                        tipDialog.setTitle("提示");
                        tipDialog.setPositiveButton("确定", new MyDialog.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (tipDialog.isShowing())
                                    tipDialog.dismiss();
                            }
                        });
                    }
                } else {
                    callPhone();
                }
                break;
            case R.id.custom_service_feedback:
                startActivity(new Intent(mContext, AvFeedback.class));
                break;
        }
    }

    //    授权的结果回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CALLPHONE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhone();
            } else {
                Toast.makeText(mContext, "您没有给应用授于拨号的权限", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void callPhone() {
        String phone = (String) SPUtils.get(this, "servicePhone", "");
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void showFAQList(FAQListResponse.DataBean data) {
        ptrFrameLayout.refreshComplete();
        if (TextUtils.equals(data.page.page, "1")) {
            adapter.setData(data.list);
            return;
        }
        adapter.addDatas(data.list);
    }

    @Override
    public void completeFresh() {
        ptrFrameLayout.refreshComplete();
    }
}
