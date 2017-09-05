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
import java.util.Dictionary;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.SearchAddressListAdapter;
import cn.bs.zjzc.baidumap.IndexOfUtils;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.bean.HistoryAddressParamters;
import cn.bs.zjzc.model.callback.HttpTaskCallback;
import cn.bs.zjzc.model.impl.OftenAddressModel;
import cn.bs.zjzc.model.response.OftenUseAddressResponse;
import cn.bs.zjzc.util.T;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvInputAddress extends BaseActivity implements OnGetPoiSearchResultListener {


    private Context mContext = this;
    private TextView selectCity;
    private TextView cancel;
    private TextView home;
    private TextView company;
    private TextView emptyView;
    private EditText search;
    private AutoLinearLayout oftenAddressLayout;

    private PoiSearch mPoiSearch;
    private ListView listView;

    private SearchAddressListAdapter adapter;
    private ArrayList<PoiInfo> addressInfoList = new ArrayList<>();

    private boolean forResult;//是否需要返回值

    private OftenAddressModel sp;//公司，家庭常用地址获取
    private int type, mType;

    private HistoryAddressParamters historyAddressParamters = new HistoryAddressParamters();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_input_address);
        initViews();
        initEvents();

        // 初始化搜索模块，注册搜索事件监听
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);

        initListView();

        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);
        mType = intent.getIntExtra("mType", 1);
        forResult = intent.getBooleanExtra("forResult", false);

        sp = new OftenAddressModel(this, App.LOGIN_USER.getAccount());
    }

    private void initListView() {
        emptyView = (TextView) findViewById(R.id.emptyView);
        listView = (ListView) findViewById(R.id.input_address_history);
        adapter = new SearchAddressListAdapter(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AvInputAddress.this, AvInfoComplement.class);
                historyAddressParamters.address = addressInfoList.get(position).name;
                historyAddressParamters.x = addressInfoList.get(position).location.latitude + "";
                historyAddressParamters.y = addressInfoList.get(position).location.longitude + "";
                intent.putExtra("HistoryAddressParamters", historyAddressParamters);
                intent.putExtra("type", type);
                intent.putExtra("mType", mType);
                if (forResult) {
                    setResult(RESULT_OK, intent);
                } else {
                    startActivity(intent);
                }
                finish();
            }
        });
        listView.setEmptyView(emptyView);
    }

    private void initEvents() {
        selectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AvSelectCity.class);
                intent.putExtra("cityName", selectCity.getText().toString());
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initViews() {
        selectCity = ((TextView) findViewById(R.id.input_address_select_city));
        if (App.LOGIN_USER.getCityInfo()[1] != null) {
            selectCity.setText(App.LOGIN_USER.getCityInfo()[1]);
        }
        search = ((EditText) findViewById(R.id.input_address_search));
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
                showLoading();
                mPoiSearch.searchInCity((new PoiCitySearchOption()).city(selectCity.getText().toString()).keyword(s.toString()).pageNum(1));
            }
        });
        cancel = ((TextView) findViewById(R.id.input_address_cancel));
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        home = ((TextView) findViewById(R.id.input_address_home));
        company = ((TextView) findViewById(R.id.input_address_company));
    }

    public void clickBTN(View v) {
        switch (v.getId()) {
            case R.id.input_address_home:
                sp.getHomeAddress(new HttpTaskCallback<OftenUseAddressResponse.DataBean>() {
                    @Override
                    public void onTaskFailed(String errorInfo) {
                        startActivity(new Intent(mContext, AvOftenUseAddress.class));
                    }

                    @Override
                    public void onTaskSuccess(OftenUseAddressResponse.DataBean data) {
                        startActivity(data);
                    }
                });
                break;
            case R.id.input_address_company:
                sp.getCompanyAddress(new HttpTaskCallback<OftenUseAddressResponse.DataBean>() {
                    @Override
                    public void onTaskFailed(String errorInfo) {
                        startActivity(new Intent(mContext, AvOftenUseAddress.class));
                    }

                    @Override
                    public void onTaskSuccess(OftenUseAddressResponse.DataBean data) {
                        startActivity(data);
                    }
                });
                break;
        }
    }

    private void startActivity(OftenUseAddressResponse.DataBean data) {
        historyAddressParamters.address = data.address;
        historyAddressParamters.add_address = data.add_address;
        historyAddressParamters.province_id = data.province_id;
        historyAddressParamters.city_id = data.city_id;
        historyAddressParamters.x = data.x;
        historyAddressParamters.y = data.y;
        Intent intent = new Intent(AvInputAddress.this, AvInfoComplement.class);
        intent.putExtra("HistoryAddressParamters", historyAddressParamters);
        intent.putExtra("type", type);
        intent.putExtra("mType", mType);
        if (forResult) {
            setResult(RESULT_OK, intent);
        } else {
            startActivity(intent);
        }
        finish();
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        hideLoading();
        if (poiResult == null || poiResult.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {// 没有找到检索结果
            T.showShort(AvInputAddress.this, "抱歉,未找到结果");
            return;
        }
        if (poiResult.error == SearchResult.ERRORNO.NO_ERROR) {// 检索结果正常返回
            addressInfoList.clear();
            addressInfoList.addAll(poiResult.getAllPoi());
            adapter.setData(addressInfoList);
            String[] ids = IndexOfUtils.setProvinceAndCityId(IndexOfUtils.getProvinceName(selectCity.getText().toString()), selectCity.getText().toString(), false);
            historyAddressParamters.province_id = ids[0];
            historyAddressParamters.city_id = ids[1];
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            selectCity.setText(data.getStringExtra("city"));
        }
    }
}
