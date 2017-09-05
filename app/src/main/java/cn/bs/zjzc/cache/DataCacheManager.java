package cn.bs.zjzc.cache;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cn.bs.zjzc.util.StreamUtil;

public class DataCacheManager extends CacheManager {
	/**
	 * 
	 * 调用 : getBaseDataTypeValue("key",Integer.class,T 0)
	 * 
	 * 获取指定类型的值，该类型有一个String参数的构造方法
	 * 获取基本数据类型的值，如int,byte,long,float,String,Double等。
	 * @param key
	 * @param clazz 欲获取基本数据类型的包装类型
	 * @param defaultValue
	 * @return
	 * @throws IOException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public <T>T getBaseDataTypeValue(String key,Class clazz,T defaultValue) throws IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(TextUtils.isEmpty(key)) return defaultValue;
		if(clazz == null) return defaultValue;
		byte[] temp = get(key);
		if(temp == null) return defaultValue;
		String tempValue = new String(temp);
		
		Constructor c = clazz.getConstructor(String.class);
		if(c == null) return defaultValue;
		return (T) c.newInstance(tempValue);
	}
	
	/**
	 * 存储字符串类型值
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public synchronized void putStringValue(String key,String value) throws IOException{
		if(TextUtils.isEmpty(value)) return ;
		byte[] temp = value.getBytes();
		put(key, temp);
	}
	
	/**
	 * 获取序列化对象
	 * @param key
	 * @param clazz	欲获取对象的class
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws NoSuchMethodException 
	 */
	public <T>T getSerializableObject(String key,Class clazz) throws IOException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		if(TextUtils.isEmpty(key)) return null;
		if(!(clazz instanceof Serializable)) return null;
		byte[] temp = get(key);
		
		if(temp == null) return null;
		
		if(clazz.isAssignableFrom(List.class)){
			String listString = getBaseDataTypeValue(key, String.class, "");
			return (T) new Gson().fromJson(listString, clazz);
		}
		
		ObjectInputStream objIn = null;
		ByteArrayInputStream bin = null;
		try {
			bin = new ByteArrayInputStream(temp);
			objIn = new ObjectInputStream(bin);
			Object obj = objIn.readObject();
			if(!clazz.isInstance(obj)) return null;
			
			return (T) obj;
		} finally{
			StreamUtil.releaseResObjects(objIn,bin);
		}
	}
	
	/**
	 * 存储序列化对象
	 * @param key
	 * @param obj
	 * @throws IOException 
	 */
	public synchronized void putSerializableObject(String key,Serializable obj) throws IOException{
		if(TextUtils.isEmpty(key)) return;
		if(obj == null) return ;

		ByteArrayOutputStream bout = null;
		ObjectOutputStream objOut = null;
		try {
			bout = new ByteArrayOutputStream(8192);
			objOut = new ObjectOutputStream(bout);
			objOut.writeObject(obj);
		}finally{
			StreamUtil.releaseResObjects(bout,objOut);
		}
	}
	
	/**
	 * 从缓存中获取bitmap
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public Bitmap getBitmap(String key) throws IOException{
		if(TextUtils.isEmpty(key)) return null;
		byte[] temp = get(key);
		
		if(temp == null) return null;
		
		InputStream is = null;
		try {
			is = new ByteArrayInputStream(temp);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			return bitmap;
		} finally{
			StreamUtil.releaseResObjects(is);
		}
	}
	
	/**
	 * 存储bitmap
	 */
	public synchronized void putBitmap(String key,Bitmap bitmap,CompressFormat format){
		if(TextUtils.isEmpty(key)) return;
		if(bitmap == null) return ;
		ByteArrayOutputStream bout = new ByteArrayOutputStream(8192);
		bitmap.compress(format, 100, bout);
		StreamUtil.releaseResObjects(bout);
	}
}
