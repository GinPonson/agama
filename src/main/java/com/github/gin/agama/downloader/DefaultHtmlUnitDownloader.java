package com.github.gin.agama.downloader;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * @author GinPonson
 * @see HtmlUnitDownloader
 */
public class DefaultHtmlUnitDownloader extends HtmlUnitDownloader {
    @Override
    public HtmlPage operate(HtmlPage htmlPage) {
        return htmlPage;
    }
}
