package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.fragment.MyOrderFragment;
import cn.bs.zjzc.util.T;

/**
 * Created by Administrator on 2016/7/12.
 */
public class AvOrderSearch extends BaseActivity implements View.OnClickListener, TextWatcher {

    private ViewGroup content_layout;
    private EditText search_key;
    private ImageView search_cancel;
    private TextView search_confirm;
    private FragmentManager supportFragmentManager;
    private String mType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_order_search);
        initView();
        initEvent();
        initBasic();
    }

    private void initBasic() {
        supportFragmentManager = getSupportFragmentManager();
        mType = getIntent().getStringExtra("type");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_cancel:  //清空关键词
                search_key.setText("");
                break;
            case R.id.search_confirm: //确认
                String keyWord = search_key.getText().toString().trim();
                if (TextUtils.isEmpty(keyWord)) {
                    T.showShort(this, "关键字不能为空");
                    return;
                }
                MyOrderFragment fragment = new MyOrderFragment();
                Bundle bundle = new Bundle();
                bundle.putString("type", mType);
                bundle.putString("order_type", null);
                bundle.putString("state", "");
                bundle.putString("keyword", keyWord);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.content_layout, fragment).commit();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void initEvent() {
        search_cancel.setOnClickListener(this);
        search_confirm.setOnClickListener(this);
        search_key.addTextChangedListener(this);
    }

    private void initView() {
        search_key = ((EditText) findViewById(R.id.search_key));
        search_cancel = ((ImageView) findViewById(R.id.search_cancel));
        search_confirm = ((TextView) findViewById(R.id.search_confirm));
        content_layout = ((ViewGroup) findViewById(R.id.content_layout));
    }
}
