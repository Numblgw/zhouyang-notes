package lgw.java_JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName Test2
 * @Description 题目：（视频中周阳老师的实现）就是看了一遍老师写的思路自己按照老师的思路又写了一遍。
 *
 *					多线程之间按顺序调用，实现 A -> B -> C 三个线程启动
 * 					AA 打印 5 次，BB 打印 10 次， CC打印 15 次
 *					一共打印 10 轮
 *
 *				总结：
 *					不完全依赖 await / signal 控制线程的执行顺序，结合了一个标志位共同控制。
 *					与自己写的相比，更加灵活，使线程的执行顺序更加容易控制。不用在开始和结束
 *					写一大堆代码控制特殊情况的执行顺序。
 * @Author Numblgw
 * @Date 2019/6/8 14:07
 */
public class Test2 {
	public static void main(String[] args) {
		PrintTest printTest = new PrintTest();
		new Thread(() -> {
			for(int i = 0 ; i < 10 ; i++) {
				printTest.printABC(5, "AA", 1);
			}
		}, "A").start();
		new Thread(() -> {
			for(int i = 0 ; i < 10 ; i++) {
				printTest.printABC(10, "BB", 2);
			}
		}, "B").start();
		new Thread(() -> {
			for(int i = 0 ; i < 10 ; i++) {
				printTest.printABC(15, "CC", 3);
			}
		}, "C").start();
	}
}

class PrintTest {
	private Lock lock = new ReentrantLock();

	private Condition A = lock.newCondition();

	private Condition B = lock.newCondition();

	private Condition C = lock.newCondition();

	/**
	 * 信号标志， 1 代表 线程 A 执行，2 代表线程 B 执行， 3 代表线程 C 执行
	 * 初始值为 1 ，因为逻辑是 A 先执行。
	 */
	private volatile int flag = 1;

	/**
	 * 传入打印的次数和打印的内容，控制台打印
	 * @param printCount	打印的次数
	 * @param printText	打印的文本
	 * @param
	 */
	public void printABC(int printCount, String printText, int flag) {
		lock.lock();
		try{
			while(this.flag != flag) {
				try {
					if(flag == 1) {
						A.await();
					}else if(flag == 2) {
						B.await();
					}else if(flag == 3) {
						C.await();
					}
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}

			for(int i = printCount ; i > 0 ; i--) {
				System.out.println(printText);
			}
			if(flag == 1) {
				this.flag = 2;
				B.signal();
			}else if(flag == 2) {
				this.flag = 3;
				C.signal();
			}else if(flag == 3) {
				this.flag = 1;
				A.signal();
			}
		}finally {
			lock.unlock();
		}
	}
}
