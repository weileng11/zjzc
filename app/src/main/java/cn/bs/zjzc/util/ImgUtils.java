package cn.bs.zjzc.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Han on 2016/5/6.
 */
public class ImgUtils {
    /**缩放的参考大小  可以想象成k*/
    private static int scal_size=100;
    /**
     * 压缩操作
     * @param img_byte
     * @return
     */

    public  static BitmapFactory.Options getOption(byte[] img_byte) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        int length=img_byte.length;
        if (length >= (scal_size * 1024 * 36)) {
            option.inSampleSize = 6;// 变成原来的1/36
        } else if (length >= (scal_size * 1024 * 25)) {
            option.inSampleSize = 5;// 变成原来的1/25
        } else if (length >= (scal_size * 1024 * 16)) {
            option.inSampleSize = 4;// 变成原来的1/16
        } else if (length >= (scal_size * 1024 * 9)) {
            option.inSampleSize = 3;// 变成原来的1/9
        } else if (length > (scal_size * 1024 * 1)) {// 120K
            option.inSampleSize = 2;// 变成原来的1/4
        } else {
            option.inSampleSize = 1;// 变成原来的1/1 //103K
        }
        System.out.println("---------option.inSampleSize:"+option.inSampleSize);
        return option;
    }

    public static boolean doScale(File file,File outFile)  {
        if(!file.exists())return false;
        try{
            if(!outFile.exists())outFile.createNewFile();

            FileInputStream ins = new FileInputStream(file);
            byte[] img_byte = StreamUtil.inputStream2ByteArray(ins);
            // 拿到之后进行压缩，文件名进行编码，并保存到本地
            BitmapFactory.Options options =getOption(img_byte);
            Bitmap bmp = BitmapFactory.decodeByteArray(img_byte, 0,
                    img_byte.length, options);
            FileOutputStream ous = new FileOutputStream(outFile);
            // CompressFormat格式 quality 质量 stream输出流
            bmp.compress(Bitmap.CompressFormat.JPEG, 80, ous);
            ous.close();
            return true;
        }catch (Exception e){
            L.e("image doscall exception :\n");
            e.printStackTrace();
            return false;
        }
    }
    public static String packagename="cn.bs.zthd_employer";
    public static String cachename="han";
    /**
     * 得到本地目录
     * @return
     */
    public static String getLocalCachePath() {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            return Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+cachename;
        }else{
            return "/data/data"+packagename+"/"+cachename;
        }
    }

    /**清除图片缓存
     */
    public static boolean clearCacha(){
        boolean isOk=false;
        try {
            List<File> fils=new ArrayList<File>();
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                fils.add(new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+cachename));
            }
            File local_file=new File("/data/data"+packagename+"/"+cachename);
            fils.add(local_file);
            for(File f:fils){
                if(f.exists()){
                    for(File temp:f.listFiles()){
                        temp.delete();
                    }
                }
            }
            isOk=true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isOk;
    }

    /**
     * 保存图片
     * @param photo 图片bmp
     * @param spath 路径，包含文件名
     */
    public static  void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
