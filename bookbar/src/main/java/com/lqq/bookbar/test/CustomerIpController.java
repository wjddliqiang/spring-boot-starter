package com.lqq.bookbar.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class CustomerIpController {

	
	@GetMapping(value = "/ip")
	public String getCustomerIp() {
		 ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		 HttpServletRequest request = servletRequestAttributes.getRequest();
		 HttpServletResponse response = servletRequestAttributes.getResponse();
		 
		//当远程计算机连接有线网络时，返回的IP是远程计算机本身的IP地址
		//当远程计算机连接无线网络时，返回的IP是远程计算机所连接的无线网络路由的IP地址
		//当远程计算机连接无线网络时且有线网络没有断开时，返回的IP是远程计算机本身的有线的IP地址
		return "host:"+request.getRemoteHost()+"  addr:"+request.getRemoteAddr();
	}
}
