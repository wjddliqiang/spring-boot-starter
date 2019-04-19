/**
 * 
 */
package com.lqq.bookbar.controller;

import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lqq.bookbar.entity.ArticleDemo;

/**
 * @author LQQ
 *
 */
@Controller
public class SiteDemoController {

	@RequestMapping("/index")
	public String index(Model model) {
		ArrayList<ArticleDemo> list = new ArrayList<>();
        list.add(new ArticleDemo("Async：简洁优雅的异步之道","在异步处理方案中，目前最为简洁优雅的便是async函数（以下简称A函数）。","www.baidu.com"));
        list.add(new ArticleDemo("H5 前端性能测试实践","H5 页面发版灵活，轻量，又具有跨平台的特性，在业务上有很多应用场景。","www.baidu.com"));
        list.add(new ArticleDemo("学习Python的建议","Python是最容易入门的编程语言。","www.baidu.com"));
        model.addAttribute("articleList",list);
		return "index";
	}
}
