package com.github.gin.agama.site;

import java.util.*;

import com.github.gin.agama.entity.HtmlEntity;
import com.github.gin.agama.util.UrlUtils;
import org.jsoup.Jsoup;

public class Page {
	
	/**
     * store output fields
     */
    private List<HtmlEntity> records = new ArrayList<>();

    /**
     * store crawl url
     */
    private List<String> requests = new ArrayList<>();

    /**
     * text
     */
	private String rawText;

	/**
	 * this is page url
	 */
	private String url;

    /**
     * response of the content-type
     */
    private String contentType;

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
	}

    public List<HtmlEntity> getRecords() {
        return records;
    }

    public void setRecords(List<HtmlEntity> records) {
        this.records = records;
    }

    public List<String> getRequests() {
		return requests;
	}

	public void setRequests(List<String> requests) {
		this.requests = requests;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Render getRender(){
        return new Render(rawText,url);
    }

}