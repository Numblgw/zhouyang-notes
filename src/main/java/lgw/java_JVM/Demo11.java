package lgw.java_JVM;

/**
 * @ClassName Demo11
 * @Description OutOfMemoryError: MetaSpace
 * @Author Numblgw
 * @Date 2019/6/15 16:14
 */
public class Demo11 {

	/**
	 * 加入jvm参数
	 * -XX:MetaspaceSize=1m -XX:MaxMetaspaceSize=1m
	 * 直接把元空间设置成 1m 直接导致虚拟机启动失败
	 * @param args
	 */
	public static void main(String[] args) {

	}
}