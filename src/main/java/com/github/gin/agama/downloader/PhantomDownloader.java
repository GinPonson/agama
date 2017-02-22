package com.github.gin.agama.downloader;

import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.ProxyPool;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import com.github.gin.agama.util.AgamaUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.Proxy;
import java.util.ArrayList;


public abstract class PhantomDownloader implements Downloader{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PhantomDownloader.class);

    private DesiredCapabilities capabilities ;

    public PhantomDownloader(){
        String[] paths = System.getenv().get("Path").split(File.pathSeparator);
        for (String path : paths ) {
            if(AgamaUtils.contains(path,"phantomjs")) {
                String binaryPath = path + File.separator + "phantomjs.exe";
                System.setProperty("phantomjs.binary.path", binaryPath);

                if(LOGGER.isDebugEnabled()) {
                    LOGGER.debug("phantomjs.binary.path : {}", binaryPath);
                }
            }
        }

        capabilities = DesiredCapabilities.phantomjs();
    }

	@Override
    public Page download(Request req) {
		LOGGER.info(Thread.currentThread().getName() + "crawling the page : {}" , req.getUrl());

        setProxy();
        Page page = null;

        try {
            WebDriver driver = new PhantomJSDriver(capabilities);
            driver.get(req.getUrl());

            opration(driver);

            page = new Page();
            page.setRawText(driver.getPageSource());
            page.setUrl(req.getUrl());

            driver.quit();
        } catch (IllegalStateException e){
            LOGGER.error("PhantomJSDriver have some trouble:{}," +
                    "please check if phantomjs in your PATH.",e.getMessage());
            e.printStackTrace();
        }

        return page;
    }

    public abstract void opration(WebDriver webDriver);

    public void setProxy(){
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
