package com.github.gin.agama.downloader;

import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.ProxyConfig;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.github.gin.agama.UserAgent;
import com.github.gin.agama.proxy.HttpProxy;
import com.github.gin.agama.proxy.Proxys;
import com.github.gin.agama.site.Page;
import com.github.gin.agama.site.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.Map;

/**
 * @author GinPonson
 * @see com.github.gin.agama.downloader.Downloader
 */
public abstract class HtmlUnitDownloader implements Downloader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlUnitDownloader.class);

    @Override
    public Page download(Request req) throws IOException {
        LOGGER.info(" {} downloading the page : {}",Thread.currentThread().getName(), req.getUrl());

        WebClient webClient = new WebClient();

        Proxy proxy = Proxys.getProxy();
        if (proxy instanceof HttpProxy) {
            HttpProxy httpProxy = (HttpProxy) proxy;
            ProxyConfig proxyConfig = webClient.getOptions().getProxyConfig();
            proxyConfig.setProxyHost(httpProxy.getHost());
            proxyConfig.setProxyPort(httpProxy.getPort());
            DefaultCredentialsProvider credentialsProvider = (DefaultCredentialsProvider) webClient.getCredentialsProvider();
            credentialsProvider.addCredentials(httpProxy.getUser(), httpProxy.getPassword());
        }

        webClient.addRequestHeader("User-Agent", UserAgent.randomUserAgent());
        for (Map.Entry<String, String> cookieEntry : req.getCookies().entrySet()) {
            Cookie cookie = new Cookie(null, cookieEntry.getKey(), cookieEntry.getValue());
            webClient.getCookieManager().addCookie(cookie);
        }

        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);

        HtmlPage htmlPage = webClient.getPage(req.getUrl());

        htmlPage = operate(htmlPage);

        Page page = new Page();
        page.setRawText(htmlPage.asXml());
        page.setUrl(req.getUrl());
        page.setCharset(htmlPage.getCharset().name());

        webClient.close();
        return page;
    }

    public abstract HtmlPage operate(HtmlPage htmlPage);
}
