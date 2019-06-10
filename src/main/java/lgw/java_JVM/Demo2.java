package lgw.java_JVM;

/**
 * @ClassName Demo2
 * @Description 测试强引用即使在内存不够用时也不会回收。
 * @Author Numblgw
 * @Date 2019/6/10 14:17
 */
public class Demo2 {

	private static final int MB_30 = 1024 * 1024 * 30;

	/**
	 *	设置虚拟机启动参数 -Xms5m -Xmx5m -XX:+PrintGCDetails
	 */
	public static void main(String[] args) {
		Object o = new Object();
		System.out.println(o);

		try{
			byte[] bytes = new byte[MB_30];
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			// 打印结果表明，即使发生 OOM 强引用引用的对象也不会被回收。
			System.out.println(o);
		}
	}
}
