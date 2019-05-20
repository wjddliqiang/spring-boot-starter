/**
 * 
 */
package com.lqq.bookbar.controller;

import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lenovo
 *
 */
@RestController
public class ProductController {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@RequestMapping("/sendmsg")
	public void sendMsg(final String msg) {
		this.jmsMessagingTemplate.convertAndSend("boot-queue",msg);
		System.out.println("发送消息："+msg);
	}
}
