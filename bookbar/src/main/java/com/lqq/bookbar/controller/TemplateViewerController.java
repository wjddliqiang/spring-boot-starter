/**
 * 
 */
package com.lqq.bookbar.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lenovo
 *
 */
@Controller
@RequestMapping("v")
public class TemplateViewerController {

	
	@RequestMapping("/login")
	public ModelAndView html1() {
		System.out.println("怎么啦1？");
		return new ModelAndView("login");
	}
	
	@RequestMapping("/login2")
	public String html2() {
		return "login2";
	}
	
	@RequestMapping("/main")
	public String main() {
		System.out.println("怎么啦2？");
		return "main";
	}
}
