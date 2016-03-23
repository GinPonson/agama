package org.pyj.vertical.JCrawler.downloader;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.pyj.vertical.JCrawler.proxy.HttpProxy;
import org.pyj.vertical.JCrawler.site.Page;
import org.pyj.vertical.JCrawler.site.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SeleniumDownloader implements Downloader{
	
	private static final Logger log = LoggerFactory.getLogger(SeleniumDownloader.class);

	private static FirefoxProfile profile = new FirefoxProfile();
	
	@Override
	public Page download(Request req) {
		log.info("正在抓取页面:"+req.getUrl());	
		
		WebDriver driver = new FirefoxDriver(profile);
		driver.get(req.getUrl());
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		String rowText = driver.getPageSource();
		Page page = new Page();
		page.setUrl(req.getUrl());
		page.setRawText(rowText);
		driver.close();
		
		return page;
	}

	@Override
	public void setHttpProxy(Proxy p) {
		HttpProxy proxy = (HttpProxy)p;
		profile.setPreference("network.proxy.type", 2);
	    profile.setPreference("network.proxy.autoconfig_url", proxy.getHost()+":"+proxy.getPort()); //自动
	}

}
