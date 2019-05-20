/**
 * 
 */
package com.lqq.bookbar.mq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @author lenovo
 *
 */
@Component
public class ConsumerB {

	@JmsListener(destination="active.queue")
	public void readActiveQueue(String msg) {
		System.out.println("消费者B接收到消息："+msg);
	}
}
