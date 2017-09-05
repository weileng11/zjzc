package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.FAQSearchAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.FAQListResponse;
import cn.bs.zjzc.presenter.ServiceHelpPresenter;
import cn.bs.zjzc.ui.view.IServiceHelpView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvFAQSearch extends BaseActivity implements View.OnClickListener, IServiceHelpView {

    private Context mContext = this;
    private ServiceHelpPresenter mServiceHelpPresenter;
    private EditText searchKey;
    private ImageView cancel;
    private TextView confirm;
    private ImageView back;
    private PtrClassicFrameLayout ptrFrame;
    private ListView lvResultList;
    private int mCurrentPage = 1;
    private FAQSearchAdapter faqSearchAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_global_search);
        mServiceHelpPresenter = new ServiceHelpPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        back.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);

        searchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    faqSearchAdapter.clearData();
                    return;
                }
                mCurrentPage = 1;
                mServiceHelpPresenter.getFQAList(s.toString(), String.valueOf(mCurrentPage++));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        initListView();
    }

    private void initListView() {
        faqSearchAdapter = new FAQSearchAdapter(mContext);
        lvResultList.setAdapter(faqSearchAdapter);

        ptrFrame.setLastUpdateTimeRelateObject(this);
        ptrFrame.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        ptrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mServiceHelpPresenter.getFQAList(searchKey.getText().toString(), String.valueOf(mCurrentPage++));
            }
        });
    }

    private void initViews() {
        back = ((ImageView) findViewById(R.id.back));
        searchKey = ((EditText) findViewById(R.id.search_key));
        cancel = ((ImageView) findViewById(R.id.search_cancel));
        confirm = ((TextView) findViewById(R.id.search_confirm));
        ptrFrame = ((PtrClassicFrameLayout) findViewById(R.id.ptr_frame));
        lvResultList = ((ListView) findViewById(R.id.search_result_list));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_cancel:
                searchKey.setText("");
                break;
            case R.id.search_confirm:
                break;
        }
    }

    @Override
    public void showFAQList(FAQListResponse.DataBean data) {
        if (TextUtils.equals(data.page.page, "1")) {
            faqSearchAdapter.setData(data.list);
        } else {
            faqSearchAdapter.addDatas(data.list);
        }
        ptrFrame.refreshComplete();
    }

    @Override
    public void completeFresh() {
        ptrFrame.refreshComplete();
    }
}
