package com.github.gin.agama.downloader;

import java.net.Proxy;
import java.util.ArrayList;

import com.github.gin.agama.site.Request;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.site.Page;


public class PhantomDownloader implements Downloader{
	
	private static final Logger log = LoggerFactory.getLogger(PhantomDownloader.class);

    private DesiredCapabilities capabilities ;

    public PhantomDownloader(String driverPath){
        System.setProperty("phantomjs.binary.path", driverPath);

        capabilities = DesiredCapabilities.phantomjs();
    }

	@Override
    public Page download(Request req) {
		log.info("正在抓取页面:" + req.getUrl());

        WebDriver driver = new PhantomJSDriver(capabilities);
        driver.get(req.getUrl());

        Page page = new Page();
        page.setRawText(driver.getPageSource());
        page.setUrl(req.getUrl());

        driver.quit();

        return page;
    }

    @Override
	public void setHttpProxy(Proxy p) {
        HttpProxy proxy = (HttpProxy) p;
        ArrayList<String> cliArgsCap = new ArrayList<>();
        cliArgsCap.add("--proxy=http://"+proxy.getHost()+":"+proxy.getPort());
        cliArgsCap.add("--proxy-auth="+proxy.getUser()+":"+proxy.getPassword());
        cliArgsCap.add("--proxy-type=http");
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
	}

}
