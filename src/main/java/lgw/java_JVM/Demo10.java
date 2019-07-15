package lgw.java_JVM;

/**
 * @ClassName Demo10
 * @Description OutOfMemoryError:unable to create new native thread
 * @Author Numblgw
 * @Date 2019/6/15 16:01
 */
public class Demo10 {

	/**
	 * windows 平台好像看不到效果，似乎是没有上限。。。。。。使用linux非root用户演示。
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;
		while(true) {
			System.out.println(++i);
			new Thread(() -> {
				try {
					Thread.sleep(Integer.MAX_VALUE);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
