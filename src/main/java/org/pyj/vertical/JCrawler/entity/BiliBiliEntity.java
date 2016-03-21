package org.pyj.vertical.JCrawler.entity;

import java.util.Date;

import org.pyj.vertical.JCrawler.annotation.AncestorElement;
import org.pyj.vertical.JCrawler.annotation.DescendantElement;

@AncestorElement(xpath="//div[@class='l-item']")
public class BiliBiliEntity {

	private int id;
	
	@DescendantElement(xpath="//a[@class='title']")
	private String title;
	
	@DescendantElement(xpath="//div[@class='v-desc']")
	private String desc;
	
	@DescendantElement(xpath="//span[@class='v-info-i gk']")
	private long gk;
	
	@DescendantElement(xpath="//span[@class='v-info-i dm']")
	private long dm;
	
	@DescendantElement(xpath="//span[@class='v-info-i sc']")
	private long sc;
	
	@DescendantElement(xpath="//a[@class='v-author']")
	private String up;
	
	@DescendantElement(xpath="//span[@class='v-date']")
	private Date upDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public long getGk() {
		return gk;
	}

	public void setGk(long gk) {
		this.gk = gk;
	}

	public long getDm() {
		return dm;
	}

	public void setDm(long dm) {
		this.dm = dm;
	}

	public long getSc() {
		return sc;
	}

	public void setSc(long sc) {
		this.sc = sc;
	}

	public String getUp() {
		return up;
	}

	public void setUp(String up) {
		this.up = up;
	}

	public Date getUpDate() {
		return upDate;
	}

	public void setUpDate(Date upDate) {
		this.upDate = upDate;
	}
	
	public String toString(){
		return "标题:"+title+",描述:"+desc+",观看"+gk+",弹幕"+dm+",收藏"+sc+",up主"+up+",up时间"+upDate;
	}
}
