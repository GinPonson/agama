package net.osc.gin.agama.serekuta;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.osc.gin.agama.util.UrlUtils;

public class JsoupSerekuta  implements Serekuta{
	
	private Elements elements;
	
	private String domain;
	
	public JsoupSerekuta(Elements elements) {
		this.elements = elements;
	}

	public JsoupSerekuta(Elements elements, String baseUri) {
		this.elements = elements;
		domain = baseUri;
	}

    @Override
    public JsoupSerekuta find(String nodeExp){
        Elements es = elements.select(nodeExp);
        return new JsoupSerekuta(es,domain);
    }

    public JsoupSerekuta regex(String pattern){
        Elements es = new Elements();
        for(Element element : elements){
            es.addAll(element.getElementsMatchingText(pattern));
        }
        return new JsoupSerekuta(es,domain);
    }

    @Override
    public String text() {
        return elements.text();
    }

    @Override
    public List<String> texts() {
		List<String> list = new ArrayList<>();
		for(Element element : elements){
            list.add(element.text());
		}
		return list;
	}

	@Override
	public String attr(String attr) {
		return elements.attr(attr);
	}

    @Override
    public List<String> attrs(String attr) {
        List<String> list = new ArrayList<>();
        for(Element element : elements){
            list.add(element.attr(attr));
        }
        return list;
    }

	@Override
	public JsoupSerekuta first() {
		Elements es = new Elements();
		es.add(elements.first());
		return new JsoupSerekuta(es,domain);
	}

	@Override
	public JsoupSerekuta last() {
		Elements es = new Elements();
		es.add(elements.last());
		return new JsoupSerekuta(es);
	}

    @Override
    public JsoupSerekuta parent() {
        Elements es = new Elements();
        es.addAll(elements.parents());
        return new JsoupSerekuta(es);
    }
}
