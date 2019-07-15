package lgw.java_JVM;

/**
 * @ClassName Demo6
 * @Description 演示 StackOverFlowError
 * @Author Numblgw
 * @Date 2019/6/15 15:06
 */
public class Demo6 {
	public static void main(String[] args) {
		stackOverFlow();
	}

	/**
	 * 无尽的递归调用，导致栈（stack）爆炸了2333
	 */
	private static void stackOverFlow() {
		stackOverFlow();
	}
}
