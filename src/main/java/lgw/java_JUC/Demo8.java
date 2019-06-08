package lgw.java_JUC;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName Demo8
 * @Description SynchronousQueue原理演示
 * @Author Numblgw
 * @Date 2019/6/7 19:51
 */
public class Demo8 {
	private SynchronousQueue<Integer> queue = new SynchronousQueue<>();

	public static void main(String[] args) {
		Demo8 demo8 = new Demo8();

		new Thread(() -> {
			for(int i = 0 ; i < 10 ; i++) {
				System.out.println("生产者 put " + i);
				try {
					// 这里会阻塞
					demo8.queue.put(i);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "生产者").start();

		new Thread(() -> {
			for(int i = 0 ; i < 10 ; i++) {
				try {
					TimeUnit.SECONDS.sleep(2);
					// take之后 生产者线程才可以继续执行。
					System.out.println("消费者 take " + demo8.queue.take());
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "消费者").start();
	}
}
