package cn.bs.zjzc.util;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 验证码倒计时按钮
 * @author pt-xuejj
 */
public class CheckCodeTimer extends CountDownTimer {
	TextView tipText;
	
	public CheckCodeTimer(TextView tipText) {
		super(60000, 1000);
		this.tipText = tipText;
	}
	
	@Override
	public void onTick(long millisUntilFinished) {
		tipText.setEnabled(false);
		tipText.setText( (millisUntilFinished/1000)+"秒再获取");
	}
	
	@Override
	public void onFinish() {
		tipText.setEnabled(true);
		tipText.setText("获取验证码");
	}
}
