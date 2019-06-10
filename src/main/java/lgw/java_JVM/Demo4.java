package lgw.java_JVM;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo4
 * @Description 弱引用（WeakReference） 不管内存是否够用，只要进行垃圾回收就会被回收。
 * @Author Numblgw
 * @Date 2019/6/10 15:18
 */
public class Demo4 {
	public static void main(String[] args) {
		Object o = new Object();
		WeakReference<Object> weakReference = new WeakReference<>(o);
		System.out.println(o);
		System.out.println(weakReference.get());
		o = null;
		System.gc();
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(o);
		// gc时 Object对象只被弱引用引用着，所以gc之后对象被回收，打印结果是 null
		System.out.println(weakReference.get());
	}
}
