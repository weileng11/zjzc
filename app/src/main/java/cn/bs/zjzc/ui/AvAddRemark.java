package cn.bs.zjzc.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bs.zjzc.R;
import cn.bs.zjzc.base.BaseActivity;
import cn.bs.zjzc.div.TopBarView;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by Ming on 2016/5/24.
 */
public class AvAddRemark extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    private static final int REQUEST_MULTI_IMAGE = 10;//选择多张图片
    private static final int REQUEST_IMAGE_1 = 11;//选择单张图片,用于替换imgPhoto1的图片
    private static final int REQUEST_IMAGE_2 = 12;//选择单张图片,用于替换imgPhoto2的图片
    private static final int REQUEST_IMAGE_3 = 13;//选择单张图片,用于替换imgPhoto3的图片
    private static final int REQUEST_IMAGE_4 = 14;//选择单张图片,用于替换imgPhoto4的图片
    private Context mContext = this;
    private TextView btnConfirm;
    private EditText etRemarkContent;
    private TopBarView topBar;
    private AutoLinearLayout photoLayout;
    private ImageView btnAddPhoto;
    private ImageView imgPhoto1;
    private ImageView imgPhoto2;
    private ImageView imgPhoto3;
    private ImageView imgPhoto4;
    private ArrayList<String> photoPathList = new ArrayList<>();
    private Bitmap[] bitmap = new Bitmap[4];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.av_add_remark);
        initViews();
        initEvents();

        initIntent();

    }

    private void initIntent() {
        Intent intent = getIntent();
        try {
            etRemarkContent.setText(intent.getStringExtra("remark_content"));
            List<String> list = intent.getStringArrayListExtra("photo");
            //添加图片
            photoPathList.addAll(list);
            for (int i = 0; i < photoPathList.size(); i++) {
                String photoPath = photoPathList.get(i);
                ImageView imageView = (ImageView) photoLayout.getChildAt(i);
                imageView.setVisibility(View.VISIBLE);
                imageView.setTag(photoPath);//设置tag,便于长按删除时删除对应的photoPathList的路径
                imageView.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                if (i == 4) {
                    //最多添加4张,到了第4张则隐藏添加按钮
                    btnAddPhoto.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initEvents() {
        //单击事件
        btnConfirm.setOnClickListener(this);
        btnAddPhoto.setOnClickListener(this);
        imgPhoto1.setOnClickListener(this);
        imgPhoto2.setOnClickListener(this);
        imgPhoto3.setOnClickListener(this);
        imgPhoto4.setOnClickListener(this);
        //长按事件
        imgPhoto1.setOnLongClickListener(this);
        imgPhoto2.setOnLongClickListener(this);
        imgPhoto3.setOnLongClickListener(this);
        imgPhoto4.setOnLongClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            switch (requestCode) {
                case REQUEST_MULTI_IMAGE:
                    //添加图片
                    photoPathList.addAll(paths);
                    for (int i = 0; i < photoPathList.size(); i++) {
                        String photoPath = photoPathList.get(i);
                        ImageView imageView = (ImageView) photoLayout.getChildAt(i);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setTag(photoPath);//设置tag,便于长按删除时删除对应的photoPathList的路径
                        imageView.setImageBitmap(BitmapFactory.decodeFile(photoPath));
                        if (i == 4) {
                            //最多添加4张,到了第4张则隐藏添加按钮
                            btnAddPhoto.setVisibility(View.GONE);
                        }
                    }
                    break;
                case REQUEST_IMAGE_1:
                    imgPhoto1.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
                    break;
                case REQUEST_IMAGE_2:
                    imgPhoto2.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
                    break;
                case REQUEST_IMAGE_3:
                    imgPhoto3.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
                    break;
                case REQUEST_IMAGE_4:
                    imgPhoto4.setImageBitmap(BitmapFactory.decodeFile(paths.get(0)));
                    break;
            }
        }
    }

    private void initViews() {
        topBar = ((TopBarView) findViewById(R.id.add_remark_top_bar));
        etRemarkContent = ((EditText) findViewById(R.id.add_remark_content));
        photoLayout = ((AutoLinearLayout) findViewById(R.id.add_remark_photo_layout));
        btnAddPhoto = ((ImageView) findViewById(R.id.add_remark_add_photo));
        imgPhoto1 = ((ImageView) findViewById(R.id.add_remark_photo1));
        imgPhoto2 = ((ImageView) findViewById(R.id.add_remark_photo2));
        imgPhoto3 = ((ImageView) findViewById(R.id.add_remark_photo3));
        imgPhoto4 = ((ImageView) findViewById(R.id.add_remark_photo4));
        btnConfirm = ((TextView) findViewById(R.id.add_remark_confirm));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_remark_add_photo:
                selectPhoto();
                break;
            case R.id.add_remark_confirm:
                Intent intent = new Intent();
                //返回备注内容
                intent.putExtra("remark_content", etRemarkContent.getText().toString());
                //返回备注图片
                intent.putStringArrayListExtra("photo", photoPathList);
                setResult(RESULT_OK, intent);
                finish();
                break;
            //单击图片替换图片
            case R.id.add_remark_photo1:
                changePhoto(REQUEST_IMAGE_1);
                break;
            case R.id.add_remark_photo2:
                changePhoto(REQUEST_IMAGE_2);
                break;
            case R.id.add_remark_photo3:
                changePhoto(REQUEST_IMAGE_3);
                break;
            case R.id.add_remark_photo4:
                changePhoto(REQUEST_IMAGE_4);
                break;
        }
    }

    /**
     * 长按删除图片
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.add_remark_photo1:
            case R.id.add_remark_photo2:
            case R.id.add_remark_photo3:
            case R.id.add_remark_photo4:
                removePhoto(v);
                break;
        }
        return true;
    }

    /**
     * 删除图片
     *
     * @param v
     */
    private void removePhoto(View v) {
        v.setVisibility(View.GONE);
        btnAddPhoto.setVisibility(View.VISIBLE);
        photoPathList.remove(v.getTag());//删除当前图片的地址
    }

    /**
     * 进入图片选择界面,选择多张
     */
    private void selectPhoto() {
        MultiImageSelector.create()
                .showCamera(false) // show camera or not. true by default
                .multi() // single mode
                .count(4 - photoPathList.size())
                .start(AvAddRemark.this, REQUEST_MULTI_IMAGE);
    }

    /**
     * 进入图片选择界面,选择单张
     *
     * @param requestCode
     */
    private void changePhoto(int requestCode) {
        MultiImageSelector.create()
                .showCamera(false) // show camera or not. true by default
                .single() // single mode
                .start(AvAddRemark.this, requestCode);
    }


}
