package cn.bs.zjzc.base;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import cn.bs.zjzc.App;


public class BaseFilterPhotoActivity extends BaseActivity {
    // 照相
    public static final int TAKE_PHOTO_REQUEST_CODE = 1;
    // 相册选择
    public static final int SELECTED_PHOTO_REQUEST_CODE = 2;
    // 相片裁剪
    public static final int CROP_PHOTO_REQUEST_CODE = 3;
    // 剪裁宽
    protected int outputX = 600;
    // 剪裁高
    protected int outputY = 300;
    // 宽高比例
    protected int aspectX = 2;
    protected int aspectY = 1;

    // 相片保存路径
    protected Uri outputUri;
    // 图片来源
    protected Uri imageFileUri = null;
    //相片名字

    /**
     * 设置裁剪参数
     *
     * @param ox
     * @param oy
     * @param ax
     * @param ay
     */
    protected void setCropConfig(int ox, int oy, int ax, int ay) {
        // 裁剪宽
        outputX = ox;
        // 裁剪高
        outputY = oy;
        // 宽高比
        aspectX = ax;
        aspectY = ay;

    }

    /**
     * 保存图片
     */
    protected File savePhoto(String name) {
        File photoFile = new File(App.cacheDir, name);
        photoFile.getParentFile().mkdirs();
        outputUri = Uri.fromFile(photoFile);
        /**
         * 如果不调用该方法，则无法保存图片
         */
        insertPhotoUri();
        return photoFile;
    }

    /**
     * uri插入系统相册
     */
    protected void insertPhotoUri() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.EXTRA_OUTPUT, outputUri.toString());
        getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues);
    }

    /**
     * 拍照
     */
    protected void fromTakePhoto() {
        imageFileUri = getContentResolver().insert(Media.EXTERNAL_CONTENT_URI,
                new ContentValues());
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);
        startActivityForResult(takePhotoIntent, TAKE_PHOTO_REQUEST_CODE);
    }

    /**
     * 相册选取
     */
    protected void fromAlbum() {
        Intent selectedIntent = new Intent(Intent.ACTION_PICK,
                Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(selectedIntent, SELECTED_PHOTO_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case TAKE_PHOTO_REQUEST_CODE:
                cropImageUri();
                break;
            case SELECTED_PHOTO_REQUEST_CODE:
                imageFileUri = data.getData();
                cropImageUri();
                break;
//		case CROP_PHOTO_REQUEST_CODE:
//			try {
//				InputStream imagein = getContentResolver().openInputStream(
//						outputUri);
//				Bitmap bitmap = BitmapFactory.decodeStream(imagein);
//				driverPhoto.setImageBitmap(bitmap);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			break;
        }
    }


    /**
     * 获取剪裁后的图片（bitmap对象）
     *
     * @return
     */
    protected Bitmap getFilterPhoto() {
        try {
            InputStream imagein = getContentResolver().openInputStream(
                    outputUri);
            Bitmap bitmap = BitmapFactory.decodeStream(imagein);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void cropImageUri() {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageFileUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("return-data", false);
        intent.putExtra("circleCrop", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不开启人脸识别
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, CROP_PHOTO_REQUEST_CODE);
    }


}
