package lgw.java_JVM;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Demo8
 * @Description OutOfMemory: GC Overhead
 * @Author Numblgw
 * @Date 2019/6/15 15:28
 */
public class Demo8 {

	/**
	 * 添加jvm参数
	 * -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:MaxDirectMemorySize=5m
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 0;

		List<String> list = new ArrayList<>();

		while(true) {
			list.add(String .valueOf(++i).intern());
		}
	}
}
