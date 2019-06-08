package lgw.java_JUC;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo6
 * @Description CyclicBarrier基本用法演示
 * @Author Numblgw
 * @Date 2019/6/7 18:37
 */
public class Demo6 {

	/**
	 * 第一个参数是设置有 5 个线程等待时打开屏障，第二个是打开屏障之后执行的方法。
	 */
	private CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> System.out.println(Thread.currentThread().getName() + "执行，屏障打开了"));

	public static void main(String[] args) {
		Demo6 demo6 = new Demo6();

		for(int i = 0 ; i < 5 ; i++) {
			final int j = i;
			new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(j);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "等待");

				try {
					demo6.cyclicBarrier.await();
				} catch(InterruptedException e) {
					e.printStackTrace();
				} catch(BrokenBarrierException e) {
					e.printStackTrace();
				}
			}, "t" + String.valueOf(i)).start();
		}
	}
}
