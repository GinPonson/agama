package org.pyj.vertical.JCrawler.downloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

public class Page {
	
	/**
     * store output fields
     */
    private Map<String,String> fields = new HashMap<String,String>();

    /**
     * store crawl url
     */
    private List<String> requests = new ArrayList<String>();

    /**
     * text
     */
	private String text;


    private Html html;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public List<String> getRequests() {
		return requests;
	}

	public void setRequests(List<String> requests) {
		this.requests = requests;
	}

    public Html getHtml() {
        if(text == null){
            throw new NullPointerException();
        }
        html = new Html(Jsoup.parse(text));
        return html;
    }

}