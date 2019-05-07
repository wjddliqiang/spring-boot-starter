package com.lqq.bookbar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lqq.bookbar.service.MailService;

@Controller
public class MailController {

	@Autowired
	private MailService mailService;

	@RequestMapping("/sendmails")
	public String sendmail() {
		
		mailService.sendSimpleMail("jerry_li@aeonchina.com.cn", "标题：Java技术栈投稿", "邮件正文：技术分享");
		System.out.println("发送邮件");
		return "index";
	}
}
