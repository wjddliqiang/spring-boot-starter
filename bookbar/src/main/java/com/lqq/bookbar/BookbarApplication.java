package com.lqq.bookbar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.lqq.bookbar.controller.Person;
 
/**
   * 在启动类当中加上extends SpringBootServletInitializer并重写configure方法，这是为了打包springboot项目用的
 * @author LQQ
 *
 */
@SpringBootApplication
@EnableConfigurationProperties({Person.class})
public class BookbarApplication extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(BookbarApplication.class, args);
		
	}

	/**
	   *  项目打包使用
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(this.getClass());
	}
	

}
