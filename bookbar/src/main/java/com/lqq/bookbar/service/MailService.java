/**
 * 
 */
package com.lqq.bookbar.service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.ode.nonstiff.EmbeddedRungeKuttaFieldIntegrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.sun.mail.util.MimeUtil;

/**
 * @author lenovo
 *
 */
@Service
public class MailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;//获取发件人
	
	public MailService() {
		//java mail发邮件是附件名过长默认会被截断，附件名显示【tcmime.29121.29517.50430.bin】，主动设为false可正常显示附件名
		System.setProperty("mail.mime.splitlongparameters", "false");
	}
	
	private static Log logger = LogFactory.getLog(MailService.class);
	
	/**
	 * 发送邮件，这里只有简单的文本
	 * @param touser
	 * @param subject
	 * @param text
	 * @param ccuser
	 * @return
	 */
	public boolean sendSimpleMail(String touser,String subject,String text,String... ccuser) {
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setFrom(from);//邮件发送着
		msg.setBcc(ccuser);//邮件抄送
		msg.setTo(touser);//邮件收件人
		msg.setSubject(subject);//标题
		msg.setText(text);//正文
		try {
			javaMailSender.send(msg);
		} catch (MailException ex) {
			logger.warn(ex.getRootCause());
			return false;
		}
		return true;
	}
	
	public boolean sendMimemail(String to,String subject,String text,Map<String, String> attachment,String... ccuser) {
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true,"UTF-8");//是否为富文本
			helper.setFrom(from);
		    helper.setTo(to);
		    helper.setSubject(subject);
            helper.setText(text, true);
            
		    helper.setText(text, true);
		    if (attachment != null && attachment.size() != 0) {
				for (Entry<String, String> entry : attachment.entrySet()) {
					File file = new File(entry.getValue());
					if (file.exists()) {
						FileSystemResource res = new FileSystemResource(file);
						//附件名称编码格式，不设置收到邮件显示会有问题
						helper.addAttachment(MimeUtility.encodeWord(entry.getKey(),"utf-8","B"), res);
					}
				}
			}
		    javaMailSender.send(message);
		} catch (MessagingException e) {
			logger.warn(e.getMessage());
			return false;
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage());
			return false;
		}
		return true;
	}
	
}
