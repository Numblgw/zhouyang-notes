package lgw.java_JUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @ClassName Demo2
 * @Description 演示并发环境下使用乐观锁产生的ABA问题
 * @Author Numblgw
 * @Date 2019/6/7 12:08
 */
public class Demo2 {

	/**
	 * 普通的原子更新类 存在ABA问题 t1 t2 线程演示
	 */
	static AtomicInteger count = new AtomicInteger(0);

	/**
	 * 加了版本号可以解决ABA问题 t3 t4 线程演示
	 */
	static AtomicStampedReference<Integer> stampedCount = new AtomicStampedReference<>(0, 1);

	public static void main(String[] args) {
		new Thread(() -> {
			int expect = count.get();

			// 模拟 t1 线程的执行时间长
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			// t1 并不知道 t2 对该值进行了什么操作，期望值没变就认为是没有被修改过。
			count.compareAndSet(expect, 1);
			System.out.println(Thread.currentThread().getName() + " 线程将count修改为了 " + count.get());
		}, "t1").start();

		new Thread(() -> {
			// t2 先将值改为 1 又重新改成 0
			System.out.println(Thread.currentThread().getName() + " 线程将count修改为了 " + count.incrementAndGet());
			System.out.println(Thread.currentThread().getName() + " 线程将count修改为了 " + count.decrementAndGet());
		}, "t2").start();

		new Thread(() -> {
			Integer expectReference = stampedCount.getReference();
			int expectStamped = stampedCount.getStamp();

			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			boolean updateResult = stampedCount.compareAndSet(expectReference, expectReference + 1, expectStamped, expectStamped + 1);
			System.out.println("期望值为 " + expectReference + " 期望版本号为 " + expectStamped);
			System.out.println("当前值为 " + stampedCount.getReference() + " 当前版本号为 " + stampedCount.getStamp());
			System.out.println(Thread.currentThread().getName() + "线程是否修改成功 " + updateResult);
		}, "t3").start();

		new Thread(() -> {
			stampedCount.set(stampedCount.getReference() + 1, stampedCount.getStamp() + 1);
			System.out.println(Thread.currentThread().getName() + " 线程将count修改为了 " + stampedCount.getReference() + " 当前版本号为 " + stampedCount.getStamp());
			stampedCount.set(stampedCount.getReference() - 1, stampedCount.getStamp() + 1);
			System.out.println(Thread.currentThread().getName() + " 线程将count修改为了 " + stampedCount.getReference() + " 当前版本号为 " + stampedCount.getStamp());
		}, "t4").start();
	}
}
