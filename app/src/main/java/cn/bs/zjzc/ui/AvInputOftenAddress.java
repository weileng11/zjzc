package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.SearchAddressListAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.util.T;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvInputOftenAddress extends BaseActivity implements OnGetPoiSearchResultListener {

    private String mAction;
    private Context mContext = this;
    private TextView selectCity;
    private TextView cancel;
    private EditText search;

    private PoiSearch mPoiSearch;
    private ListView listView;
    private SearchAddressListAdapter adapter;
    private ArrayList<PoiInfo> addressInfoList = new ArrayList<>();
    private AutoLinearLayout oftenAddressLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_input_address);
        initBase();
        initViews();
        initEvents();

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        initListView();
    }

    private void initBase() {
        mAction = getIntent().getAction();
    }


    private void initListView() {
        listView = (ListView) findViewById(R.id.input_address_history);
        adapter = new SearchAddressListAdapter(mContext);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AvInputOftenAddress.this, AvOftenAddressComplement.class);
                PoiInfo value = addressInfoList.get(position);
                intent.putExtra("address_info", value);
                intent.setAction(mAction);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initEvents() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvSelectCity.class);
                intent.putExtra("cityName", selectCity.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() <= 0) {
                    return;
                }
                mPoiSearch.searchInCity((new PoiCitySearchOption()).city(selectCity.getText().toString()).keyword(s.toString()).pageNum(1));
            }
        });
    }

    private void initViews() {
        selectCity = ((TextView) findViewById(R.id.input_address_select_city));
        if (App.LOGIN_USER.getCityInfo()[1] != null) {
            selectCity.setText(App.LOGIN_USER.getCityInfo()[1]);
        }
        search = ((EditText) findViewById(R.id.input_address_search));
        cancel = ((TextView) findViewById(R.id.input_address_cancel));
        oftenAddressLayout = ((AutoLinearLayout) findViewById(R.id.input_often_address_layout));
        oftenAddressLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectCity.setText(data.getStringExtra("city"));
        }
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            T.showShort(AvInputOftenAddress.this, "抱歉,未找到结果");
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            addressInfoList.clear();
            addressInfoList.addAll(poiResult.getAllPoi());
            adapter.setData(addressInfoList);
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult result) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        super.onDestroy();
    }
}
