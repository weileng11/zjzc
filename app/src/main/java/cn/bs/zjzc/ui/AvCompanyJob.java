package cn.bs.zjzc.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.presenter.CompanyJobPresenter;
import cn.bs.zjzc.ui.view.ICompanyJobView;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvCompanyJob extends BaseActivity implements ICompanyJobView {
    private CompanyJobPresenter mCompanyJobPresenter;
    private EditText etCompanyName;
    private EditText etJob;
    private TextView btnSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_company_job_info);
        mCompanyJobPresenter = new CompanyJobPresenter(this);
        initViews();
        initEvents();
    }

    private void initEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String company = etCompanyName.getText().toString();
                String job = etJob.getText().toString();
                String companyJob = getCompanyJob(company, job);
                if (TextUtils.equals(companyJob, App.LOGIN_USER.getCompany_job())) {
                    finish();
                } else {
                    mCompanyJobPresenter.changeCompanyJob(companyJob);
                }

            }
        });
    }

    private void initViews() {
        etCompanyName = ((EditText) findViewById(R.id.company_job_et_company_name));
        etJob = ((EditText) findViewById(R.id.company_job_et_job));
        btnSave = ((TextView) findViewById(R.id.company_job_btn_save));

        String company_job = App.LOGIN_USER.getCompany_job();
        if (!TextUtils.isEmpty(company_job)) {
            etCompanyName.setText(company_job.substring(0, company_job.lastIndexOf("-")));
            etJob.setText(company_job.substring(company_job.lastIndexOf("-") + 1));
        }
    }

    private String getCompanyJob(String company, String job) {
        return company + "-" + job;
    }

    @Override
    public void changeCompanyJobSuccess() {
        setResult(RESULT_OK);
        finish();
    }
}
