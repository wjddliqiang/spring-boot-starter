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
public class TemplateViewerController {

	
	@RequestMapping("/loginview")
	public ModelAndView login() {
		System.out.println("怎么啦1？");
		return new ModelAndView("login");
	}
	
	@RequestMapping("/mainview")
	public String main() {
		System.out.println("怎么啦2？");
		return "main";
	}
}
