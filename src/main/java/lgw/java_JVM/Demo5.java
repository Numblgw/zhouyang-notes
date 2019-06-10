package lgw.java_JVM;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @ClassName Demo5
 * @Description
 * @Author Numblgw
 * @Date 2019/6/10 16:24
 */
public class Demo5 {
	public static void main(String[] args) {
		Object o = new Object();
		ReferenceQueue<PhantomReference> referenceQueue = new ReferenceQueue<>();
	}
}
