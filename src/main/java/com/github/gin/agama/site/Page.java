package com.github.gin.agama.site;

import java.util.*;

public class Page {
	
	/**
     * process output fields
     */
    private ResultItems resultItems = new ResultItems();

    /**
     * process crawl url
     */
    private List<Request> requests = new ArrayList<>();

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

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public void addRequests(List<String> urls) {
        for(String url : urls){
            this.requests.add(new Request(url));
        }
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