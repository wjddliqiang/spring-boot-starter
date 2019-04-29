/**
 * 
 */
package com.lqq.bookbar.utils;

/**
 * 主键生成器，有guid，也有组合主键（guid和时间序列结合的）
 * @author lenovo
 *
 */
public class PrimaryKeyHelper {

	/**
	 *  去掉连接符‘-’，并将原始字符转换成大写
	 * @return String 
	 */
	public static String getGuidKey() {
		return java.util.UUID.randomUUID().toString().toUpperCase().replace("-", "");
	}
	
	/**
	 * 去掉连接符‘-’，并将原始字符转换成大写，然后截取前19位，然后剩余位数由系统自动生成的精确到毫秒级的时间补充
	 * @return
	 */
	public static String getCompKey() {
		return java.util.UUID.randomUUID().toString().toUpperCase().replace("-", "").substring(0, 19)+System.currentTimeMillis();
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(getGuidKey());
		System.out.println(getCompKey());
	}
}
