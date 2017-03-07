package com.github.gin.agama.site;

public class Page {
	
	private String rawText;

	private String url;

    private String contentType;

    private String charset;

    public Page() {}

    public Page(String rawText) {
        this.rawText = rawText;
    }

    public String getRawText() {
		return rawText;
	}

	public void setRawText(String rawText) {
		this.rawText = rawText;
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

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}