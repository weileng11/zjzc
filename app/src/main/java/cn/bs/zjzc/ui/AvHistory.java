package cn.bs.zjzc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.adapter.HistroyAddressAdapter;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.bean.HistoryAddressParamters;
import cn.bs.zjzc.model.response.HistoryAddressResponse;
import cn.bs.zjzc.util.DBUtils;
import cn.bs.zjzc.util.L;

/**
 * 历史记录（地址）
 * Created by Ming on 2016/5/26.
 */
public class AvHistory extends BaseActivity {

    private ListView listView;
    private TextView emptyView;
    private List<HistoryAddressResponse.Data> list = new ArrayList<>();
    private HistroyAddressAdapter adapter;
    private DBUtils db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_history_record);

        db = new DBUtils(this);
        db.open();

        initViews();

        selectHistory();
    }

    private void selectHistory() {
        List<String> listJson = db.queryAllData();
        Type type = new TypeToken<HistoryAddressResponse>() {
        }.getType();
        Gson gson = new Gson();
        HistoryAddressResponse history = gson.fromJson("{data:" + listJson + "}", type);
        if (history != null && history.data != null && history.data.size() != 0) {
            list.clear();
            list.addAll(history.data);
            adapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        emptyView = (TextView) findViewById(R.id.emptyView);
        listView = (ListView) findViewById(R.id.history_list);
        adapter = new HistroyAddressAdapter(this, db, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AvHistory.this, AvInfoComplement.class);
                HistoryAddressParamters historyAddressParamters = new HistoryAddressParamters(list.get(position).province_id, list.get(position).city_id, list.get(position).x, list.get(position).y, list.get(position).address, list.get(position).add_address, list.get(position).name, list.get(position).phone);
                intent.putExtra("HistoryAddressParamters", historyAddressParamters);
                L.d("OrderParameters", "History:" + historyAddressParamters);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        listView.setEmptyView(emptyView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
