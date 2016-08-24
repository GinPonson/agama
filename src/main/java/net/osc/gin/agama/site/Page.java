package net.osc.gin.agama.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;

import net.osc.gin.agama.util.UrlUtils;

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
	private String rawText;

	/**
	 * this is page url
	 */
	private String url;

	/**
	 * parse to html
	 */
    private Html html;

	public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
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
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Html getHtml() {
        if(rawText == null){
            throw new NullPointerException();
        }
        String domain = UrlUtils.getDefaultDomain(url);
        html = new Html(Jsoup.parse(rawText,domain));
        return html;
    }

}