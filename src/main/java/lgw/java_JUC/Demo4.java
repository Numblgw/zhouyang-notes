package lgw.java_JUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Demo4
 * @Description 手写自旋锁
 * @Author Numblgw
 * @Date 2019/6/7 16:28
 */
public class Demo4 {
	public static void main(String[] args) {
		// 验证周阳老师实现的自选锁
		MySpinLock1 spinLock1 = new MySpinLock1();
		new Thread(() -> {
			spinLock1.myLock();

			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			spinLock1.myUnlock();
		}, "t1").start();
		new Thread(() -> {
			spinLock1.myLock();
			spinLock1.myUnlock();
		}, "t2").start();

		try {
			TimeUnit.SECONDS.sleep(1);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		// ================================================

		// 验证自己通过 ReentrantLock 实现的自旋锁
		MySpinLock2 spinLock2 = new MySpinLock2();
		new Thread(() -> {
			spinLock1.myLock();

			try {
				TimeUnit.MILLISECONDS.sleep(1);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			spinLock1.myUnlock();
		}, "t3").start();
		new Thread(() -> {
			spinLock1.myLock();
			spinLock1.myUnlock();
		}, "t4").start();
	}
}

/**
 * 周阳老师视频中的自旋锁手写实现
 */
class MySpinLock1 {
	private AtomicReference<Thread> atomicReference = new AtomicReference<>();

	public void myLock() {
		Thread current = Thread.currentThread();
		System.out.println(current.getName() + "尝试获取锁");
		while(!atomicReference.compareAndSet(null, current)) {
			System.out.println(Thread.currentThread().getName() + "尝试获取锁");
		}
		System.out.println(current.getName() + "获取到锁");
	}

	public void myUnlock() {
		Thread current = Thread.currentThread();
		System.out.println(current.getName() + "解锁");
		atomicReference.compareAndSet(current, null);
	}
}

/**
 * 自己实现自旋锁，这应该也算是自旋锁吧，tryLock方法应该不会阻塞，待验证。
 */
class MySpinLock2 {
	private final Lock lock = new ReentrantLock();

	public void myLock() {
		System.out.println(Thread.currentThread().getName() + "尝试获取锁");
		while(!lock.tryLock()) {
			System.out.println(Thread.currentThread().getName() + "尝试获取锁");
		}
		System.out.println(Thread.currentThread().getName() + "获取到锁");
	}

	public void myUnlock() {
		System.out.println(Thread.currentThread().getName() + "解锁");
		lock.unlock();
	}
}
