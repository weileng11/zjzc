package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.IndustryPresenter;
import cn.bs.zjzc.ui.view.IIndustryView;

/**
 * Created by Ming on 2016/6/13.
 */
public class AvIndustry extends BaseActivity implements IIndustryView {

    private Context mContext = this;
    private ListView lvIndustryList;
    private IndustryPresenter mIndustryPresenter;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_simple_list);
        initViews();
        initEvents();
        mIndustryPresenter = new IndustryPresenter(this);
        mIndustryPresenter.getIndustryList();
    }

    private void initEvents() {
        lvIndustryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (adapter.getItem(position).equals(App.LOGIN_USER.getIndustry())) {
                    finish();
                }
                mIndustryPresenter.changeIndustry(adapter.getItem(position));
            }
        });
    }

    private void initViews() {
        lvIndustryList = ((ListView) findViewById(R.id.lv_list));
    }

    @Override
    public void showIndustry(List<String> data) {
        adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, data);
        lvIndustryList.setAdapter(adapter);
    }

    @Override
    public void changeIndustrySuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
