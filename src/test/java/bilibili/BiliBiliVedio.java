package bilibili;

import com.github.gin.agama.annotation.Xpath;
import com.github.gin.agama.entity.HtmlEntity;

import java.util.Date;

public class BiliBiliVedio extends HtmlEntity {

	private int id;

	@Xpath("//a[@class='title']")
	private String title;

	@Xpath("//a/@href")
	private String href;

	@Xpath("//div[@class='v-desc']")
	private String desc;

	@Xpath("//img/@data-img")
	private String img;

	@Xpath("//span[@class='v-info-i gk']")
	private long gk;

	@Xpath("//span[@class='v-info-i dm']")
	private long dm;

	@Xpath("//span[@class='v-info-i sc']")
	private long sc;

	@Xpath("//a[@class='v-author']")
	private String up;

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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
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
		return "标题:"+title+",描述:"+desc+",观看"+gk+",弹幕"+dm+",收藏"+sc+",up主"+up+",up时间"+upDate +",\n封面:"+img+",\n地址:"+href;
	}
}
