package net.osc.gin.agama.entity;

import java.util.Date;

import net.osc.gin.agama.annotation.CSV;
import net.osc.gin.agama.annotation.Xpath;

@Xpath("//div[@class='l-item']")
public class BiliBiliEntity {

	private int id;

    @CSV(title = "标题")
	@Xpath("//a[@class='title']")
	private String title;

    @CSV(title = "描述")
	@Xpath("//div[@class='v-desc']")
	private String desc;

    @CSV(title = "封面链接")
	@Xpath("//img/@data-img")
	private String img;

    @CSV(title = "观看人数")
	@Xpath("//span[@class='v-info-i gk']")
	private long gk;

    @CSV(title = "弹幕数量")
	@Xpath("//span[@class='v-info-i dm']")
	private long dm;

    @CSV(title = "播放数量")
	@Xpath("//span[@class='v-info-i sc']")
	private long sc;

    @CSV(title = "上传up主")
	@Xpath("//a[@class='v-author']")
	private String up;

    @CSV(title = "上传时间")
	@Xpath("//span[@class='v-date']")
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
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String toString(){
		return "标题:"+title+",描述:"+desc+",观看"+gk+",弹幕"+dm+",收藏"+sc+",up主"+up+",up时间"+upDate +",\n封面:"+img;
	}
}
