package lgw.java_JUC;

import java.util.concurrent.*;

/**
 * @ClassName Demo9
 * @Description 仿照源码改造线程池，并且演示4种拒绝策略
 * @Author Numblgw
 * @Date 2019/6/9 12:51
 */
public class Demo9 {
	public static void main(String[] args) {
		ExecutorService threadPool = new ThreadPoolExecutor(
				3,
				5,
				60,
				TimeUnit.SECONDS,
				new LinkedBlockingQueue<>(3),
				Executors.defaultThreadFactory(),

				// 默认的 会抛出异常
				new ThreadPoolExecutor.AbortPolicy());

				// 将任务回退给调用者
				//new ThreadPoolExecutor.CallerRunsPolicy());

				// 抛弃等待最久的任务
				// new ThreadPoolExecutor.DiscardOldestPolicy());

				// 直接抛弃新来的任务
				// new ThreadPoolExecutor.DiscardPolicy());

		try{
			for(int i = 0 ; i < 10 ; i++) {
				threadPool.execute(() -> System.out.println(Thread.currentThread().getName() + " 办理业务"));
			}
		}finally {
			threadPool.shutdown();
		}
	}
}
