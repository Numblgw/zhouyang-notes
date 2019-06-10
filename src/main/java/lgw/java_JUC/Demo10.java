package lgw.java_JUC;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo10
 * @Description 演示死锁
 * @Author Numblgw
 * @Date 2019/6/9 13:27
 */
public class Demo10 {
	private String lockA;

	private String lockB;

	public Demo10(String lockA, String lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	public void run1() {
		synchronized(lockA) {
			System.out.println(Thread.currentThread().getName() + "该线程获得了lockA");
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			synchronized(lockB) {

			}
		}
	}

	public void run2() {
		synchronized(lockB) {
			System.out.println(Thread.currentThread().getName() + "该线程获得了lockB");
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}

			synchronized(lockA) {

			}
		}
	}

	public static void main(String[] args) {
		Demo10 demo10 = new Demo10("lockA", "lockB");

		new Thread(demo10::run1).start();

		new Thread(demo10::run2).start();
	}
}
