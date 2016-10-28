package com.github.gin.agama.site;

import java.util.*;

public class Page {
	
	/**
     * store output fields
     */
    private ResultItems resultItems = new ResultItems();;

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

    /**
     * store crawl url
     */
    private List<String> requests = new ArrayList<>();

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
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

    public ResultItems getResultItems(){
        return resultItems;
    }


}