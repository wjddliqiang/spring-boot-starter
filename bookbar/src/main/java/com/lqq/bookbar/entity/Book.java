/**
 * 
 */
package com.lqq.bookbar.entity;

/**
 * @author LQQ
 *
 */
public class Book {
	
	private String id	;//varchar2(32);
	private String bname;//	varchar2(50)
	private byte[] cover;//	blob
	private String author;//	varchar2(50)
	private String origin_author;//	varchar2(50)
	private String translator;//	varchar2(50)
	private String publish_date;//	varchar2(50)
	private Integer page_count;//	number(6)
	private String binding;//	varchar2(50)
	private String series;//	varchar2(50)
	private String isbn;//	varchar2(50)
	private String content_desc;//	varchar2(200)
	private String author_desc;//	varchar2(200)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public byte[] getCover() {
		return cover;
	}
	public void setCover(byte[] cover) {
		this.cover = cover;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getOrigin_author() {
		return origin_author;
	}
	public void setOrigin_author(String origin_author) {
		this.origin_author = origin_author;
	}
	public String getTranslator() {
		return translator;
	}
	public void setTranslator(String translator) {
		this.translator = translator;
	}
	public String getPublish_date() {
		return publish_date;
	}
	public void setPublish_date(String publish_date) {
		this.publish_date = publish_date;
	}
	public Integer getPage_count() {
		return page_count;
	}
	public void setPage_count(Integer page_count) {
		this.page_count = page_count;
	}
	public String getBinding() {
		return binding;
	}
	public void setBinding(String binding) {
		this.binding = binding;
	}
	public String getSeries() {
		return series;
	}
	public void setSeries(String series) {
		this.series = series;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getContent_desc() {
		return content_desc;
	}
	public void setContent_desc(String content_desc) {
		this.content_desc = content_desc;
	}
	public String getAuthor_desc() {
		return author_desc;
	}
	public void setAuthor_desc(String author_desc) {
		this.author_desc = author_desc;
	}

}
