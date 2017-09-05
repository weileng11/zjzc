package cn.bs.zjzc.cache;

import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class ByteMemoryCache extends LruCache<String, byte[]> {
	public ByteMemoryCache(int maxSize) {
		super(maxSize);
	}
	
	public ByteMemoryCache() {
		this((int)(Runtime.getRuntime().maxMemory()/8));
	}
	
	@Override
	protected int sizeOf(String key, byte[] value) {
		return value.length;
	}
	
	public byte[] getBytes(String key){
		if(TextUtils.isEmpty(key)) return null;
		return get(key);
	}
	
	public void putKV(String key,byte[] value){
		if(TextUtils.isEmpty(key)){
			return;
		}
		
		if(value == null){
			return;
		}
		
		if(value.length == 0){
			return;
		}
		
		put(key, value);
	}
}