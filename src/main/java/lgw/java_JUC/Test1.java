package lgw.java_JUC;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Test1
 * @Description 题目：（看答案之前自己手写实现）
 * 					多线程之间按顺序调用，实现 A -> B -> C 三个线程启动
 * 					AA 打印 5 次，BB 打印 10 次， CC打印 15 次
 * 					一共打印 10 轮
 *
 * 				总结：
 * 					看答案之前，自己手写实现的。通过这个程序可以看出自己对于多线程编程还是不够熟练，各个方法的理解也不够深刻。下面
 * 				这个实现也存在瑕疵：
 *
 * 					1) 最开始第一次运行时只能通过 sleep 实现，否则可能出现第一次运行顺序错误的问题。
 *
 * 					2) 实现逻辑不够完善，最后一个循环结束后还要加额外的代码控制三个方法正常退出。
 *
 * 					刚开始写的时候对于 await() 方法理解的不对。只知道 await() 会释放锁，不知道这里的释放只是暂时释放，在重新被唤
 * 					醒时会自动获得锁。在 await() 后面紧接着打印 lock.toString() 可以看出线程又重新获得了锁。打印结果：
 * 						java.util.concurrent.locks.ReentrantLock@79476aac[Locked by thread A]
 *
 * @see lgw.java_JUC.Test2 按照周阳老师的思路实现。
 * @Author Numblgw
 * @Date 2019/6/7 20:24
 */
public class Test1 {
	private Lock lock = new ReentrantLock();

	private Condition conditionA = lock.newCondition();

	private Condition conditionB = lock.newCondition();

	private Condition conditionC = lock.newCondition();

	public void threadAPrint() {
		lock.lock();
		for(int i = 10 ; i > 0 ; i--) {
			System.out.println("第 " + (11-i) + " 次........");
			for(int j = 5 ; j > 0 ; j--) {
				System.out.println("AA");
			}
			try {
				conditionB.signal();
				conditionA.await();
				System.out.println(lock.toString());
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 最后一次循环之后，方法退出前还要唤醒下一个方法，让下一个方法也退出。
		conditionB.signal();
		lock.unlock();
		System.out.println("方法 A 结束了");
	}

	public void threadBPrint() {
		lock.lock();
		for(int i = 10 ; i > 0 ; i--) {
			for(int j = 10 ; j > 0 ; j--) {
				System.out.println("BB");
			}
			try {
				conditionC.signal();
				conditionB.await();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 最后一次循环之后，方法退出前还要唤醒下一个方法，让下一个方法也退出。
		conditionC.signal();
		lock.unlock();
		System.out.println("方法 B 结束了");
	}

	public void threadCPrint() {
		lock.lock();
		for(int i = 10 ; i > 0 ; i--) {
			for(int j = 15 ; j > 0 ; j--) {
				System.out.println("CC");
			}
			try {
				conditionA.signal();
				conditionC.await();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock.unlock();
		System.out.println("方法 C 结束了");
	}

	public static void main(String[] args) {
		Test1 test1 = new Test1();

		new Thread(test1::threadAPrint, "A").start();
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(test1::threadBPrint, "B").start();
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(test1::threadCPrint, "C").start();
	}
}
