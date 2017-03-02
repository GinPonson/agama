package com.github.gin.agama.site;

import com.github.gin.agama.entity.AgamaEntity;
import com.github.gin.agama.exception.AgamaException;
import com.github.gin.agama.util.UrlUtils;
import org.jsoup.Jsoup;

/**
 * Created by FSTMP on 2016/10/27.
 */
public class Render {

    private String rawText;

    private String url;

    public Render(String rawText, String url) {
        this.rawText = rawText;
        this.url = url;
    }

    public JsonRender renderToJson() {
        if (rawText == null) {
            throw new AgamaException("内容为空");
        }
        return new JsonRender(rawText);
    }

    public XpathRender renderToHtml() {
        if (rawText == null) {
            throw new AgamaException("内容为空");
        }
        String domain = UrlUtils.getDefaultDomain(url);
        XpathRender html = new XpathRender(Jsoup.parse(rawText, domain));
        return html;
    }
}
