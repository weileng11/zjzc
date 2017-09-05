package cn.bs.zjzc.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mgc on 2016/6/6.
 */
public class DBUtils {
    private static final String DB_NAME = "zjzc.db";
    private static final String DB_TABLE = "historyAddress";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "address";

    private SQLiteDatabase db;
    private final Context context;
    private DBOpenHelper dbOpenHelper;

    public DBUtils(Context context) {
        this.context = context;
    }

    /**
     * Close the database
     */
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /**
     * Open the database
     */
    public void open() throws SQLiteException {
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbOpenHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbOpenHelper.getReadableDatabase();
        }
    }

    /**
     * 添加数据
     */
    public long insert(String json) {
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_NAME, json);
        return db.insert(DB_TABLE, null, newValues);
    }

    /**
     * 查询所有数据
     */
    public List<String> queryAllData() {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME},
                null, null, null, null, null);
        return ConvertToStorage(results);
    }

    /**
     * 查询单个数据
     */
    public List<String> queryOneData(String json) {
        Cursor results = db.query(DB_TABLE, new String[]{KEY_ID, KEY_NAME},
                KEY_NAME + "=" + json, null, null, null, null);
        return ConvertToStorage(results);
    }

    /**
     * 通过Cursor取出所有数据
     */
    private List<String> ConvertToStorage(Cursor cursor) {
        int resultCounts = cursor.getCount();
        if (resultCounts == 0 || !cursor.moveToFirst()) {
            return null;
        }
        List<String> jsonList = new ArrayList<>();
        for (int i = 0; i < resultCounts; i++) {
            int _id = cursor.getInt(0);
            String json = cursor.getString(cursor.getColumnIndex(KEY_NAME));
            jsonList.add(json);
            cursor.moveToNext();
        }
        return jsonList;
    }

    /**
     * 清除所有数据
     */
    public long deleteAllData() {
        return db.delete(DB_TABLE, null, null);
    }

    /**
     * 清除单个数据
     */
    public long deleteOneData(String json) {
        return db.delete(DB_TABLE, KEY_NAME + "=" + json, null);
    }

    /**
     * 更新单个数据
     */
    public long updateOneData(long id, String orderId) {
        ContentValues updateValues = new ContentValues();

        updateValues.put(KEY_NAME, orderId);

        return db.update(DB_TABLE, updateValues, KEY_ID + "=" + id, null);
    }

    /**
     * 静态Helper类，用于建立、更新和打开数据库
     */
    private static class DBOpenHelper extends SQLiteOpenHelper {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        private static final String DB_CREATE = "create table " +
                DB_TABLE + " (" + KEY_ID + " integer primary key autoincrement, " +
                KEY_NAME + " varchar(60) not null);";

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DB_CREATE);
            L.d("SQLite", "数据库创建成功");
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
            _db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(_db);
            L.d("SQLite", "onUpgrade");
        }
    }
}
