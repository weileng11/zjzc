package cn.bs.zjzc.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 输入输出流工具类
 * 
 * @author pt-xuejj
 * 
 */
public final class StreamUtil {
	/**
	 * 释放输入输出流
	 * 
	 * @param objs
	 */
	public final static void releaseResObjects(Object... objs) {
		if (objs == null) {
			return;
		}

		if (objs.length == 0) {
			return;
		}

		for (Object obj : objs) {
			if (obj != null) {
				releaseResObject(obj);
			}
		}
	}

	private final static void releaseResObject(Object obj) {
		synchronized (obj) {
			try {
				if (obj instanceof Flushable) {
					((Flushable) obj).flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (obj instanceof Closeable) {
						((Closeable) obj).close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 输入流写到输出流
	 * 
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public final static void inputStream2outputStream(InputStream in,
			OutputStream out) throws IOException {
		inputStream2outputStream(in, out, 8192);
	}
	/**
	 * 输入流写到输出流
	 * @param in
	 * @param out
	 * @param buffSize 缓存大小
	 * @throws IOException
	 */
	public final static void inputStream2outputStream(InputStream in,
			OutputStream out,int buffSize) throws IOException {
		if (in == null || out == null) {
			return;
		}

		int len = -1;
		byte[] buff = new byte[buffSize];
		while ((len = in.read(buff)) > 0) {
			out.write(buff, 0, len);
		}
	}
	public static byte[] inputStream2ByteArray(InputStream ins)throws Exception {
		//内存流，字节数组输出流
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		byte[]buff=new byte[1024];
		int len=-1;
		//直到读到流的尾部
		while((len=ins.read(buff))!=-1){
			baos.write(buff, 0, len);
		}
		baos.close();
		ins.close();
		return baos.toByteArray();
	}
}