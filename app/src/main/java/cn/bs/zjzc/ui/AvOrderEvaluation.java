package cn.bs.zjzc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.OrderEvaluation;
import cn.bs.zjzc.presenter.EvaluationOrderPresenter;
import cn.bs.zjzc.ui.view.IOrderEvaluationView;
import cn.bs.zjzc.util.L;
import cn.bs.zjzc.util.T;

/**
 * Created by Administrator on 2016/7/5.
 */
public class AvOrderEvaluation extends BaseActivity implements IOrderEvaluationView, View.OnClickListener {

    private TextView tips;
    private RatingBar stars;
    private ViewGroup order_detail_labLayout;
    private EditText et_more_opinion;
    private TextView order_evaluation_confirm;
    private String mOrder_id;
    private String mType;
    private EvaluationOrderPresenter mEvaluationOrderPresenter;
    private boolean mIsRead;

    private String[] orderLab; //我的接单标签

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_order_evaluation);
        initView();
        initBasic();
        initEvent();
    }

    private void initEvent() {
        order_evaluation_confirm.setOnClickListener(this);
    }

    private void initBasic() {
        mOrder_id = getIntent().getStringExtra("order_id");
        mType = getIntent().getStringExtra("type");
        mIsRead = getIntent().getBooleanExtra("isRead", false);
        mEvaluationOrderPresenter = new EvaluationOrderPresenter(this);
        if (!mIsRead) {
            order_evaluation_confirm.setVisibility(View.VISIBLE);
        }
        if (mIsRead)
            mEvaluationOrderPresenter.checkOrderEvaluation(mType, mOrder_id);
        if (mType.equals("1")) { //我的接单
            orderLab = new String[]{"有礼貌", "好客户", "通情达理"}; //我的订单标签
        } else {  //我的订单
            orderLab = new String[]{"有礼貌", "准时到达", "通情达理"}; //我的接单标签
        }
        for (int i = 0; i < order_detail_labLayout.getChildCount(); i++) {
            ((CheckBox) order_detail_labLayout.getChildAt(i)).setText(orderLab[i]);
        }
    }

    private void initView() {
        tips = ((TextView) findViewById(R.id.tv_evaluation_tips));
        stars = ((RatingBar) findViewById(R.id.order_evaluation_stars));
        order_detail_labLayout = ((ViewGroup) findViewById(R.id.order_detail_labLayout));
        et_more_opinion = ((EditText) findViewById(R.id.et_more_opinion));
        order_evaluation_confirm = ((TextView) findViewById(R.id.order_evaluation_confirm));
    }

    @Override
    public void evaluationSuccessView() {
        if (mType.equals("1")) { //我的接单
            Intent intent = new Intent(this, AvOrderCompetition.class);
            startActivity(intent);
        } else if (mType.equals("2")) {  //我的订单
            Intent intent = new Intent();
            intent.putExtra("isSuccess", true);
            setResult(1001, intent);  //跳转详情页
        }
        finish();
    }

    @Override
    public void checkOutEvaluation(OrderEvaluation orderEvaluation) {
        float level = Float.parseFloat(orderEvaluation.data.level);
        stars.setRating(level);
        //设置评星不可点击
        stars.setIsIndicator(true);
        List<Integer> type = orderEvaluation.data.type;
//        选中要显示的评价标签
        for (int i = 0; i < type.size(); i++) {
            ((CheckBox) order_detail_labLayout.getChildAt(type.get(i) - 1)).setChecked(true);
        }
//        设置评价标签不可点击
        for (int i = 0; i < order_detail_labLayout.getChildCount(); i++) {
            ((CheckBox) order_detail_labLayout.getChildAt(i)).setEnabled(false);
        }

//        文本编辑框设置
        et_more_opinion.setText(orderEvaluation.data.content);
        et_more_opinion.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_evaluation_confirm:
                String evaluate_type = "";
                for (int i = 0; i < order_detail_labLayout.getChildCount(); i++) {
                    CheckBox cb = (CheckBox) order_detail_labLayout.getChildAt(i);
                    if (cb.isChecked()) {
                        evaluate_type = evaluate_type + (i + 1) + ",";
                    }
                }
                if (evaluate_type.length() != 0)
                    evaluate_type = evaluate_type.substring(0, evaluate_type.length() - 1);
                if (TextUtils.isEmpty(evaluate_type)) {
                    T.showShort(this, "未选择评价标签");
                    return;
                }

                String content = et_more_opinion.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    T.showShort(this, "评价内容不能为空");
                    return;
                }
                float rating = stars.getRating();
                L.e(mType + "\n" + mOrder_id + "\n" + evaluate_type + "\n" + rating + "\n" + content);
                mEvaluationOrderPresenter.conmintEvaluation(mType, mOrder_id, evaluate_type, rating + "", content);
                break;
        }
    }
}
