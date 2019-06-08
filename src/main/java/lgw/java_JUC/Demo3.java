package lgw.java_JUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName Demo3
 * @Description 演示乐观锁在高并发下，一直更新数据失败，一直重做的问题。
 * @Author Numblgw
 * @Date 2019/6/7 12:56
 */
public class Demo3 {
	static AtomicInteger count = new AtomicInteger(0);

	public static void main(String[] args) {
		// t1 线程由于运行时间较长，导致其运行过程中 count 被别的线程修改，t1 一直在重复计算消耗 cpu 资源
		new Thread(() -> {
			int expect;
			do {
				expect = count.get();

				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "尝试修改...................");
			}while(!count.compareAndSet(expect,expect + 1));
		}, "t1").start();

		// t2 线程模拟高并发环境下对一个变量的频繁修改
		new Thread(() -> {
			while(true) {

				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}

				count.incrementAndGet();
				System.out.println("count被 " + Thread.currentThread().getName() + " 修改");
			}
		}, "t2").start();
	}
}
