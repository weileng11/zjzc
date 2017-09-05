package cn.bs.zjzc;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import cn.bs.zjzc.model.bean.User;

/**
 * Created by Ming on 2016/6/3.
 */
public class App extends Application {
    // 应用缓存目录
    public static File cacheDir = null;
    public static User LOGIN_USER;

    @Override
    public void onCreate() {
        super.onCreate();
        initUser();
        createCacheDIR();
        //百度地图 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
//        bugly初始化
        initBugly();
    }

    private void initBugly() {
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_ACTIVITIES);
            CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(this);
            strategy.setAppVersion(packageInfo.versionName);      //App的版本
            strategy.setAppPackageName(packageInfo.packageName);  //App的包名
            CrashReport.initCrashReport(getApplicationContext(), "900040678", false, strategy);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUser() {
        SharedPreferences sp = getSharedPreferences("login_data", MODE_PRIVATE);
        //获取最后登录的帐号
        String lastLogin = sp.getString("last_login", "");
        //初始化登录帐号为最后登录帐号,如果用户登录新账号,则会重新设置为新的登录帐号
        LOGIN_USER = new User(getApplicationContext(), lastLogin);
    }

    private void createCacheDIR() {
        // 创建缓存目录
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(getApplicationContext(), "无存储卡！无法创建缓存目录!",
                    Toast.LENGTH_SHORT).show();
        } else {
            cacheDir = getExternalFilesDir(AllConsts.app.cacheDirName);
            // 创建一次性缓存目录
            File tempDir = new File(cacheDir, AllConsts.app.tempDir);
            File cacheDataDir = new File(cacheDir, AllConsts.app.cacheDir);
            tempDir.mkdirs();
            try {
                AllConsts.cache.onceCache.init(tempDir);
                // 永久缓存
                AllConsts.cache.cacheData.init(cacheDataDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 应用值栈，代替intent的传值
     */
    Hashtable<String, Object> valueStack = new Hashtable<String, Object>() {
        @Override
        public synchronized Object get(Object key) {
            Object value = super.get(key);
            remove(key);

            return value;
        }
    };

    public static <T> T getVa() {
        T t = null;
        return t;
    }

    /**
     * 从值栈中获取值
     *
     * @param key
     * @return
     */
    public <T> T getValueFromStack(String key, T... defaultValues) {
        T defaultValue = null;
        if (defaultValues != null && defaultValues.length > 0) {
            defaultValue = defaultValues[0];
        }

        if (valueStack.isEmpty())
            return defaultValue;
        Object obj = valueStack.get(key);
        if (obj == null)
            return defaultValue;

        return (T) obj;
    }

    /**
     * 键值对存入值栈
     *
     * @param key
     * @param value
     */
    public void putValue2Stack(String key, Object value) {
        if (TextUtils.isEmpty(key) || value == null)
            return;
        valueStack.put(key, value);
    }

}
