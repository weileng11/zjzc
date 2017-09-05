package cn.bs.zjzc.dialog;

import android.content.Context;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;

/**
 * 小费弹框
 * Created by GGG on 2016/7/14.
 */
public class TipMoneyDialog {

    private OptionsPickerView pvOptionsTips;
    private TipMoneySelectListener listener;

    public TipMoneyDialog(Context cxt, TipMoneySelectListener listener) {
        this.listener = listener;
        initTipDialog(cxt);
    }

    private void initTipDialog(Context cxt) {
        //选项选择器
        pvOptionsTips = new OptionsPickerView(cxt);
        //选项1
        final ArrayList<String> tipsPrice = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            tipsPrice.add(i + "元");
        }
        pvOptionsTips.setPicker(tipsPrice);
        //监听确定选择按钮
        pvOptionsTips.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3) {
                listener.tipMoneySelect(tipsPrice.get(options1));
            }
        });
    }

    public void show() {
        pvOptionsTips.show();
    }

    public void dismiss() {
        pvOptionsTips.dismiss();
    }

    public interface TipMoneySelectListener {
        void tipMoneySelect(String tipMoney);
    }
}
