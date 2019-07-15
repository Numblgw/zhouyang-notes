package lgw.java_JVM;

import java.nio.ByteBuffer;

/**
 * @ClassName Demo9
 * @Description OutOfMemory: Direct buffer memory
 * @Author Numblgw
 * @Date 2019/6/15 15:45
 */
public class Demo9 {

	private static final int MB_6 = 1024 * 1024 * 6;

	/**
	 * 添加jvm参数
	 * -XX:MaxDirectMemorySize=5m
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("配置的maxDirectMemory：" + (sun.misc.VM.maxDirectMemory() / (double) 1024 / 1024) + "MB");
		ByteBuffer bb = ByteBuffer.allocateDirect(MB_6);
	}
}
