package net.osc.gin.agama.processer;

import java.util.List;

import net.osc.gin.agama.core.CrawlConfiger;
import net.osc.gin.agama.core.JCrawler;
import net.osc.gin.agama.entity.BiliBiliEntity;
import net.osc.gin.agama.site.Page;

public class OSCPageProcess implements PageProcess{

	public void process(Page page) {
		//System.out.println(page.getHtml().toString());
		//System.out.println(page.getHtml().select(".vd-list-cnt").text());
		/*for(String link : page.getHtml().absLinks(".vd-list-cnt")){
			System.out.println(link);
		}*/
		System.out.println("-----------------------------------");
		List<BiliBiliEntity> lists = page.getHtml().toEntityList(BiliBiliEntity.class);
		for(BiliBiliEntity b : lists){
			System.out.println(b.toString());
		}

		page.getRequests().addAll(page.getHtml().xpath("//div[@class='pagelistbox']/a/@href").list());
		
	}
	
	 /*public void CaptureScreenshot(String fileName, WebDriver driver) {
		 154         String dirName = "d:/screenshot";
		 155         if (!(new File(dirName).isDirectory())) {
		 156             new File(dirName).mkdir();
		 157         }
		 158         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
		 159         String time = sdf.format(new Date());
		 160         TakesScreenshot tsDriver = (TakesScreenshot) driver;
		 161         File image = new File(dirName + File.separator + time + "_" + fileName==null?"":fileName + ".png");
		 162         tsDriver.getScreenshotAs(OutputType.FILE).renameTo(image);
		 163     }
	 */
	public static void main(String[] args) {
		//HttpProxy proxy = new HttpProxy(Type.HTTP, "10.228.110.21", 80, "panyongjian", "pan240409F");
		//Request request = new Request("http://weibo.cn/gztq");
		CrawlConfiger config = new CrawlConfiger("http://www.bilibili.com/video/bangumi-two-1.html");
		//config.setProxy(proxy);
		config.setDepth(1);
		config.setThreadNum(2);
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
