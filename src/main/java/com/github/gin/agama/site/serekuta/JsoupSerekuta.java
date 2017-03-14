package com.github.gin.agama.site.serekuta;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupSerekuta implements Serekuta {

    private Elements elements;

    public JsoupSerekuta(String html) {
        Document document = Jsoup.parse(html);
        this.elements = new Elements(document);
    }

    public JsoupSerekuta(Elements elements) {
        this.elements = elements;
    }

    @Override
    public Serekuta select(String css) {
        Elements selectElements = elements.select(css);
        return new JsoupSerekuta(selectElements);
    }

    @Override
    public Serekuta find(String nodeExp) {
        Elements es = elements.select(nodeExp);
        return new JsoupSerekuta(es);
    }

    @Override
    public String text() {
        return elements.text();
    }

    @Override
    public String attr(String attr) {
        return elements.attr(attr);
    }

    @Override
    public Serekuta first() {
        Element first = elements.first();
        return new JsoupSerekuta(new Elements(first));
    }

    @Override
    public Serekuta last() {
        Element last = elements.last();
        return new JsoupSerekuta(new Elements(last));
    }

    @Override
    public String toString() {
        return elements.outerHtml();
    }

}
