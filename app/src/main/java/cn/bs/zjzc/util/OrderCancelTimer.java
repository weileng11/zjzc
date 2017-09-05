package cn.bs.zjzc.util;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

/**
 * 取消订单倒计时按钮
 *
 * @author pt-xuejj
 */
public class OrderCancelTimer extends CountDownTimer {
    TextView tipText;

    public OrderCancelTimer(long timeLong, TextView tipText) {
        super(timeLong, 1000);
        this.tipText = tipText;
    }

    @Override
    public void onTick(long millisUntilFinished) {
//        tipText.setEnabled(false);
        tipText.setText("取消订单（" + (millisUntilFinished / 1000) + "S）");
    }

    @Override
    public void onFinish() {
//        tipText.setEnabled(true);
//        tipText.setText("获取验证码");
        tipText.setVisibility(View.GONE);
    }
}
