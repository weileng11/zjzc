package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.SelectBankAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.BankListResponse;
import cn.bs.zjzc.presenter.SelectBankPresenter;
import cn.bs.zjzc.ui.view.ISelectBankView;

/**
 * Created by Ming on 2016/6/15.
 */
public class AvSelectBank extends BaseActivity implements ISelectBankView {

    private static final int BANK_RESULT_CODE = 0;
    private Context mContext = this;
    private ListView lvBankList;
    private SelectBankPresenter mSelectBankPresenter;
    private SelectBankAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_simple_list);
        initViews();
        initEvents();
        mSelectBankPresenter = new SelectBankPresenter(this);
        mSelectBankPresenter.getBankList();
    }

    private void initEvents() {
        lvBankList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("selected_bank", adapter.getData().get(position));
                setResult(BANK_RESULT_CODE, intent);
                finish();
            }
        });
    }

    private void initViews() {
        lvBankList = ((ListView) findViewById(R.id.lv_list));
        adapter = new SelectBankAdapter(mContext);
    }


    @Override
    public void showBankList(List<BankListResponse.DataBean> data) {
        BankListResponse.DataBean alipayData = new BankListResponse.DataBean();
        alipayData.name = "支付宝";
        alipayData.id = "";
        data.add(alipayData);
        adapter.setData(data);
        lvBankList.setAdapter(adapter);
    }
}
