package lgw.java_JVM;

import java.lang.ref.SoftReference;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo3
 * @Description 测试被软引用（SoftReference）引用的对象会在内存不够时回收。
 * @Author Numblgw
 * @Date 2019/6/10 14:27
 */
public class Demo3 {

	private static final int MB_30 = 1024 * 1024 * 30;

	/**
	 * 内存足够时
	 */
	public static void softReferenceMemoryEnough() {
		Object o = new Object();
		SoftReference<Object> softReference = new SoftReference<>(o);
		System.out.println(o);
		System.out.println(softReference);
		o = null;
		System.gc();
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(o);
		System.out.println(softReference);
	}

	/**
	 * 添加jvm参数，将内存调小，保证出现OOM
	 * -Xms5m -Xmx5m -XX:+PrintGCDetails
	 */
	public static void softReferenceMemoryNotEnough() {
		Object o = new Object();
		SoftReference<Object> softReference = new SoftReference<>(o);
		System.out.println(o);
		System.out.println(softReference.get());
		o = null;
		try{
			byte[] bytes = new byte[MB_30];
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			System.out.println(o);
			System.out.println(softReference.get());
		}
	}

	public static void main(String[] args) {

		// softReferenceMemoryEnough();
		 softReferenceMemoryNotEnough();
	}
}
