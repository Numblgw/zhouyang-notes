package lgw.java_JVM;

/**
 * @ClassName Demo7
 * @Description OutOfMemory: JavaHeapSpace演示
 * @Author Numblgw
 * @Date 2019/6/15 15:14
 */
public class Demo7 {

	private static final int MB_80 = 80 * 1024 * 1024;

	/**
	 * 添加虚拟机运行参数，
	 * -Xms10m -Xmx10m
	 *
	 * 大对象导致堆内存溢出
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] b = new byte[MB_80];
	}
}
