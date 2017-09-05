package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import cn.bs.zjzc.App;
import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseFilterPhotoActivity;
import cn.bs.zjzc.bean.ProvinceCityListResponse;
import cn.bs.zjzc.dialog.MessageTools;
import cn.bs.zjzc.div.NormalPopuMenu;
import cn.bs.zjzc.model.bean.RegisterOrderTakerRequestBody;
import cn.bs.zjzc.model.bean.UploadFileBody;
import cn.bs.zjzc.presenter.RegisterOrderTakerPresenter;
import cn.bs.zjzc.ui.view.IRegisterOrderTakerView;
import cn.bs.zjzc.util.MD5;

/**
 * Created by Ming on 2016/5/31.
 */
public class AvRegisterOrderTaker extends BaseFilterPhotoActivity implements View.OnClickListener, IRegisterOrderTakerView {

    private Context mContext = this;
    private RegisterOrderTakerPresenter mRegisterOrderTakerPresenter;
    private TextView btnSelectCity;
    private EditText etRealName;
    private EditText etIdNumber;
    private ImageView frontPhoto;
    private ImageView backPhoto;
    private ImageView peoplePhoto;
    private ImageView btnAddFrontPhoto;
    private ImageView btnAddBackPhoto;
    private ImageView btnAddPeoplePhoto;
    private TextView btnCommit;

    private Map<String, UploadFileBody> photoMap;
    private NormalPopuMenu mNormalPopuMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_register_order_taker);
        mRegisterOrderTakerPresenter = new RegisterOrderTakerPresenter(this);
        photoMap = new HashMap<>();
        initViews();
        initEvents();
        //初始化图片裁剪参数
        setCropConfig(600, 460, 30, 23);
    }

    private void initEvents() {
        btnSelectCity.setOnClickListener(this);
        btnAddFrontPhoto.setOnClickListener(this);
        btnAddBackPhoto.setOnClickListener(this);
        btnAddPeoplePhoto.setOnClickListener(this);
        btnCommit.setOnClickListener(this);
    }

    private void initViews() {
        btnSelectCity = ((TextView) findViewById(R.id.register_order_taker_select_city));
        etRealName = ((EditText) findViewById(R.id.register_order_taker_et_real_name));
        etIdNumber = ((EditText) findViewById(R.id.register_order_taker_et_id_number));
        frontPhoto = ((ImageView) findViewById(R.id.register_order_taker_identification_photo1));
        backPhoto = ((ImageView) findViewById(R.id.register_order_taker_identification_photo2));
        peoplePhoto = ((ImageView) findViewById(R.id.register_order_taker_identification_photo3));
        btnAddFrontPhoto = ((ImageView) findViewById(R.id.register_order_taker_add_identification_photo1));
        btnAddBackPhoto = ((ImageView) findViewById(R.id.register_order_taker_add_identification_photo2));
        btnAddPeoplePhoto = ((ImageView) findViewById(R.id.register_order_taker_add_identification_photo3));
        btnCommit = ((TextView) findViewById(R.id.register_order_taker_btn_commit));

        //添加标记
        btnAddFrontPhoto.setTag("ID_photo");
        btnAddBackPhoto.setTag("ID_back_photo");
        btnAddPeoplePhoto.setTag("ID_people");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CROP_PHOTO_REQUEST_CODE) {
            String fileName = outputUri.getLastPathSegment();
            if (fileName.equals(getPhotoName((String) btnAddFrontPhoto.getTag()))) {//身份证正面照
                frontPhoto.setImageBitmap(getFilterPhoto());
            } else if (fileName.equals(getPhotoName((String) btnAddBackPhoto.getTag()))) {//身份证反面照
                backPhoto.setImageBitmap(getFilterPhoto());
            } else if (fileName.equals(getPhotoName((String) btnAddPeoplePhoto.getTag()))) {//手持身份证照
                peoplePhoto.setImageBitmap(getFilterPhoto());
            }
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.register_order_taker_select_city:
                showCityPickerView();
                break;
            case R.id.register_order_taker_add_identification_photo1:
            case R.id.register_order_taker_add_identification_photo2:
            case R.id.register_order_taker_add_identification_photo3:
                showSelectPhotoPupuMenu(v);
                break;
            case R.id.register_order_taker_btn_commit:
                registerOrderTaker();
                break;
        }
    }

    private void showCityPickerView() {
        OptionsPickerView pvOptions = new OptionsPickerView(mContext);

        //一级菜单(省)数组
        ArrayList<ProvinceCityListResponse.DataBean> pList = new ArrayList<>();
        //二级菜单(市)数组
        final ArrayList<ArrayList<ProvinceCityListResponse.DataBean.CityListBean>> pcList = new ArrayList<>();
        //添加省份数据到一级菜单数组
        pList.addAll(App.LOGIN_USER.getProvinceCityList());
        int size = pList.size();
        //循环添加城市到二级菜单
        for (int i = 0; i < size; i++) {
            ArrayList<ProvinceCityListResponse.DataBean.CityListBean> city_list = pList.get(i).city_list;
            //如果城市列表为空,则删除改省份
            if (city_list.size() == 0) {
                pList.remove(i);
                i--;
                size--;
                continue;
            }
            pcList.add(city_list);
        }
        //设置监听
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                String name = pcList.get(options1).get(option2).name;
                Log.i("onOptionsSelect", "onOptionsSelect: " + name);
                btnSelectCity.setText(name);
            }
        });

        //设置多级联动菜单的标题
        pvOptions.setTitle("选择城市");
        //设置多级联动菜单的数据
        pvOptions.setPicker(pList, pcList, true);
        //设置菜单是否循环显示
        pvOptions.setCyclic(false, false, false);
        pvOptions.show();
    }

    private void showSelectPhotoPupuMenu(final View v) {
        if (mNormalPopuMenu == null) {
            mNormalPopuMenu = new NormalPopuMenu(mContext);
            mNormalPopuMenu.setMenuGravity(Gravity.CENTER);
        }
        String[] items = getResources().getStringArray(R.array.str_array_setting_header);
        mNormalPopuMenu.show(v, "上传相片", items, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    fromAlbum();
                    addPhotoBody(v);
                } else if (position == 2) {
                    fromTakePhoto();
                    addPhotoBody(v);
                } else if (position == 3) {
                    mNormalPopuMenu.dismiss();
                }
            }
        });
    }

    private void addPhotoBody(View v) {
        UploadFileBody photoBody = new UploadFileBody();
        String tag = (String) v.getTag();
        photoBody.fileName = getPhotoName(tag);
        photoBody.file = savePhoto(photoBody.fileName);
        photoMap.put(tag, photoBody);
        mNormalPopuMenu.dismiss();
    }


    @NonNull
    private String getPhotoName(String key) {
        return MD5.getMD5(key) + ".jpg";
    }

    private void registerOrderTaker() {
        String address = btnSelectCity.getText().toString();
        String realName = etRealName.getText().toString();
        String idNumber = etIdNumber.getText().toString();
        if (TextUtils.isEmpty(address)) {
            showMsg("没有选择地址");
            return;
        }
        if (TextUtils.isEmpty(realName)) {
            showMsg("没有输入真实姓名");
            return;
        }
        if (TextUtils.isEmpty(idNumber)) {
            showMsg("没有输入身份证号码");
            return;
        }
        //身份证未验证
        if(!Pattern.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X|x)$)", idNumber)){
            showMsg("请输入正确的身份证号码");
            return;
        }

        if (!photoMap.containsKey("ID_photo")) {
            showMsg("请上传身份证正面照");
            return;
        }
        if (!photoMap.containsKey("ID_back_photo")) {
            showMsg("请上传身份证反面照");
            return;
        }
        if (!photoMap.containsKey("ID_people")) {
            showMsg("请上传手持身份证照");
            return;
        }
        mRegisterOrderTakerPresenter.registerOrderTaker(new RegisterOrderTakerRequestBody(address, realName, idNumber, photoMap));
    }

    @Override
    public void registOrderUserSuccess(String msg) {
        MessageTools.showDialog(this, "申请结果", msg, false, "我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AvRegisterOrderTaker.this.finish();
            }
        });

    }
}
