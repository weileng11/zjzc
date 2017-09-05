package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoFrameLayout;

import java.io.File;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseFilterPhotoActivity;
import cn.bs.zjzc.div.CircleImageView;
import cn.bs.zjzc.div.NormalOptionView;
import cn.bs.zjzc.div.NormalPopuMenu;
import cn.bs.zjzc.div.Stars;
import cn.bs.zjzc.net.RequestUrl;
import cn.bs.zjzc.presenter.PersonalInfoPresenter;
import cn.bs.zjzc.ui.view.IPersonalInfoView;
import cn.bs.zjzc.util.DensityUtils;

/**
 * Created by Ming on 2016/5/21.
 */
public class AvPersonalInfo extends BaseFilterPhotoActivity implements View.OnClickListener, IPersonalInfoView {

    private static final int INDUSTRY_REQUEST_CODE = 11;
    private static final int NICK_NAME_REQUEST_CODE = 12;
    private static final int COMPANY_JOB_REQUEST_CODE = 13;
    private Context mContext = this;
    private PersonalInfoPresenter mPersonalInfoPresenter;
    private NormalOptionView optionHeader;
    private NormalOptionView optionNickName;
    private NormalOptionView optionGender;
    private NormalOptionView optionAge;
    private NormalOptionView optionPhoneNumber;
    private NormalOptionView optionLevel;
    private NormalOptionView optionLevelStar;
    private NormalOptionView optionIndustry;
    private NormalOptionView optionCompanyJob;
    private NormalOptionView optionVerification;
    private NormalPopuMenu mNormalPopuMenu;
    private CircleImageView mHeaderImage;//头像图片
    private Stars stars; //用户等级中的星星
    private String USER_PHOTO = "/temp/user_photo.jpg";
    private File userPhotoFile;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_personal_info);
        initViews();
        mPersonalInfoPresenter = new PersonalInfoPresenter(this);
        initEvents();
        setCropConfig(200, 200, 1, 1);
    }

    private void initEvents() {
        optionHeader.setOnClickListener(this);
        optionNickName.setOnClickListener(this);
        optionGender.setOnClickListener(this);
        optionAge.setOnClickListener(this);
        optionPhoneNumber.setOnClickListener(this);
        optionLevel.setOnClickListener(this);
        optionLevelStar.setOnClickListener(this);
        optionIndustry.setOnClickListener(this);
        optionCompanyJob.setOnClickListener(this);
        optionVerification.setOnClickListener(this);
    }

    private void initViews() {
        optionHeader = ((NormalOptionView) findViewById(R.id.personal_info_option_header));
        optionNickName = ((NormalOptionView) findViewById(R.id.personal_info_option_nick_name));
        optionGender = ((NormalOptionView) findViewById(R.id.personal_info_option_gender));
        optionAge = ((NormalOptionView) findViewById(R.id.personal_info_option_age));
        optionPhoneNumber = ((NormalOptionView) findViewById(R.id.personal_info_option_phone_number));
        optionLevel = ((NormalOptionView) findViewById(R.id.personal_info_option_level));
        optionLevelStar = ((NormalOptionView) findViewById(R.id.personal_info_option_level_star));
        optionIndustry = ((NormalOptionView) findViewById(R.id.personal_info_option_industry));
        optionCompanyJob = ((NormalOptionView) findViewById(R.id.personal_info_option_company_job));
        optionVerification = ((NormalOptionView) findViewById(R.id.personal_info_option_employer_verification));

        mNormalPopuMenu = new NormalPopuMenu(mContext);
        initHeaderImage();
        initStar();
        updateData();
    }


    private void updateData() {
        Picasso.with(mContext).load(App.LOGIN_USER.getHead_img()).into(mHeaderImage);
        optionNickName.setContent(App.LOGIN_USER.getName());
        optionGender.setContent(App.LOGIN_USER.getSex());
        optionAge.setContent(App.LOGIN_USER.getAge());
        optionPhoneNumber.setContent(App.LOGIN_USER.getPhone());
        optionLevel.setContent(App.LOGIN_USER.getLevel());
        stars.setStars(Integer.parseInt(App.LOGIN_USER.getStar()));
        optionIndustry.setContent(App.LOGIN_USER.getIndustry());
        optionCompanyJob.setContent(App.LOGIN_USER.getCompany_job());
        optionVerification.setContent(App.LOGIN_USER.getApply_state());

//        mHeaderImage.setImageResource(R.mipmap.ic_launcher);
//        optionNickName.setContent("张三");
//        optionGender.setContent("男");
//        optionAge.setContent("90后");
//        optionPhoneNumber.setContent("12345678");
//        optionLevel.setContent("新用户");
//        stars.setStars(3);
//        optionIndustry.setContent("计算机");
//        optionCompanyJob.setContent("博尚-开发");
//        optionVerification.setContent("未验证");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_info_option_header:
                showHeaderPopuMenu(v);
                break;
            case R.id.personal_info_option_nick_name:
                startActivityForResult(new Intent(mContext, AvNickName.class), NICK_NAME_REQUEST_CODE);
                break;
            case R.id.personal_info_option_gender:
                showGenderPopuMenu(v);
                break;
            case R.id.personal_info_option_age:
                ShowAgePopuMenu(v);
                break;
            case R.id.personal_info_option_phone_number:
                startActivity(new Intent(mContext, AvPhoneNumber.class));
                break;
            case R.id.personal_info_option_level:
                startActivity(new Intent(mContext, AvUserLevel.class));
                break;
            case R.id.personal_info_option_level_star:
                Intent intent = new Intent(this, AvCommdWebView.class);
                intent.putExtra("title", "星级说明");
                intent.putExtra("url", RequestUrl.getRequestPath(RequestUrl.SubPaths.starRule));
                startActivity(intent);
                break;
            case R.id.personal_info_option_industry:
                startActivityForResult(new Intent(mContext, AvIndustry.class), INDUSTRY_REQUEST_CODE);
                break;
            case R.id.personal_info_option_company_job:
                startActivityForResult(new Intent(mContext, AvCompanyJob.class), COMPANY_JOB_REQUEST_CODE);
                break;
            case R.id.personal_info_option_employer_verification:
                if (App.LOGIN_USER.getApply_state().equals("未认证")) {
                    startActivity(new Intent(mContext, AvRegisterOrderTaker.class));
                } else if (App.LOGIN_USER.getApply_state().equals("审核中")) {
                    startActivity(new Intent());
                } else if (App.LOGIN_USER.getApply_state().equals("认证通过")) {
                    startActivity(new Intent(mContext, AvVerificationInfo.class));
                } else if (App.LOGIN_USER.getApply_state().equals("认证不通过")) {
                    startActivity(new Intent(mContext, AvRegisterOrderTaker.class));
                }
                //startActivity(new Intent(mContext, AvRegisterOrderTaker.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CROP_PHOTO_REQUEST_CODE:
                    mPersonalInfoPresenter.changeHeader(userPhotoFile);
                    break;
                case INDUSTRY_REQUEST_CODE:
                    optionIndustry.setContent(App.LOGIN_USER.getIndustry());
                    break;
                case NICK_NAME_REQUEST_CODE:
                    optionNickName.setContent(App.LOGIN_USER.getName());
                    break;
                case COMPANY_JOB_REQUEST_CODE:
                    optionCompanyJob.setContent(App.LOGIN_USER.getCompany_job());
                    break;
            }
        }
    }

    /**
     * 选择年龄弹窗菜单
     *
     * @param v
     */
    private void ShowAgePopuMenu(View v) {
        final String[] items = getResources().getStringArray(R.array.str_array_setting_age);
        mNormalPopuMenu.show(v, "年龄选择", items, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //防止点击标题关闭弹窗
                if (position == 0) {
                    return;
                }
                String age = items[position - 1];
                if (!age.equals(optionAge.getContent())) {
                    mPersonalInfoPresenter.changeAge(age);
                }
                mNormalPopuMenu.dismiss();
            }
        });
    }

    /**
     * 选择性别弹窗菜单
     *
     * @param v
     */
    private void showGenderPopuMenu(View v) {
        final String[] items = getResources().getStringArray(R.array.str_array__setting_gender);
        mNormalPopuMenu.show(v, "性别选择", items, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //防止点击标题关闭弹窗
                if (position == 0) {
                    return;
                }
                String gender = items[position - 1];
                if (!gender.equals(optionGender.getContent())) {
                    mPersonalInfoPresenter.changeGender(gender);
                }
                mNormalPopuMenu.dismiss();
            }
        });
    }

    /**
     * 上传图片弹窗菜单
     *
     * @param v
     */
    private void showHeaderPopuMenu(View v) {
        String[] items = getResources().getStringArray(R.array.str_array_setting_header);
        mNormalPopuMenu.show(v, "上传相片", items, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        userPhotoFile = savePhoto(USER_PHOTO);
                        fromAlbum();
                        mNormalPopuMenu.dismiss();
                        break;
                    case 2:
                        userPhotoFile = savePhoto(USER_PHOTO);
                        fromTakePhoto();
                        mNormalPopuMenu.dismiss();
                        break;
                    case 3:
                        mNormalPopuMenu.dismiss();
                        break;
                }
            }
        });
    }

    /**
     * 初始化综合评价的星星
     */
    private void initStar() {
        stars = new Stars(mContext);
        optionLevelStar.setRightView(stars);
    }

    /**
     * 初始化头像ImageView
     */
    private void initHeaderImage() {
        mHeaderImage = new CircleImageView(mContext);
        AutoFrameLayout.LayoutParams params = new AutoFrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        mHeaderImage.setLayoutParams(params);
        optionHeader.setRightView(mHeaderImage);
    }

    @Override
    public void setHeader() {
        //清除Picasso的缓存,如果不清除只有第一次修改头像图片能更新,之后修改会一直获取第一次修改缓存的图片
        Picasso.with(mContext).invalidate(App.LOGIN_USER.getHead_img());
        Picasso.with(mContext).load(App.LOGIN_USER.getHead_img()).into(mHeaderImage);
    }

    @Override
    public void setGender(String gender) {
        optionGender.setContent(gender);
    }

    @Override
    public void setAge(String age) {
        optionAge.setContent(age);
    }
}