package com.lqq.bookbar.test;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JDTokenRec {

	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value = "/jd/token")
	public String jd() {
		System.out.println("token:"+request.getParameter("token"));
		Map<String,String[]> maps = request.getParameterMap();
		System.out.println("code:"+request.getParameter("code"));
		
		return null;
	}
}
