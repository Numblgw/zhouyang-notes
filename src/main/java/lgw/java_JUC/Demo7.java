package lgw.java_JUC;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo7
 * @Description Semaphore基本使用演示
 * @Author Numblgw
 * @Date 2019/6/7 18:58
 */
public class Demo7 {
	/**
	 * 第一个参数是指定有 7 个资源，第二个参数是指定是否公平，默认是 false，类似于ReentrantLock
	 */
	private Semaphore semaphore = new Semaphore(3, false);

	public static void main(String[] args) {
		Demo7 demo7 = new Demo7();
		for(int i = 7 ; i > 0 ; i--) {
			final int j = i;
			new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				try {
					demo7.semaphore.acquire();
					System.out.println(Thread.currentThread().getName() + "抢到了资源");
					TimeUnit.MILLISECONDS.sleep(500);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println(Thread.currentThread().getName() + "释放了资源");
					demo7.semaphore.release();
				}

			}, String.valueOf(j)).start();
		}
	}
}
