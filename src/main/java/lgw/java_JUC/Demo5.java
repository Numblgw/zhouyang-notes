package lgw.java_JUC;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo5
 * @Description CountDownLatch用法，之前看马士兵老师的视频时已经学过了，简单复习一遍。
 * @Author Numblgw
 * @Date 2019/6/7 18:21
 */
public class Demo5 {

	private CountDownLatch latch = new CountDownLatch(5);

	public static void main(String[] args) {
		Demo5 demo5 = new Demo5();
		for(int i = 5 ; i > 0 ; i--) {
			final int j = 6-i;
			new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(j);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "count down");
				demo5.latch.countDown();
			}).start();
		}

		System.out.println("主线程阻塞");
		try {
			demo5.latch.await();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("主线程执行");

	}
}
