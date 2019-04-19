/**
 * 
 */
package com.lqq.bookbar.entity;

/**
 * @author LQQ
 *
 */
public class ArticleDemo {

	private String name;
	private String content;
	private String url;
	
	public ArticleDemo(String name,String content,String url) {
		this.setName(name);
		this.setContent(content);
		this.setUrl(url);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}