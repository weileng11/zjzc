package cn.bs.zjzc.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.model.response.VerificationInfoResponse;
import cn.bs.zjzc.presenter.VerificationInfoPresenter;
import cn.bs.zjzc.ui.view.IVerificationInfoView;

/**
 * Created by Ming on 2016/5/25.
 */
public class AvVerificationInfo extends BaseActivity implements IVerificationInfoView {
    private Context mContext = this;
    private VerificationInfoPresenter mVerificationPresenter;
    private TextView tvCity;
    private TextView tvRealName;
    private TextView tvIdNumber;
    private ImageView photo1;
    private ImageView photo2;
    private ImageView photo3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_verification_info);
        mVerificationPresenter = new VerificationInfoPresenter(this);
        mVerificationPresenter.getVerificationInfo();
        initViews();
    }

    private void initViews() {
        tvCity = ((TextView) findViewById(R.id.verification_info_city));
        tvRealName = ((TextView) findViewById(R.id.verification_info_real_name));
        tvIdNumber = ((TextView) findViewById(R.id.verification_info_id_number));
        photo1 = ((ImageView) findViewById(R.id.verification_info_photo1));
        photo2 = ((ImageView) findViewById(R.id.verification_info_photo2));
        photo3 = ((ImageView) findViewById(R.id.verification_info_photo3));

    }

    @Override
    public void showVerificationInfo(VerificationInfoResponse.DataBean data) {
        tvCity.setText(data.address);
        tvRealName.setText(data.my_real_name);
        tvIdNumber.setText(data.id_card_number);

        Picasso.with(mContext).load(data.id_photo).into(photo1);
        Picasso.with(mContext).load(data.id_back_photo).into(photo2);
        Picasso.with(mContext).load(data.id_people).into(photo3);

    }
}
