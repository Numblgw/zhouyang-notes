package lgw.java_JUC;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.Objects;

/**
 * @ClassName Demo1
 * @Description double check 单例模式 为什么要使用 volatile 关键字修饰？
 * @Author Numblgw
 * @Date 2019/6/6 12:43
 */
public class Demo1 {
	public static void main(String[] args) {
		for(int i = 0 ; i < 2 ; i++) {
			// 不加 volatile 关键字的时候，这里有可能取不到name的值，概率很小，不好演示。
			new Thread(() -> System.out.println(Singleton.getInstance().getName())).start();
		}
	}
}

class Singleton {

	/**
	 * 如果不加 volatile 关键字，底层进行指令重排优化的时候，多线程环境下可能会出现问题。
	 */
	private static /* volatile */ Singleton instance;

	private String name;

	private Singleton() {
		name = "lgw";
	}

	/**
	 * 当不加 volatile 关键字的时候，这一步操作可能会进行指令重排序。
	 * 该操作大概可以分为三个步骤：
	 * 	1) 分配内存空间，创建对象。
	 * 	2) 初始化对象中的属性。
	 * 	3) 将引用变量 instance 指向该对象。
	 *
	 * 这三个步骤按顺序执行是没有问题的，但如果进行指令重排。执行顺序变成了 1 3 2 就会存在问题：
	 * 	当步骤3执行完 instance 指向新创建的对象时，该对象还没有进行初始化。这个时候如果有其他线程调用 getInstance() 方法
	 * 	获得的对象是还没有初始化完成的，读取 name 域获取不到值。
	 *
	 * @return
	 */
	public static Singleton getInstance() {
		if(instance == null) {
			synchronized(Singleton.class) {
				if(instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}

	public String getName() {
		return name;
	}
}