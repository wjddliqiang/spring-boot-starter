/**
 * 
 */
package com.lqq.bookbar.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @author lenovo
 *
 */
@Component
public class ConsumerA {

	@JmsListener(destination="boot-queue")
	public void readActiveQueue(String msg) {
		System.out.println("消费者A接收到消息："+msg);
	}
	
	@JmsListener(destination="boot-queue")
	@SendTo("active.queue")
	public String readActiveQueue1(String msg) {
		System.out.println("消费者A转发消息："+msg);
		return "消费者A转发消息-"+msg;
	}
}
