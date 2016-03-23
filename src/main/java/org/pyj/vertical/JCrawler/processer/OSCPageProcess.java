package org.pyj.vertical.JCrawler.processer;

import java.util.List;

import org.pyj.vertical.JCrawler.core.CrawlConfiger;
import org.pyj.vertical.JCrawler.core.JCrawler;
import org.pyj.vertical.JCrawler.entity.BiliBiliEntity;
import org.pyj.vertical.JCrawler.site.Page;

public class OSCPageProcess implements PageProcess{

	public void process(Page page) {
		//System.out.println(page.getHtml().toString());
		//System.out.println(page.getHtml().targetHtml(".vd-list-cnt"));
		/*for(String link : page.getHtml().absLinks(".vd-list-cnt")){
			System.out.println(link);
		}*/
		System.out.println("-----------------------------------");
		List<BiliBiliEntity> lists = page.getHtml().toEntityList(BiliBiliEntity.class);
		for(BiliBiliEntity b : lists){
			System.out.println(b.toString());
		}

		page.getRequests().addAll(page.getHtml().xpath("//div[@class='pagelistbox']/a/@href"));
		
		/*HtmlCleaner hc = new HtmlCleaner();
		TagNode tn = hc.clean(page.getRawText());
		try {
			Object[] os = tn.evaluateXPath("//div[@class='l-item']");
			System.out.println(os.length);
			for(int i = 0; i < 1; i++){
				TagNode t = (TagNode)os[i];
				Object[] os1 = t.evaluateXPath("//a[@class='title']");
				System.out.println(os1[0]);
			}
		} catch (XPatherException e) {
			e.printStackTrace();
		}*/
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
		config.setDepth(4);
		config.setThreadNum(2);
		JCrawler.create(new OSCPageProcess()).setConfig(config).run();
	}
}
