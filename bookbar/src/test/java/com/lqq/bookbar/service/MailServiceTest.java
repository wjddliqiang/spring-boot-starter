package com.lqq.bookbar.service;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MailServiceTest {

	@Autowired
	private TemplateEngine templateEngine;//模板引擎
	 
	@Autowired
	public MailService mail;
	@Test
	public void testSendSimpleMail() {
		assertTrue("测试发送一个简单文本邮件",mail.sendSimpleMail("jerry_li@aeonchina.com.cn", "标题：Java技术栈投稿", "邮件正文：技术分享"));
	}

	@Test
	public void testSendMimemail() {
		Map<String, String> map = new HashMap<>();
		//获取public目录的绝对路径
		String filePrexPath = System.getProperty("user.dir")+"\\src\\main\\resources\\public\\";
		
		map.put("test-mail-attechment.txt", filePrexPath+"test-mail-attechment.txt");//必须是绝对路径
		map.put("答复 扫码购CFM的运维对应网络访问权限OS帐号密码开通.msg", filePrexPath+"答复 扫码购CFM的运维对应网络访问权限OS帐号密码开通.msg");
		
		// 添加正文（使用thymeleaf模板）
        Context context = new Context();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("title", "sfsdfsfs");
        map1.put("content", "s时空裂缝收款方式是的冯绍峰");
        context.setVariables(map1);
        String mubanName = "/mail/mailTemplate";
        String content = templateEngine.process(mubanName, context);
		assertTrue("测试发送一个带附件邮件",mail.sendMimemail("jerry_li@aeonchina.com.cn", "标题：Java技术栈投稿-带附件", content,map));
	}

}
