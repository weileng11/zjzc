package cn.bs.zjzc.cache;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.bs.zjzc.util.StreamUtil;


/**
 * 硬盘缓存+内存缓存
 * 
 * @author pt-xuejj
 */
public class CacheManager {
	/**
	 * 开辟10M空间缓存文件(硬盘缓存)
	 */
	final int cacheDirMaxSize = 10 * 1024 * 1024;
	/* commonUtil.getAppVersion(context) */

	// 缓存版本号
	final int cacheVersionCode = 1;

	private final ByteMemoryCache byteMemoryCache = new ByteMemoryCache();
	private DiskLruCache diskLruCache;

	private File cacheDir;

	public void init(File dir) throws IOException {
		this.cacheDir = dir;

		/**
		 * 如果是设置成应用程序版本，则每次应用升级都要重新下载缓存
		 */
		diskLruCache = DiskLruCache.open(cacheDir, cacheVersionCode, 1,
				cacheDirMaxSize);


	}

	/**
	 * 从缓存中获取输入流(同步)
	 * 
	 * @param key
	 * @throws IOException
	 */
	public byte[] get(String key) throws IOException {
		byte[] bytes = byteMemoryCache.getBytes(key);

		if (bytes != null) {
			return bytes;
		}

		DiskLruCache.Snapshot snapshot = null;
		ByteArrayOutputStream bout = null;
		try {
			snapshot = diskLruCache.get(key);
			bout = new ByteArrayOutputStream();
			if (snapshot != null) {
				InputStream tempIn = snapshot.getInputStream(0);
				StreamUtil.inputStream2outputStream(tempIn, bout);
				byteMemoryCache.put(key, bytes = bout.toByteArray());
				return bytes;
			}
		} finally {
			StreamUtil.releaseResObjects(snapshot, bout);
		}

		return null;
	}

	/**
	 * 字节数组存入缓存
	 * 
	 * @param key
	 * @param value
	 * @param
	 * @throws IOException
	 */
	public synchronized void put(String key, byte[] value) throws IOException {
		if (TextUtils.isEmpty(key) || value == null) {
			return;
		}

		// 写入内存缓存
		byteMemoryCache.put(key, value);
		// 写入硬盘缓存
		ByteArrayInputStream in = new ByteArrayInputStream(value);
		putValue2disk(key, value);
		StreamUtil.releaseResObjects(in);
	}

	/**
	 * 写入硬盘缓存
	 * 
	 * @param key
	 * @param
	 * @throws IOException
	 */
	private synchronized void putValue2disk(final String key, final byte[] data)
			throws IOException {
		DiskLruCache.Editor editor = null;
		OutputStream out = null;
		ByteArrayInputStream bin = null;
		try {
			bin = new ByteArrayInputStream(data);
			editor = diskLruCache.edit(key);
			out = editor.newOutputStream(0);
			// 缓存写到本地硬盘
			StreamUtil.inputStream2outputStream(bin, out);
			editor.commit();
			diskLruCache.flush();
		} catch (IOException e) {
			e.printStackTrace();
			if (editor != null) {
				editor.abort();
			}
			throw new IOException(e.getMessage());
		} finally {
			StreamUtil.releaseResObjects(out, bin);
		}
	}

	public void removeCache(String key) throws IOException {
		if (TextUtils.isEmpty(key)) {
			return;
		}

		byteMemoryCache.remove(key);
		diskLruCache.remove(key);
	}

	/**
	 * 清除所有缓存
	 * 
	 * @throws IOException
	 */
	public void clearCaches() throws IOException {
		byteMemoryCache.evictAll();
		diskLruCache.delete();
	}

	/**
	 * 关闭硬盘缓存。
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		if (diskLruCache != null)
			diskLruCache.close();
	}
}