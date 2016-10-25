package com.github.gin.agama.downloader;

import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Proxy;
import java.util.ArrayList;


public abstract class AbstractPhantomDownloader implements Downloader{
	
	private static final Logger log = LoggerFactory.getLogger(AbstractPhantomDownloader.class);

    private DesiredCapabilities capabilities ;

    public AbstractPhantomDownloader(){
        String driverPath = AbstractPhantomDownloader.class.getClassLoader().getResource("phantomjs-2.1.1-windows/bin/phantomjs.exe").getPath();

        System.setProperty("phantomjs.binary.path", driverPath);

        capabilities = DesiredCapabilities.phantomjs();

    }

	@Override
    public Page download(Request req) {
		log.info(Thread.currentThread().getName() + "正在抓取页面:" + req.getUrl());

        setHttp();

        WebDriver driver = new PhantomJSDriver(capabilities);
        driver.get(req.getUrl());

        opration(driver);

        Page page = new Page();
        page.setRawText(driver.getPageSource());
        page.setUrl(req.getUrl());

        driver.quit();

        return page;
    }

    public abstract void opration(WebDriver webDriver);

    public void setHttp(){
        Proxy proxy = ProxyPool.getProxy();
        if(proxy instanceof HttpProxy){
            HttpProxy httpProxy = (HttpProxy) proxy;
            ArrayList<String> cliArgsCap = new ArrayList<>();
            cliArgsCap.add("--proxy=http://"+httpProxy.getHost()+":"+httpProxy.getPort());
            cliArgsCap.add("--proxy-auth=" + httpProxy.getUser() + ":" + httpProxy.getPassword());
            cliArgsCap.add("--proxy-type=http");
            capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        }
    }
}
