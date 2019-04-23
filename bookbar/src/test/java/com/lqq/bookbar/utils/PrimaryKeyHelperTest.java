package com.lqq.bookbar.utils;

import static org.junit.Assert.*;

import java.util.regex.Pattern;

import org.junit.Test;

public class PrimaryKeyHelperTest {

	@Test
	public void testGetGuidKey() {
		String guid = PrimaryKeyHelper.getGuidKey();
		assertNotNull(guid);
		assertEquals(32L, guid.length());
		//assertEquals(guid, actual);
		//使用正则表达式检测字符串是否都是大写字母和数字
		//使用\\w也是可以的，但不建议，它代表匹配这些字符 [A-Za-z0-9_]还包括希腊字母，俄文的字母等
		//使用\\W是匹配非单词的字符，如果是单词内的匹配不了，所以手写一个
		String regex = "[A-Za-z0-9]{32}";
		assertTrue(Pattern.matches(regex, guid));
	}

	@Test
	public void testGetCompKey() {
		String guid = PrimaryKeyHelper.getCompKey();
		assertNotNull(guid);
		assertEquals(32L, guid.length());
		//使用正则表达式检测字符串是否都是大写字母和数字
		//使用\\w也是可以的，但不建议，它代表匹配这些字符 [A-Za-z0-9_]还包括希腊字母，俄文的字母等
		//使用\\W是匹配非单词的字符，如果是单词内的匹配不了，所以手写一个
		String regex = "[A-Za-z0-9]{32}";
		assertTrue(Pattern.matches(regex, guid));
	}

}
